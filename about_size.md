# 尺寸相关
### dp与px转换
``` java
/**
* dp转px
*/
public static int dp2px(Context context, float dpValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (dpValue * scale + 0.5f);
}

/**
* px转dp
*/
public static int px2dp(Context context, float pxValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (pxValue / scale + 0.5f);
}
```
 
### sp与px转换
``` java
/**
* sp转px
*/
public static int sp2px(Context context, float spValue) {
    final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
    return (int) (spValue * fontScale + 0.5f);
}

/**
* px转sp
*/
public static int px2sp(Context context, float pxValue) {
	final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
    return (int) (pxValue / fontScale + 0.5f);
}
```

### 各种单位转换
``` java
/**
 * 各种单位转换
 * 该方法存在于TypedValue
 */
public static float applyDimension(int unit, float value, DisplayMetrics metrics) {
    switch (unit) {
        case TypedValue.COMPLEX_UNIT_PX:
            return value;
        case TypedValue.COMPLEX_UNIT_DIP:
            return value * metrics.density;
        case TypedValue.COMPLEX_UNIT_SP:
            return value * metrics.scaledDensity;
        case TypedValue.COMPLEX_UNIT_PT:
            return value * metrics.xdpi * (1.0f / 72);
        case TypedValue.COMPLEX_UNIT_IN:
            return value * metrics.xdpi;
        case TypedValue.COMPLEX_UNIT_MM:
            return value * metrics.xdpi * (1.0f / 25.4f);
    }
    return 0;
}
```

### 在onCreate()即可强行获取View的尺寸
``` java
/**
* 在onCreate()即可强行获取View的尺寸
* 需回调onGetSizeListener接口，在onGetSize中获取view宽高
* 用法示例如下所示
* SizeUtils.forceGetViewSize(view);
* SizeUtils.setListener(new SizeUtils.onGetSizeListener() {
 *     @Override
 *     public void onGetSize(View view) {
 *         Log.d("tag", view.getWidth() + " " + view.getHeight());
 *     }
* });
*/
public static void forceGetViewSize(final View view) {
    view.post(new Runnable() {
        @Override
        public void run() {
            if (mListener != null) {
                mListener.onGetSize(view);
            }
        }
    });
}

/**
* 获取到View尺寸的监听
*/
public interface onGetSizeListener {
    void onGetSize(View view);
}

public static void setListener(onGetSizeListener listener) {
    mListener = listener;
}

private static onGetSizeListener mListener;
```

### ListView中提前测量View尺寸
``` java
/**
 * ListView中提前测量View尺寸，如headerView
 * 用的时候去掉注释拷贝到ListView中即可
 * 参照以下注释代码
 */
public static void measureViewInLV(View view) {
    Log.i("tips", "U should copy the follow code.");
    /*
    ViewGroup.LayoutParams p = view.getLayoutParams();
    if (p == null) {
        p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }
    int width = ViewGroup.getChildMeasureSpec(0, 0, p.width);
    int height;
    int tempHeight = p.height;
    if (tempHeight > 0) {
        height = MeasureSpec.makeMeasureSpec(tempHeight,
                MeasureSpec.EXACTLY);
    } else {
        height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
    }
    view.measure(width, height);
    */
}
```
