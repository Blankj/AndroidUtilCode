package com.blankj.subutil.util.http;

import android.support.annotation.NonNull;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2019/02/17
 * </pre>
 */
public final class Request {

    URL                 mURL;
    Map<String, String> mHeader;
    Body                mBody;

    public static Request withUrl(@NonNull final String url) {
        return new Request(url);
    }

    private Request(final String url) {
        try {
            mURL = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public Request addHeader(@NonNull final String name, @NonNull final String value) {
        if (mHeader == null) {
            mHeader = new HashMap<>();
        }
        mHeader.put(name, value);
        return this;
    }

    public Request addHeader(@NonNull final Map<String, String> header) {
        if (this.mHeader == null) {
            this.mHeader = new HashMap<>();
        }
        this.mHeader.putAll(header);
        return this;
    }

    public Request post(@NonNull final Body body) {
        this.mBody = body;
        return this;
    }

    public static class Body {
        String              mediaType;
        BufferedInputStream bis;
        long                length;

        private Body(final String mediaType, final byte[] body) {
            this.mediaType = mediaType;
            bis = new BufferedInputStream(new ByteArrayInputStream(body));
            length = body.length;
        }

        private Body(final String mediaType, final InputStream body) {
            this.mediaType = mediaType;
            if (body instanceof BufferedInputStream) {
                bis = (BufferedInputStream) body;
            } else {
                bis = new BufferedInputStream(body);
            }
            length = -1;
        }

        private static String getCharsetFromMediaType(String mediaType) {
            mediaType = mediaType.toLowerCase().replace(" ", "");
            int index = mediaType.indexOf("charset=");
            if (index == -1) return "utf-8";
            int st = index + 8;
            int end = mediaType.length();
            if (st >= end) {
                throw new IllegalArgumentException("MediaType is not correct: \"" + mediaType + "\"");
            }
            for (int i = st; i < end; i++) {
                char c = mediaType.charAt(i);
                if (c >= 'A' && c <= 'Z') continue;
                if (c >= 'a' && c <= 'z') continue;
                if (c >= '0' && c <= '9') continue;
                if (c == '-' && i != 0) continue;
                if (c == '+' && i != 0) continue;
                if (c == ':' && i != 0) continue;
                if (c == '_' && i != 0) continue;
                if (c == '.' && i != 0) continue;
                end = i;
                break;
            }
            String charset = mediaType.substring(st, end);
            return checkCharset(charset);
        }

        public static Body create(@NonNull String mediaType, @NonNull byte[] content) {
            return new Body(mediaType, content);
        }

        public static Body form(@NonNull final Map<String, String> form) {
            return form(form, "utf-8");
        }

        public static Body form(@NonNull final Map<String, String> form, String charset) {
            String mediaType = "application/x-www-form-urlencoded;charset=" + checkCharset(charset);
            final StringBuilder sb = new StringBuilder();
            for (String key : form.keySet()) {
                if (sb.length() > 0) sb.append("&");
                sb.append(key).append("=").append(form.get(key));
            }
            try {
                return new Body(mediaType, sb.toString().getBytes(charset));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return null;
            }
        }

        public static Body json(@NonNull final String json) {
            return json(json, "utf-8");
        }

        public static Body json(@NonNull final String json, String charset) {
            String mediaType = "application/json;charset=" + checkCharset(charset);
            try {
                return new Body(mediaType, json.getBytes(charset));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return null;
            }
        }

//        public static RequestBody file(String mediaType, final File file) {
//
//            return new RequestBody(mediaType, );
//        }

    }

    private static String checkCharset(final String charset) {
        if (Charset.isSupported(charset)) return charset;
        throw new IllegalCharsetNameException(charset);
    }
}
