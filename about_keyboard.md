# ¼üÅÌÏà¹Ø
### ±ÜÃâÊäÈë·¨Ãæ°åÕÚµ²
``` java
// ÔÚmanifest.xmlÖĞactivityÖĞÉèÖÃ
android:windowSoftInputMode="stateVisible|adjustResize"
```

### ¶¯Ì¬Òş²ØÈí¼üÅÌ
``` java
/**
* ¶¯Ì¬Òş²ØÈí¼üÅÌ
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
* ¶¯Ì¬Òş²ØÈí¼üÅÌ
*/
public static void hideSoftInput(Context context, EditText edit) {
    edit.clearFocus();
    InputMethodManager inputmanger = (InputMethodManager) context
            .getSystemService(Context.INPUT_METHOD_SERVICE);
    inputmanger.hideSoftInputFromWindow(edit.getWindowToken(), 0);
}
```

### ¶¯Ì¬ÏÔÊ¾Èí¼üÅÌ
``` java
/**
* ¶¯Ì¬ÏÔÊ¾Èí¼üÅÌ
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

### ÇĞ»»¼üÅÌÏÔÊ¾Óë·ñ×´Ì¬
``` java
/**
* ÇĞ»»¼üÅÌÏÔÊ¾Óë·ñ×´Ì¬
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
