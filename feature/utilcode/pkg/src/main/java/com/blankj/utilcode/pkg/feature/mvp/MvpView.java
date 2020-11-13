package com.blankj.utilcode.pkg.feature.mvp;

import android.text.Layout;
import android.view.View;
import android.widget.TextView;

import com.blankj.base.mvp.BaseView;
import com.blankj.utilcode.pkg.R;
import com.blankj.utilcode.util.ClickUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.ToastUtils;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/11/26
 *     desc  :
 * </pre>
 */
public class MvpView extends BaseView<MvpView>
        implements MvpMvp.View {

    private TextView mvpTv;
    private TextView mvpMeasureWidthTv;
    private int      i = 0;

    public MvpView(MvpActivity activity) {
        super(activity);
        mvpTv = activity.findViewById(R.id.mvpUpdateTv);
        ClickUtils.applyPressedBgDark(mvpTv);
        mvpTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter(MvpPresenter.class).updateMsg();
            }
        });

        mvpMeasureWidthTv = activity.findViewById(R.id.mvpMeasureWidthTv);

        measure();
    }

    private void measure() {
        ThreadUtils.runOnUiThreadDelayed(new Runnable() {
            @Override
            public void run() {
                float textWidth = Layout.getDesiredWidth(mvpMeasureWidthTv.getText(), mvpMeasureWidthTv.getPaint()) + SizeUtils.dp2px(16);
                float textWidth2 = mvpMeasureWidthTv.getPaint().measureText(mvpMeasureWidthTv.getText().toString()) + SizeUtils.dp2px(16);
                LogUtils.i(mvpMeasureWidthTv.getWidth(), textWidth, textWidth2);
                mvpMeasureWidthTv.setText(mvpMeasureWidthTv.getText().toString() + i);
                measure();
            }
        }, 1000);
    }

    @Override
    public void setLoadingVisible(boolean visible) {
        final MvpActivity activity = getActivity();
        if (visible) {
            activity.showLoading(new Runnable() {
                @Override
                public void run() {
                    activity.finish();
                }
            });
        } else {
            activity.dismissLoading();
        }
    }

    @Override
    public void showMsg(CharSequence msg) {
        ToastUtils.showLong(msg);
    }
}
