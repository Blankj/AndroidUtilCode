package com.blankj.utilcode.pkg.feature.blur;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blankj.lib.base.BaseBackActivity;
import com.blankj.utilcode.pkg.R;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2018/12/13
 *     desc  :
 * </pre>
 */
public class BlurActivity extends BaseBackActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, BlurActivity.class);
        starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(starter);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int bindLayout() {
//        this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
        return R.layout.activity_blur;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState, @NotNull View contentView) {
        setTitle(R.string.demo_blur);
    }

    @Override
    public void doBusiness() {
        AppUtils.registerAppStatusChangedListener(this, new Utils.OnAppStatusChangedListener() {
            @Override
            public void onForeground() {
                LogUtils.e();
                BlurActivity.leave(false);
            }

            @Override
            public void onBackground() {
                LogUtils.e();
                BlurActivity.leave(true);
            }
        });
    }

    @Override
    protected void onDestroy() {
        AppUtils.unregisterAppStatusChangedListener(this);
        super.onDestroy();
    }

    @Override
    public void onWidgetClick(@NotNull View view) {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        if (hasFocus) {
//            leave(false);
//            LogUtils.e();
//        } else {
//            leave(true);
//            LogUtils.e();
//        }
//        super.onWindowFocusChanged(hasFocus);
//    }


    public static void leave(boolean isBlur) {
        Activity topActivity = ActivityUtils.getTopActivity();
        if (topActivity == null) return;
        ViewGroup decorView = (ViewGroup) topActivity.getWindow().getDecorView();
        View blur = decorView.findViewWithTag("blur");
        if (blur == null) {
            if (isBlur) {
                Bitmap bitmapForView = ImageUtils.view2Bitmap(decorView);

                Bitmap bitmap = ImageUtils.fastBlur(bitmapForView, 0.125f, 2f, true);

                ImageView view = new ImageView(topActivity);
                view.setImageBitmap(bitmap);
                view.setTag("blur");
                decorView.addView(
                        view,
                        new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                );
//                TransparentActivity.start();
            }
        } else {
            if (!isBlur) {
                decorView.removeView(blur);
            }
        }
    }

}
