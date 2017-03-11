package com.blankj.utilcode.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.StringRes;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <pre>
 *     author: deemons
 *     blog  : http://blankj.com
 *     time  : 2016/8/2
 *     desc  : SP相关工具类
 * </pre>
 */
public class SPUtils {


    private static SPUtils spUtils;

    private static SharedPreferences sp;
    private static SharedPreferences.Editor editor;

    private static final String DEFAULT_SP_NAME = "SharedData";
    private static final int DEFAULT_INT = -1;
    private static final float DEFAULT_FLOAT = -1f;
    private static final String DEFAULT_STRING = null;
    private static final boolean DEFAULT_BOOLEAN = false;
    private static final Set<String> DEFAULT_STRING_SET = new HashSet<>(0);

    private static String mCurSPName = DEFAULT_SP_NAME;
    private Context mContext;

    private SPUtils(Context context) {
        this(context, DEFAULT_SP_NAME);
    }

    private SPUtils(Context context, String spName) {
        mContext = context.getApplicationContext();
        sp = getContext().getSharedPreferences(spName, Context.MODE_PRIVATE);
        editor = sp.edit();
        mCurSPName = spName;
    }

    private Context getContext() {
        return mContext;
    }

    /**
     * SPUtils构造函数
     *
     * @param context application
     * @return spUtils
     */
    public static SPUtils init(Context context) {
        if (spUtils == null || !mCurSPName.equals(DEFAULT_SP_NAME)) {
            spUtils = new SPUtils(context);
        }
        return spUtils;
    }

    /**
     * SPUtils构造函数
     * 在Application中初始化
     *
     * @param context application
     * @param spName  sp文件的名称
     * @return spUtils
     */
    public static SPUtils init(Context context, String spName) {
        if (spUtils == null) {
            spUtils = new SPUtils(context, spName);
        } else if (!spName.equals(mCurSPName)) {
            spUtils = new SPUtils(context, spName);
        }
        return spUtils;
    }

    /**
     * 获取spUtils实例
     *
     * @return SPUtils
     */
    public static SPUtils getInstant() {
        if (spUtils != null)
            return spUtils;
        throw new NullPointerException("u should init first");
    }


    /**
     * SP中写入String类型value
     *
     * @param key   键 string.xml
     * @param value 值
     * @return spUtils
     */
    public SPUtils put(@StringRes int key, Object value) {
        return put(getContext().getString(key), value);
    }

    /**
     * SP中写入基本数据类型的value
     *
     * @param key   键
     * @param value 值
     * @return spUtils
     */
    public SPUtils put(String key, Object value) {

        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else {
            editor.putString(key, value.toString());
        }
        editor.apply();
        return spUtils;
    }

    /**
     * sp中获取key对于的值
     * 如果用同一key存放了不同类型的value，则此方法不适用，此时需要根据类型获取。
     *
     * @param key           键
     * @param defaultObject 值
     * @return object
     */
    public Object get(@StringRes int key, Object defaultObject) {
        return get(getContext().getString(key), defaultObject);
    }


