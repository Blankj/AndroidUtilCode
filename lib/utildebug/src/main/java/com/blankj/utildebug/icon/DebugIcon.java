package com.blankj.utildebug.icon;

import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utildebug.DebugUtils;
import com.blankj.utildebug.R;
import com.blankj.utildebug.config.DebugConfig;
import com.blankj.utildebug.helper.ShadowHelper;
import com.blankj.utildebug.helper.TouchHelper;
import com.blankj.utildebug.menu.DebugMenu;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/08/26
 *     desc  :
 * </pre>
 */
public class DebugIcon extends RelativeLayout {

    private static final DebugIcon INSTANCE = new DebugIcon();

    private int   mIconId;
    private float globalX = DebugConfig.getDebugIconX();
    private float globalY = DebugConfig.getDebugIconY();

    public static DebugIcon getInstance() {
        return INSTANCE;
    }

    public static void setVisibility(boolean isShow) {
        if (INSTANCE == null) return;
        INSTANCE.setVisibility(isShow ? VISIBLE : GONE);
    }

    public DebugIcon() {
        super(DebugUtils.getApp());
        inflate(getContext(), R.layout.du_debug_icon, this);
        int spacing = SizeUtils.dp2px(8);
        setPadding(spacing, spacing, spacing, spacing);

        ShadowHelper.applyDebugIcon(this);
        TouchHelper.applyDrag(this, new TouchHelper.OnDragListener(true, true) {

            @Override
            public void onDown(View view, int x, int y, MotionEvent event) {
            }

            @Override
            public void onMove(View view, int x, int y, int dx, int dy, MotionEvent event) {
                view.setX(Math.min(Math.max(0, view.getX() + dx), appWidth - viewWidth));
                view.setY(Math.min(Math.max(statusBarHeight, view.getY() + dy), appHeight - viewHeight));
            }

            @Override
            public void onStop(View view, int x, int y, MotionEvent event) {
            }
        });

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    PermissionUtils.requestDrawOverlays(new PermissionUtils.SimpleCallback() {
                        @Override
                        public void onGranted() {
                            DebugMenu.getInstance().show();
                        }

                        @Override
                        public void onDenied() {
                            ToastUtils.showLong(R.string.de_permission_tips);
                        }
                    });
                } else {
                    DebugMenu.getInstance().show();
                }
            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setX(globalX);
        setY(globalY);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        globalX = getX();
        globalY = getY();
        DebugConfig.saveDebugIconX(globalX);
        DebugConfig.saveDebugIconY(globalY);
    }

    public void setIconId(final int iconId) {
        ImageView debugPanelIconIv = findViewById(R.id.debugIconIv);
        mIconId = iconId == -1 ? R.drawable.du_ic_icon_default : iconId;
        debugPanelIconIv.setImageResource(mIconId);
    }

    public int getIconId() {
        return mIconId;
    }
}
