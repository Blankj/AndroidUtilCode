package com.blankj.utilcode.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2020/03/19
 *     desc  :
 * </pre>
 */
public class UtilsTransActivity extends AppCompatActivity {

    private static final Map<UtilsTransActivity, TransActivityDelegate> CALLBACK_MAP = new HashMap<>();
    private static       TransActivityDelegate                          sDelegate;

    public static void start(final TransActivityDelegate delegate) {
        start(null, delegate);
    }

    public static void start(final Utils.Consumer<Intent> consumer,
                             final TransActivityDelegate delegate) {
        if (delegate == null) return;
        Intent starter = new Intent(Utils.getApp(), UtilsTransActivity.class);
        starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (consumer != null) {
            consumer.accept(starter);
        }
        Utils.getApp().startActivity(starter);
        sDelegate = delegate;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        overridePendingTransition(0, 0);
        if (sDelegate == null) {
            super.onCreate(savedInstanceState);
            finish();
            return;
        }
        CALLBACK_MAP.put(this, sDelegate);
        sDelegate.onCreateBefore(this, savedInstanceState);
        super.onCreate(savedInstanceState);
        sDelegate.onCreated(this, savedInstanceState);
        sDelegate = null;
    }

    @Override
    protected void onStart() {
        super.onStart();
        TransActivityDelegate callback = CALLBACK_MAP.get(this);
        if (callback == null) return;
        callback.onStarted(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        TransActivityDelegate callback = CALLBACK_MAP.get(this);
        if (callback == null) return;
        callback.onResumed(this);
    }

    @Override
    protected void onPause() {
        overridePendingTransition(0, 0);
        super.onPause();
        TransActivityDelegate callback = CALLBACK_MAP.get(this);
        if (callback == null) return;
        callback.onPaused(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        TransActivityDelegate callback = CALLBACK_MAP.get(this);
        if (callback == null) return;
        callback.onStopped(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        TransActivityDelegate callback = CALLBACK_MAP.get(this);
        if (callback == null) return;
        callback.onSaveInstanceState(this, outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TransActivityDelegate callback = CALLBACK_MAP.get(this);
        if (callback == null) return;
        callback.onDestroy(this);
        CALLBACK_MAP.remove(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        TransActivityDelegate callback = CALLBACK_MAP.get(this);
        if (callback == null) return;
        callback.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TransActivityDelegate callback = CALLBACK_MAP.get(this);
        if (callback == null) return;
        callback.onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        TransActivityDelegate callback = CALLBACK_MAP.get(this);
        if (callback == null) return super.dispatchTouchEvent(ev);
        if (callback.dispatchTouchEvent(this, ev)) {
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    public abstract static class TransActivityDelegate {
        public void onCreateBefore(Activity activity, @Nullable Bundle savedInstanceState) {/**/}

        public void onCreated(Activity activity, @Nullable Bundle savedInstanceState) {/**/}

        public void onStarted(Activity activity) {/**/}

        public void onDestroy(Activity activity) {/**/}

        public void onResumed(Activity activity) {/**/}

        public void onPaused(Activity activity) {/**/}

        public void onStopped(Activity activity) {/**/}

        public void onSaveInstanceState(Activity activity, Bundle outState) {/**/}

        public void onRequestPermissionsResult(Activity activity, int requestCode, String[] permissions, int[] grantResults) {/**/}

        public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {/**/}

        public boolean dispatchTouchEvent(Activity activity, MotionEvent ev) {
            return false;
        }
    }
}
