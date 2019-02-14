package com.blankj.utilcode.util;

import android.accounts.NetworkErrorException;
import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/02/08
 *     desc  : utils about http
 * </pre>
 */
public class HttpUtils {

    private static final String BOUNDARY    = java.util.UUID.randomUUID().toString();
    private static final String TWO_HYPHENS = "--";

    private static final int CONNECT_TIMEOUT_TIME = 15000;
    private static final int READ_TIMEOUT_TIME    = 19000;

    private static final TrustManager[] DEFAULT_TRUST_MANAGERS = new TrustManager[]{
            new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @SuppressLint("TrustAllX509TrustManager")
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) { /**/ }

                @SuppressLint("TrustAllX509TrustManager")
                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) { /**/ }
            }
    };

    private static final HostnameVerifier DEFAULT_VERIFIER = new HostnameVerifier() {
        @SuppressLint("BadHostnameVerifier")
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    private static final Config CONFIG = new Config();

    private HttpUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void doGet(@NonNull final Request request, final BaseResponse response) {
        if (response == null) return;
        HttpURLConnection conn = null;
        try {
            conn = getConnection(request, "GET");
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                InputStream is = conn.getInputStream();
                response.onSuccess(is);
                is.close();
            } else if (responseCode == 301 || responseCode == 302) {
                String location = conn.getHeaderField("Location");
                doGet(request, response);
            } else {
                response.onError(new NetworkErrorException("Response code: " + responseCode));
            }
        } catch (IOException e) {
            response.onError(e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public static void doPost(@NonNull final Request request,
                              final BaseResponse response) {
        if (response == null) return;
        HttpURLConnection conn = null;
        try {
            conn = getConnection(request, "POST");
            addHeader(conn, request.header);
//            conn.setRequestProperty("accept", "*/*");
//            conn.setRequestProperty("connection", "Keep-Alive");
//            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded;charset=UTF-8");
//            conn.setRequestProperty("content-length", String.valueOf(sb.length()));
//            conn.setDoOutput(true);
//
//            PrintWriter printWriter = new PrintWriter(conn.getOutputStream());
//            printWriter.write(sb.toString());
//            printWriter.flush();
//            printWriter.close();

//            DataOutputStream os;
//            BufferedOutputStream outputStream = new BufferedOutputStream(conn.getOutputStream());
//            outputStream.write(request.body.content);
//            outputStream.close();
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                InputStream is = conn.getInputStream();
                response.onSuccess(is);
                is.close();
            } else {
                response.onError(new NetworkErrorException("Response code: " + responseCode));
            }
        } catch (IOException e) {
            response.onError(e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    private static HttpURLConnection getConnection(final Request request, final String requestMethod) throws IOException {
        URL httpUrl = new URL(request.url);
        HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
        if (conn instanceof HttpsURLConnection) {
            HttpsURLConnection httpsConn = (HttpsURLConnection) conn;
            trustAllHosts(httpsConn);
        }
        conn.setConnectTimeout(CONFIG.connectTimeout);
        conn.setReadTimeout(CONFIG.readTimeout);
        conn.setUseCaches(CONFIG.useCaches);
        conn.setRequestMethod(requestMethod);
        return conn;
    }

    private static void trustAllHosts(HttpsURLConnection conn) {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, DEFAULT_TRUST_MANAGERS, new java.security.SecureRandom());
            conn.setSSLSocketFactory(sslContext.getSocketFactory());
            conn.setHostnameVerifier(DEFAULT_VERIFIER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addHeader(final HttpURLConnection conn, final Map<String, String> headerMap) {
        if (headerMap != null) {
            for (String key : headerMap.keySet()) {
                conn.setRequestProperty(key, headerMap.get(key));
            }
        }
    }

    private static String checkCharset(final String charset) {
        if (Charset.isSupported(charset)) return charset;
        throw new IllegalCharsetNameException(charset);
    }

    private static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static class Config {
        private int     connectTimeout = CONNECT_TIMEOUT_TIME;
        private int     readTimeout    = READ_TIMEOUT_TIME;
        private boolean useCaches      = false;
    }

    public static class Request {
        private String              url;
        private Map<String, String> header;
        private RequestBody         body;

        public static Request withUrl(@NonNull final String url) {
            return new Request(url);
        }

        private Request(final String url) {
            this.url = url;
        }

        public Request addHeader(@NonNull final String name, @NonNull final String value) {
            if (header == null) {
                header = new HashMap<>();
            }
            header.put(name, value);
            return this;
        }

        public Request addHeader(@NonNull final Map<String, String> header) {
            if (this.header == null) {
                this.header = new HashMap<>();
            }
            this.header.putAll(header);
            return this;
        }

        public Request post(@NonNull final RequestBody body) {
            this.body = body;
            return this;
        }
    }

    public static class RequestBody {
        String      mediaType;
        byte[]      content;
        InputStream is;
        long        length;

        private RequestBody(String mediaType, byte[] content) {
            this.mediaType = mediaType;
            this.content = content;
            length = content == null ? 0 : content.length;
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

        public static RequestBody create(String mediaType, byte[] content) {
            return new RequestBody(mediaType, content);
        }

        public static RequestBody form(final Map<String, String> form) {
            return form(form, "utf-8");
        }

        public static RequestBody form(final Map<String, String> form, String charset) {
            String mediaType = "application/x-www-form-urlencoded;charset=" + checkCharset(charset);
            if (form != null) {
                final StringBuilder sb = new StringBuilder();
                for (String key : form.keySet()) {
                    if (sb.length() > 0) sb.append("&");
                    sb.append(key).append("=").append(form.get(key));
                }
                try {
                    return new RequestBody(mediaType, sb.toString().getBytes(charset));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            return new RequestBody(mediaType, null);
        }

        public static RequestBody json(final String json) {
            return json(json, "utf-8");
        }

        public static RequestBody json(final String json, String charset) {
            String mediaType = "application/json;charset=" + checkCharset(charset);
            if (json != null) {
                try {
                    return new RequestBody(mediaType, json.getBytes(charset));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            return new RequestBody(mediaType, null);
        }

//        public static RequestBody file(String mediaType, final File file) {
//
//            return new RequestBody(mediaType, );
//        }

    }

    public static class Response {
        Map<String, List<String>> headers;
    }


    public static abstract class BaseResponse {
        public abstract void onSuccess(InputStream is);

        public abstract void onError(Throwable t);
    }
}
