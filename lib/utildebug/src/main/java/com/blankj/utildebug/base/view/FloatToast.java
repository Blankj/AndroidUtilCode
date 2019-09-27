package com.blankj.utildebug.base.view;

import android.annotation.SuppressLint;
import android.support.annotation.IntDef;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utildebug.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/09/10
 *     desc  :
 * </pre>
 */
public class FloatToast extends BaseFloatView {

    public static final int SUCCESS = 0;
    public static final int WARNING = 1;
    public static final int ERROR   = 2;

    @IntDef({SUCCESS, WARNING, ERROR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    private static final FloatToast INSTANCE = new FloatToast();

    private static final Runnable DISMISS_RUNNABLE = new Runnable() {
        @Override
        public void run() {
            INSTANCE.dismiss();
        }
    };

    @Type
    private int    mType;
    private String mMsg;

    private ImageView toastIconIv;
    private TextView  toastMsgTv;

    private FloatToast() {
    }

    @Override
    public int bindLayout() {
        return R.layout.du_float_toast;
    }

    @Override
    public void initContentView() {
        toastIconIv = findViewById(R.id.toastIconIv);
        toastMsgTv = findViewById(R.id.toastMsgTv);
    }

    @Override
    protected void onCreateLayoutParams() {
        super.onCreateLayoutParams();

        mLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mLayoutParams.windowAnimations = android.R.style.Animation_Toast;
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        mLayoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
        mLayoutParams.y = SizeUtils.dp2px(64);
    }

    @Override
    public void show() {
        super.show();
        if (mType < 0 || mType > 2) {
            toastIconIv.setVisibility(GONE);
        } else {
            toastIconIv.setVisibility(VISIBLE);
            if (mType == SUCCESS) {
                toastIconIv.setImageResource(R.drawable.du_ic_toast_success);
            } else if (mType == WARNING) {
                toastIconIv.setImageResource(R.drawable.du_ic_toast_warn);
            } else {
                toastIconIv.setImageResource(R.drawable.du_ic_toast_error);
            }
        }
        toastMsgTv.setText(mMsg);
    }

    private void setType(int type) {
        mType = type;
    }

    private void setMsg(String msg) {
        mMsg = msg == null ? "" : msg;
    }

    public static void showShort(String msg) {
        show(msg, 2000);
    }

    public static void showShort(@Type int type, String msg) {
        show(type, msg, 2000);
    }

    public static void showLong(String msg) {
        show(msg, 3500);
    }

    public static void showLong(@Type int type, String msg) {
        show(type, msg, 3500);
    }

    @SuppressLint("WrongConstant")
    public static void show(String msg, long millis) {
        show(-1, msg, millis);
    }

    public static void show(@Type int type, String msg, long millis) {
        INSTANCE.removeCallbacks(DISMISS_RUNNABLE);
        INSTANCE.dismiss();
        INSTANCE.setType(type);
        INSTANCE.setMsg(msg);
        INSTANCE.show();
        INSTANCE.postDelayed(DISMISS_RUNNABLE, millis);
    }
}
