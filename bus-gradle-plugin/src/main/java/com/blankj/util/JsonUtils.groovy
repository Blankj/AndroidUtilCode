package com.blankj.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/10/08
 *     desc  :
 * </pre>
 */
final class JsonUtils {

    static final Gson GSON = new GsonBuilder().setPrettyPrinting().create()

    static String getFormatJson(Object object) {
        return GSON.toJson(object)
    }
}
