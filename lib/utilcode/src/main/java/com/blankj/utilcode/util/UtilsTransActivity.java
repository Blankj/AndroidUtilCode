package com.blankj.utilcode.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import java.io.Serializable;
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

    protected static final String EXTRA_DELEGATE = "extra_delegate";

    public static void start(final TransActivityDelegate delegate) {
        start(null, null, delegate, UtilsTransActivity.class);
    }

    public static void start(final Utils.Consumer<Intent> consumer,
                             final TransActivityDelegate delegate) {
        start(null, consumer, delegate, UtilsTransActivity.class);
    }

    public static void start(final Activity activity,
                             final TransActivityDelegate delegate) {
        start(activity, null, delegate, UtilsTransActivity.class);
    }

    public static void start(final Activity activity,
                             final Utils.Consumer<Intent> consumer,
                             final TransActivityDelegate delegate) {
        start(activity, consumer, delegate, UtilsTransActivity.class);
    }

    protected static void start(final Activity activity,
                                final Utils.Consumer<Intent> consumer,
                                final TransActivityDelegate delegate,
                                final Class<?> cls) {
        if (delegate == null) return;
        Intent starter = new Intent(Utils.getApp(), cls);
        starter.putExtra(EXTRA_DELEGATE, delegate);
        if (consumer != null) {
            consumer.accept(starter);
        }
        if (activity == null) {
            starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Utils.getApp().startActivity(starter);
        } else {
            activity.startActivity(starter);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        overridePendingTransition(0, 0);
        Serializable extra = getIntent().getSerializableExtra(EXTRA_DELEGATE);
        if (!(extra instanceof TransActivityDelegate)) {
            super.onCreate(savedInstanceState);
            finish();
            return;
        }
        TransActivityDelegate delegate = (TransActivityDelegate) extra;
        CALLBACK_MAP.put(this, delegate);
        delegate.onCreateBefore(this, savedInstanceState);
        super.onCreate(savedInstanceState);
        delegate.onCreated(this, savedInstanceState);
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

    public abstract static class TransActivityDelegate implements Serializable {
        public void onCreateBefore(@NonNull UtilsTransActivity activity, @Nullable Bundle savedInstanceState) {/**/}

        public void onCreated(@NonNull UtilsTransActivity activity, @Nullable Bundle savedInstanceState) {/**/}

        public void onStarted(@NonNull UtilsTransActivity activity) {/**/}

        public void onDestroy(@NonNull UtilsTransActivity activity) {/**/}

        public void onResumed(@NonNull UtilsTransActivity activity) {/**/}

        public void onPaused(@NonNull UtilsTransActivity activity) {/**/}

        public void onStopped(@NonNull UtilsTransActivity activity) {/**/}

        public void onSaveInstanceState(@NonNull UtilsTransActivity activity, Bundle outState) {/**/}

        public void onRequestPermissionsResult(@NonNull UtilsTransActivity activity, int requestCode, String[] permissions, int[] grantResults) {/**/}

        public void onActivityResult(@NonNull UtilsTransActivity activity, int requestCode, int resultCode, Intent data) {/**/}

        public boolean dispatchTouchEvent(@NonNull UtilsTransActivity activity, MotionEvent ev) {
            return false;
        }
    }
}
