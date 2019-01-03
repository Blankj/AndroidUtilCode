//package com.blankj.utilcode.util;
//
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//public final class JsonUtils {
//
//    private static byte TYPE_LONG = 0x01;
//    private static byte TYPE_INT  = 0x02;
//
//    private JsonUtils() {
//        throw new UnsupportedOperationException("u can't instantiate me...");
//    }
//
//    public static long getLong(final JSONObject jsonObject,
//                               final String key,
//                               final long defaultValue) {
//        if (jsonObject == null || key == null || key.length() == 0) {
//            return defaultValue;
//        }
//        try {
//            return jsonObject.getLong(key);
//        } catch (JSONException e) {
//            return defaultValue;
//        }
//    }
//
//    public static long getLong(final String json,
//                               final String key,
//                               final long defaultValue) {
//        if (json == null || json.length() == 0
//                || key == null || key.length() == 0) {
//            return defaultValue;
//        }
//
//        try {
//            return getLong(new JSONObject(json), key, defaultValue);
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return defaultValue;
//        }
//    }
//
//    public static Object getType(final JSONObject jsonObject,
//                                 final String key,
//                                 final Object defaultValue,
//                                 final byte type) {
//        if (jsonObject == null || key == null || key.length() == 0) {
//            return defaultValue;
//        }
//        try {
//            if (type == TYPE_LONG) {
//                return jsonObject.getLong(key);
//            } else if (type == TYPE_INT) {
//                return jsonObject.getInt(key);
//            }
//        } catch (JSONException e) {
//            return defaultValue;
//        }
//    }
//
//    public static long getLong(final String json,
//                               final String key,
//                               final long defaultValue) {
//        if (json == null || json.length() == 0
//                || key == null || key.length() == 0) {
//            return defaultValue;
//        }
//
//        try {
//            return getLong(new JSONObject(json), key, defaultValue);
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return defaultValue;
//        }
//    }
//
//}
