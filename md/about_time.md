# 时间相关
### 将时间戳转为时间字符串
``` java
/**
 * 将时间戳转为时间字符串
 * <p>格式为yyyy-MM-dd HH:mm:ss
 */
public static String milliseconds2String(long milliseconds) {
    return milliseconds2String(milliseconds, DEFAULT_SDF);
}

/**
 * 将时间戳转为时间字符串
 * <p>格式为用户自定义
 */
public static String milliseconds2String(long milliseconds, SimpleDateFormat format) {
    return format.format(new Date(milliseconds));
}
```
 
### 将时间字符串转为时间戳
``` java
/**
 * 将时间字符串转为时间戳
 * <p>格式为yyyy-MM-dd HH:mm:ss
 */
public static long string2Milliseconds(String time) {
    return string2Milliseconds(time, DEFAULT_SDF);
}

/**
 * 将时间字符串转为时间戳
 * <p>格式为用户自定义
 */ 
public static long string2Milliseconds(String time, SimpleDateFormat format) {
    try {
        return format.parse(time).getTime();
    } catch (ParseException e) {
        e.printStackTrace();
    }
    return -1;
}
```

### 将时间字符串转为Date类型
``` java
/**
 * 将时间字符串转为Date类型
 * <p>格式为yyyy-MM-dd HH:mm:ss
 */
public static Date string2Date(String formatDate) {
    return string2Date(formatDate, DEFAULT_SDF);
}

/**
 * 将时间字符串转为Date类型
 * <p>格式为用户自定义
 */
public static Date string2Date(String formatDate, SimpleDateFormat format) {
    return new Date(string2Milliseconds(formatDate, format));
}
```

### 将Date类型转为时间字符串
``` java
/**
 * 将Date类型转为时间字符串
 * <p>格式为yyyy-MM-dd HH:mm:ss
 */
public static String date2String(Date date) {
    return date2String(date, DEFAULT_SDF);
}

/**
 * 将Date类型转为时间字符串
 * <p>格式为用户自定义
 */
public static String date2String(Date date, SimpleDateFormat format) {
    return format.format(date);
}
```

### 将Date类型转为时间戳
``` java
/**
 * 将Date类型转为时间戳
 */
public static long date2Milliseconds(Date date) {
    return date.getTime();
}
```

### 将时间戳转为Date类型
``` java
/**
 * 将时间戳转为Date类型
 */
public static Date milliseconds2Date(long milliseconds) {
    return new Date(milliseconds);
}
```

### 毫秒时间戳单位转换（单位：unit）
``` java
/**
 * 毫秒时间戳单位转换（单位：unit）
 * <pre>
 * UNIT_MSEC:毫秒
 * UNIT_SEC :秒
 * UNIT_MIN :分
 * UNIT_HOUR:小时
 * UNIT_DAY :天
 * </pre>
 */
private static long milliseconds2Unit(long milliseconds, int unit) {
    switch (unit) {
        case UNIT_MSEC:
        case UNIT_SEC:
        case UNIT_MIN:
        case UNIT_HOUR:
        case UNIT_DAY:
            return Math.abs(milliseconds) / unit;
    }
    return -1;
}
```

### 获取两个时间差（单位：unit）
``` java
/**
 * 获取两个时间差（单位：unit）
 * <pre>
 * UNIT_MSEC:毫秒
 * UNIT_SEC :秒
 * UNIT_MIN :分
 * UNIT_HOUR:小时
 * UNIT_DAY :天
 * </pre>
 * <p>time1和time2格式都为yyyy-MM-dd HH:mm:ss
 */
public static long getIntervalTime(String time1, String time2, int unit) {
    return getIntervalTime(time1, time2, unit, DEFAULT_SDF);
}

/**
 * 获取两个时间差（单位：unit）
 * <pre>
 * UNIT_MSEC:毫秒
 * UNIT_SEC :秒
 * UNIT_MIN :分
 * UNIT_HOUR:小时
 * UNIT_DAY :天
 * </pre>
 * <p>time1和time2格式都为format
 */
public static long getIntervalTime(String time1, String time2, int unit, SimpleDateFormat for
    return milliseconds2Unit(string2Milliseconds(time1, format)
            - string2Milliseconds(time2, format), unit);
}

/**
 * 获取两个时间差（单位：unit）
 * <pre>
 * UNIT_MSEC:毫秒
 * UNIT_SEC :秒
 * UNIT_MIN :分
 * UNIT_HOUR:小时
 * UNIT_DAY :天
 * </pre>
 * <p>time1和time2都为Date
 */
public static long getIntervalTime(Date time1, Date time2, int unit) {
    return milliseconds2Unit(date2Milliseconds(time2) - date2Milliseconds(time1), unit);
}
```

### 获取当前时间
``` java
/**
 * 获取当前时间
 * <p>单位（毫秒）
 */
public static long getCurTimeMills() {
    return System.currentTimeMillis();
}

/**
 * 获取当前时间
 * <p>格式为yyyy-MM-dd HH:mm:ss
 */
public static String getCurTimeString() {
    return milliseconds2String(getCurTimeMills());
}

/**
 * 获取当前时间
 * <p>格式为用户自定义
 */
public static String getCurTimeString(SimpleDateFormat format) {
    return milliseconds2String(getCurTimeMills(), format);
}

/**
 * 获取当前时间
 * <p>Date类型
 */
public static Date getCurTimeDate() {
    return new Date();
}
```

### 获取与当前时间的差（单位：unit）
``` java
/**
 * 获取与当前时间的差（单位：unit）
 * <pre>
 * UNIT_MSEC:毫秒
 * UNIT_SEC :秒
 * UNIT_MIN :分
 * UNIT_HOUR:小时
 * UNIT_DAY :天
 * <p>time1和time2格式都为yyyy-MM-dd HH:mm:ss
 */
public static long getIntervalByNow(String time, int unit) {
    return getIntervalByNow(time, unit, DEFAULT_SDF);
}

/**
 * 获取与当前时间的差（单位：unit）
 * <pre>
 * UNIT_MSEC:毫秒
 * UNIT_SEC :秒
 * UNIT_MIN :分
 * UNIT_HOUR:小时
 * UNIT_DAY :天
 * <p>time1和time2格式都为format
 */
public static long getIntervalByNow(String time, int unit, SimpleDateFormat format) {
    return getIntervalTime(getCurTimeString(), time, unit, format);
}

/**
 * 获取与当前时间的差（单位：unit）
 * <pre>
 * UNIT_MSEC:毫秒
 * UNIT_SEC :秒
 * UNIT_MIN :分
 * UNIT_HOUR:小时
 * UNIT_DAY :天
 * <p>time1和time2格式都为format
 */
public static long getIntervalByNow(Date time, int unit) {
    return getIntervalTime(getCurTimeDate(), time, unit);
}
```

### 判断闰年
``` java
/**
 * 判断闰年
 */
public static boolean isLeapYear(int year) {
    return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
}
```