    /**
     * sp中获取key对于的值
     * 如果用同一key存放不同类型的value，则此方法不适用，此时需要根据类型获取。
     *
     * @param key           键
     * @param defaultObject 默认值
     * @return object
     */
    public Object get(String key, Object defaultObject) {
        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (int) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (long) defaultObject);
        }
        return null;
    }

    /**
     * sp中存储int类型的value
     *
     * @param key   键
     * @param value 值
     * @return SPUtils
     */
    public SPUtils putInt(String key, int value) {
        editor.putInt(key, value).apply();
        return this;
    }

    /**
     * sp中存储int类型的value
     *
     * @param key   键
     * @param value 值
     * @return SPUtils
     */
    public SPUtils putInt(@StringRes int key, int value) {
        return putInt(getContext().getString(key), value);
    }

    /**
     * sp中获取int类型的value
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值
     */
    public int getInt(@StringRes int key) {
        return getInt(getContext().getString(key));
    }

    /**
     * sp中获取int类型的value
     *
     * @param key      键
     * @param defValue 默认值
     * @return 存在返回对应值，不存在返回默认值
     */
    public int getInt(@StringRes int key, int defValue) {
        return getInt(getContext().getString(key), defValue);
    }


    /**
     * sp中获取int类型的value
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值
     */
    public int getInt(String key) {
        return getInt(key, DEFAULT_INT);
    }

    /**
     * sp中获取int类型的value
     *
     * @param key      键
     * @param defValue 默认值
     * @return 存在返回对应值，不存在返回默认值
     */
    public int getInt(String key, int defValue) {
        return sp.getInt(key, defValue);
    }

    /**
     * sp中存储float类型的value
     *
     * @param key   键
     * @param value 值
     * @return SPUtils
     */
    public SPUtils putFloat(@StringRes int key, float value) {
        return putFloat(getContext().getString(key), value);
    }

    /**
     * sp中存储float类型的value
     *
     * @param key   键
     * @param value 值
     * @return SPUtils
     */
    public SPUtils putFloat(String key, float value) {
        editor.putFloat(key, value).apply();
        return spUtils;
    }

    /**
     * sp中获取float类型的value
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值
     */
    public float getFloat(String key) {
        return getFloat(key, DEFAULT_FLOAT);
    }


    /**
     * sp中获取float类型的value
     *
     * @param key      键
     * @param defValue 默认值
     * @return 存在返回对应值，不存在返回默认值
     */
    public float getFloat(String key, float defValue) {
        return sp.getFloat(key, defValue);
    }

    /**
     * sp中获取float类型的value
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值
     */
    public float getFloat(@StringRes int key) {
        return getFloat(getContext().getString(key));
    }

    /**
     * sp中获取float类型的value
     *
     * @param key      键
     * @param defValue 默认值
     * @return 存在返回对应值，不存在返回默认值
     */
    public float getFloat(@StringRes int key, float defValue) {
        return getFloat(getContext().getString(key), defValue);
    }

    /**
     * sp中存储long类型的value
     *
     * @param key   键
     * @param value 值
     * @return SPUtils
     */
    public SPUtils putLong(@StringRes int key, long value) {
        return putLong(getContext().getString(key), value);
    }

    /**
     * sp中存储long类型的value
     *
     * @param key   键
     * @param value 值
     * @return SPUtils
     */
    public SPUtils putLong(String key, long value) {
        editor.putLong(key, value).apply();
        return spUtils;
    }

    /**
     * sp中获取long类型的value
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值
     */
    public long getLong(String key) {
        return getLong(key, DEFAULT_INT);
    }

    /**
     * sp中获取long类型的value
     *
     * @param key      键
     * @param defValue 默认值
     * @return 存在返回对应值，不存在返回默认值
     */
    public long getLong(String key, long defValue) {
        return sp.getLong(key, defValue);
    }

    /**
     * sp中获取long类型的value
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值
     */
    public long getLong(@StringRes int key) {
        return getLong(getContext().getString(key));
    }

    /**
     * sp中获取long类型的value
     *
     * @param key      键
     * @param defValue 默认值
     * @return 存在返回对应值，不存在返回默认值
     */
    public long getLong(@StringRes int key, long defValue) {
        return getLong(getContext().getString(key), defValue);
    }

    /**
     * sp中存储String类型的value
     *
     * @param key   键
     * @param value 值
     * @return SPUtils
     */
    public SPUtils putString(@StringRes int key, String value) {
        return putString(getContext().getString(key), value);
    }

    /**
     * sp中存储String类型的value
     *
     * @param key   键
     * @param value 值
     * @return SPUtils
     */
    public SPUtils putString(String key, String value) {
        editor.putString(key, value).apply();
        return spUtils;
    }

    /**
     * sp中获取String类型的value
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值
     */
    public String getString(String key) {
        return getString(key, DEFAULT_STRING);
    }

    /**
     * sp中获取String类型的value
     *
     * @param key      键
     * @param defValue 默认值
     * @return 存在返回对应值，不存在返回默认值
     */
    public String getString(String key, String defValue) {
        return sp.getString(key, defValue);
    }

    /**
     * sp中获取String类型的value
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值
     */
    public String getString(@StringRes int key) {
        return getString(getContext().getString(key), DEFAULT_STRING);
    }

    /**
     * sp中获取String类型的value
     *
     * @param key      键
     * @param defValue 默认值
     * @return 存在返回对应值，不存在返回默认值
     */
    public String getString(@StringRes int key, String defValue) {
        return getString(getContext().getString(key), defValue);
    }

    /**
     * sp中存储boolean类型的value
     *
     * @param key   键
     * @param value 值
     * @return SPUtils
     */
    public SPUtils putBoolean(@StringRes int key, boolean value) {
        return putBoolean(getContext().getString(key), value);
    }

    /**
     * sp中存储boolean类型的value
     *
     * @param key   键
     * @param value 值
     * @return SPUtils
     */
    public SPUtils putBoolean(String key, boolean value) {
        editor.putBoolean(key, value).apply();
        return spUtils;
    }

    /**
     * sp中获取boolean类型的value
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值
     */
    public boolean getBoolean(String key) {
        return getBoolean(key, DEFAULT_BOOLEAN);
    }

    /**
     * sp中获取boolean类型的value
     *
     * @param key      键
     * @param defValue 默认值
     * @return 存在返回对应值，不存在返回默认值
     */
    public boolean getBoolean(String key, boolean defValue) {
        return sp.getBoolean(key, defValue);
    }

    /**
     * sp中获取boolean类型的value
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值
     */
    public boolean getBoolean(@StringRes int key) {
        return getBoolean(getContext().getString(key));
    }

    /**
     * sp中获取boolean类型的value
     *
     * @param key      键
     * @param defValue 默认值
     * @return 存在返回对应值，不存在返回默认值
     */
    public boolean getBoolean(@StringRes int key, boolean defValue) {
        return getBoolean(getContext().getString(key), defValue);
    }

    /**
     * sp中存储String类型的Set集合
     *
     * @param key   键
     * @param value 值
     * @return SPUtils
     */
    public SPUtils putStringSet(@StringRes int key, Set<String> value) {
        return putStringSet(getContext().getString(key), value);
    }

    /**
     * sp中存储String类型的Set集合
     *
     * @param key   键
     * @param value 值
     * @return SPUtils
     */
    public SPUtils putStringSet(String key, Set<String> value) {
        editor.putStringSet(key, value).apply();
        return spUtils;
    }

    /**
     * sp中获取boolean类型的value
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值
     */
    public Set<String> getStringSet(String key) {
        return getStringSet(key, DEFAULT_STRING_SET);
    }

    /**
     * sp中获取boolean类型的value
     *
     * @param key      键
     * @param defValue 默认值
     * @return 存在返回对应值，不存在返回默认值
     */
    public Set<String> getStringSet(String key, Set<String> defValue) {
        return sp.getStringSet(key, defValue);
    }

    /**
     * sp中获取boolean类型的value
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值
     */
    public Set<String> getStringSet(@StringRes int key) {
        return getStringSet(getContext().getString(key));
    }

    /**
     * sp中获取boolean类型的value
     *
     * @param key      键
     * @param defValue 默认值
     * @return 存在返回对应值，不存在返回默认值
     */
    public Set<String> getStringSet(@StringRes int key, Set<String> defValue) {
        return getStringSet(getContext().getString(key), defValue);
    }


    /**
     * 校验sp中是否存储此key
     *
     * @param key 键
     * @return true 存在；false 不存在
     */
    public boolean contains(String key) {
        return sp.contains(key);
    }

    /**
     * 校验sp中是否存储此key
     *
     * @param key 键
     * @return true 存在；false 不存在
     */
    public boolean contains(@StringRes int key) {
        return contains(getContext().getString(key));
    }

    /**
     * SP中获取所有键值对
     *
     * @return Map对象
     */
    public Map<String, ?> getAll() {
        return sp.getAll();
    }

    /**
     * SP中移除该key
     *
     * @param key 键
     * @return SPUtils
     */
    public SPUtils remove(@StringRes int key) {
        return remove(getContext().getString(key));
    }

    /**
     * SP中移除该key
     *
     * @param key 键
     * @return SPUtils
     */
    public SPUtils remove(String key) {
        editor.remove(key).apply();
        return spUtils;
    }

    /**
     * SP中清除所有数据
     *
     * @return SPUtils
     */
    public SPUtils clear() {
        editor.clear().apply();
        return spUtils;
    }

    /**
     * 获取SharedPreferences
     *
     * @return SharedPreferences
     */
    public SharedPreferences getSharedPreferences() {
        return sp;
    }

}