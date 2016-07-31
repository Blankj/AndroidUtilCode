# 键盘相关
### 避免输入法面板遮挡
``` java
// 在manifest.xml中activity中设置
android:windowSoftInputMode="stateVisible|adjustResize"
```

### 动态隐藏软键盘
``` java
/**
* 动态隐藏软键盘
*/
public static void hideSoftInput(Activity activity) {
    View view = activity.getWindow().peekDecorView();
    if (view != null) {
        InputMethodManager inputmanger = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}

/**
* 动态隐藏软键盘
*/
public static void hideSoftInput(Context context, EditText edit) {
    edit.clearFocus();
    InputMethodManager inputmanger = (InputMethodManager) context
            .getSystemService(Context.INPUT_METHOD_SERVICE);
    inputmanger.hideSoftInputFromWindow(edit.getWindowToken(), 0);
}
```

###  点击屏幕空白区域隐藏软键盘
``` java
// 方法1：在onTouch中处理，未获焦点则隐藏
/**
* 在onTouch中处理，未获焦点则隐藏
*/
@Override
public boolean onTouchEvent(MotionEvent event) {
    if (null != this.getCurrentFocus()) {
        InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
    }
    return super.onTouchEvent(event);
}

// 方法2：根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，需重写dispatchTouchEvent
@Override
public boolean dispatchTouchEvent(MotionEvent ev) {
    if (ev.getAction() == MotionEvent.ACTION_DOWN) {
        View v = getCurrentFocus();
        if (isShouldHideKeyboard(v, ev)) {
            hideKeyboard(v.getWindowToken());
        }
    }
    return super.dispatchTouchEvent(ev);
}

/**
* 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
*/
private boolean isShouldHideKeyboard(View v, MotionEvent event) {
    if (v != null && (v instanceof EditText)) {
        int[] l = {0, 0};
        v.getLocationInWindow(l);
        int left = l[0],
                top = l[1],
                bottom = top + v.getHeight(),
                right = left + v.getWidth();
        return !(event.getX() > left && event.getX() < right
                && event.getY() > top && event.getY() < bottom);
    }
    return false;
}

/**
* 获取InputMethodManager，隐藏软键盘
*/
private void hideKeyboard(IBinder token) {
    if (token != null) {
        InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
```

### 动态显示软键盘
``` java
/**
* 动态显示软键盘
*/
public static void showSoftInput(Context context, EditText edit) {
    edit.setFocusable(true);
    edit.setFocusableInTouchMode(true);
    edit.requestFocus();
    InputMethodManager inputManager = (InputMethodManager) context
            .getSystemService(Context.INPUT_METHOD_SERVICE);
    inputManager.showSoftInput(edit, 0);
}
```

### 切换键盘显示与否状态
``` java
/**
* 切换键盘显示与否状态
*/
public static void toggleSoftInput(Context context, EditText edit) {
    edit.setFocusable(true);
    edit.setFocusableInTouchMode(true);
    edit.requestFocus();
    InputMethodManager inputManager = (InputMethodManager) context
            .getSystemService(Context.INPUT_METHOD_SERVICE);
    inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
}
```
