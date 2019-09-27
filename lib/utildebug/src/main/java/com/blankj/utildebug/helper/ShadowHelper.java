package com.blankj.utildebug.helper;

import android.view.View;

import com.blankj.utilcode.util.ShadowUtils;
import com.blankj.utilcode.util.SizeUtils;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/09/16
 *     desc  :
 * </pre>
 */
public class ShadowHelper {

    public static void applyDebugIcon(View view) {
        ShadowUtils.apply(view, new ShadowUtils.Builder()
                .setDrawableRadius(SizeUtils.getMeasuredWidth(view) / 2)
                .setShadowRadius(SizeUtils.dp2px(8))
                .setShadowColor(0xb0_ffffff, 0xb0_000000)
        );
    }

    public static void applyFloatView(View view) {
        ShadowUtils.apply(view, new ShadowUtils.Builder()
                .setDrawableRadius(SizeUtils.dp2px(8))
                .setShadowRadius(SizeUtils.dp2px(8))
                .setShadowColor(0xb0_000000)
        );
    }

    public static void applyMenu(View view) {
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//        ShadowUtils.apply(view, new ShadowUtils.Builder()
//                .setDrawableRadius(SizeUtils.dp2px(4))
//                .setShadowRadius(SizeUtils.dp2px(4), SizeUtils.dp2px(4))
//                .setShadowColor(0xb0_000000)
//        );
    }
}
