package com.blankj.subutil.util;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/07/04
 *     desc  : utils about rom
 * </pre>
 */
public final class RomUtils {

    public static final String SYS_EMUI     = "emui";
    public static final String SYS_MIUI     = "miui";
    public static final String SYS_FLYME    = "flyme";
    public static final String SYS_COLOROS  = "colorOs";
    public static final String SYS_FUNTOUCH = "Funtouch";
    public static final String SYS_SAMSUNG  = "samsung";

    ///////////////////////////////////////////////////////////////////////////
    // MIUI
    ///////////////////////////////////////////////////////////////////////////
    private static final String KEY_MIUI_VERSION_CODE        = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME        = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE    = "ro.miui.internal.storage";
    private static final String KEY_MIUI_VERSION_INCREMENTAL = "ro.build.version.incremental";

    ///////////////////////////////////////////////////////////////////////////
    // EMUI
    ///////////////////////////////////////////////////////////////////////////
    private static final String KEY_EMUI_API_LEVEL             = "ro.build.hw_emui_api_level";
    private static final String KEY_EMUI_VERSION               = "ro.build.version.emui";
    private static final String KEY_EMUI_CONFIG_HW_SYS_VERSION = "ro.confg.hw_systemversion";

    ///////////////////////////////////////////////////////////////////////////
    // OPPO
    ///////////////////////////////////////////////////////////////////////////
    private static final String KEY_OPPO_NAME    = "ro.rom.different.version";
    private static final String KEY_OPPO_VERSION = "ro.build.version.opporom";

    ///////////////////////////////////////////////////////////////////////////
    // VIVO
    ///////////////////////////////////////////////////////////////////////////
    private static final String KEY_VIVO_NAME    = "ro.vivo.os.name";
    private static final String KEY_VIVO_VERSION = "ro.vivo.os.version";

    private static RomBean bean = null;

    /**
     * Return the name of rom.
     *
     * @return the name of rom
     */
    public static RomBean getRom() {
        if (bean != null) return bean;
        bean = new RomBean();
        // 小米
        if (!TextUtils.isEmpty(getSystemProperty(KEY_MIUI_VERSION_CODE))
                || !TextUtils.isEmpty(getSystemProperty(KEY_MIUI_VERSION_NAME))
                || !TextUtils.isEmpty(getSystemProperty(KEY_MIUI_INTERNAL_STORAGE))) {
            bean.setRomName(SYS_MIUI);
            bean.setRomVersion(getSystemProperty(KEY_MIUI_VERSION_INCREMENTAL));
        }
        // 华为
        else if (!TextUtils.isEmpty(getSystemProperty(KEY_EMUI_API_LEVEL))
                || !TextUtils.isEmpty(getSystemProperty(KEY_EMUI_VERSION))
                || !TextUtils.isEmpty(getSystemProperty(KEY_EMUI_CONFIG_HW_SYS_VERSION))) {
            bean.setRomName(SYS_EMUI);
            String version = getSystemProperty(KEY_EMUI_VERSION);// EmotionUI_2.0
            String[] temp = version.split("_");
            if (temp.length > 1) {
                bean.setRomVersion(temp[1]);
            } else {
                bean.setRomVersion(version);
            }
        }
        // 魅族
        else if (Build.DISPLAY.toLowerCase().contains("flyme")) {
            bean.setRomName(SYS_FLYME);
            bean.setRomVersion(Build.DISPLAY);
            return bean;
        }
        // OPPO
        else if (!TextUtils.isEmpty(getSystemProperty(KEY_OPPO_NAME)) &&
                getSystemProperty(KEY_OPPO_NAME).toLowerCase().contains("coloros")) {
            bean.setRomName(SYS_COLOROS);
            bean.setRomVersion(getSystemProperty(KEY_OPPO_VERSION));
        }
        // VIVO
        else if (!TextUtils.isEmpty(getSystemProperty(KEY_VIVO_NAME))) {
            bean.setRomName(SYS_FUNTOUCH);
            bean.setRomVersion(getSystemProperty(KEY_VIVO_VERSION));
        }
        // 其他手机
        else {
            String brand = Build.BRAND;
            bean.setRomName(Build.BRAND);
            if (SYS_SAMSUNG.equalsIgnoreCase(brand)) {
                bean.setRomVersion(getSystemProperty("ro.build.changelist"));
            }
        }
        return bean;
    }

    private static String getSystemProperty(final String name) {
        String prop = getSystemPropertyByShell(name);
        if (!TextUtils.isEmpty(prop)) return prop;
        prop = getSystemPropertyByStream(name);
        if (!TextUtils.isEmpty(prop)) return prop;
        if (Build.VERSION.SDK_INT < 28) {
            return getSystemPropertyByReflect(name);
        }
        return prop;
    }

    private static String getSystemPropertyByShell(final String propName) {
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            return input.readLine();
        } catch (IOException e) {
            return "";
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ignore) {

                }
            }
        }
    }

    private static String getSystemPropertyByStream(final String key) {
        try {
            Properties prop = new Properties();
            FileInputStream is = new FileInputStream(
                    new File(Environment.getRootDirectory(), "build.prop")
            );
            prop.load(is);
            return prop.getProperty(key, "");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String getSystemPropertyByReflect(String key) {
        try {
            @SuppressLint("PrivateApi")
            Class<?> clz = Class.forName("android.os.SystemProperties");
            Method get = clz.getMethod("get", String.class, String.class);
            return (String) get.invoke(clz, key, "");
        } catch (Exception e) {
            return "";
        }
    }

    public static class RomBean {
        private String romName;
        private String romVersion;

        public String getRomName() {
            if (romName == null) return "";
            return romName;
        }

        private void setRomName(String romName) {
            this.romName = romName;
        }

        public String getRomVersion() {
            if (romVersion == null) return "";
            return romVersion;
        }

        private void setRomVersion(String romVersion) {
            this.romVersion = romVersion;
        }

        @Override
        public String toString() {
            return "romName: " + romName +
                    "\nromVersion: " + romVersion;
        }
    }
}
