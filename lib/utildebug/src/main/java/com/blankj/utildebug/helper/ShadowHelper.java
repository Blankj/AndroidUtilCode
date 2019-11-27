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
        ShadowUtils.apply(view, new ShadowUtils.Config()
                .setCircle()
                .setShadowColor(0xc0_ffffff, 0x60_ffffff)
        );
    }

    public static void applyFloatView(View view) {
        ShadowUtils.apply(view, new ShadowUtils.Config().setShadowRadius(SizeUtils.dp2px(8)));
    }

    public static void applyMenu(View view) {
        ShadowUtils.apply(view, new ShadowUtils.Config()
                .setShadowRadius(SizeUtils.dp2px(4))
        );
    }
}
