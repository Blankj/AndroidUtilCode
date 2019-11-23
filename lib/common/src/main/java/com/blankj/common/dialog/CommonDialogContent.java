package com.blankj.common.dialog;

import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.base.dialog.BaseDialogFragment;
import com.blankj.base.dialog.DialogLayoutCallback;
import com.blankj.common.R;
import com.blankj.utilcode.util.ClickUtils;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/11/18
 *     desc  :
 * </pre>
 */
public class CommonDialogContent extends BaseDialogFragment {

    private RelativeLayout cdcTitleRl;
    private TextView       cdcTitleTv;
    private RelativeLayout cdcContentRl;
    private TextView       cdcContentTv;
    private RelativeLayout cdcBottomRl;
    private TextView       cdcBottomPositiveTv;
    private TextView       cdcBottomNegativeTv;

    public CommonDialogContent init(FragmentActivity activity, final CharSequence title, final CharSequence content,
                                    final Pair<CharSequence, View.OnClickListener> positiveBtnAction,
                                    final Pair<CharSequence, View.OnClickListener> negativeBtnAction) {
        super.init(activity, new DialogLayoutCallback() {
            @Override
            public int bindTheme() {
                return R.style.CommonContentDialogStyle;
            }

            @Override
            public int bindLayout() {
                return R.layout.common_dialog_content;
            }

            @Override
            public void initView(final BaseDialogFragment dialog, View contentView) {
                cdcTitleRl = contentView.findViewById(R.id.cdcTitleRl);
                cdcTitleTv = contentView.findViewById(R.id.cdcTitleTv);
                cdcContentRl = contentView.findViewById(R.id.cdcContentRl);
                cdcContentTv = contentView.findViewById(R.id.cdcContentTv);
                cdcBottomRl = contentView.findViewById(R.id.cdcBottomRl);
                cdcBottomPositiveTv = contentView.findViewById(R.id.cdcBottomPositiveTv);
                cdcBottomNegativeTv = contentView.findViewById(R.id.cdcBottomNegativeTv);

                if (TextUtils.isEmpty(title)) {
                    cdcTitleRl.setVisibility(View.GONE);
                } else {
                    cdcTitleTv.setText(title);
                }
                if (TextUtils.isEmpty(content)) {
                    cdcContentRl.setVisibility(View.GONE);
                } else {
                    cdcContentTv.setText(content);
                }

                if (positiveBtnAction == null && negativeBtnAction == null) {
                    cdcBottomRl.setVisibility(View.GONE);
                } else {
                    if (positiveBtnAction != null) {
                        ClickUtils.applyPressedBgDark(cdcBottomPositiveTv);
                        cdcBottomPositiveTv.setText(positiveBtnAction.first);
                        cdcBottomPositiveTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dismiss();
                                positiveBtnAction.second.onClick(v);
                            }
                        });
                    }
                    if (negativeBtnAction != null) {
                        ClickUtils.applyPressedBgDark(cdcBottomNegativeTv);
                        cdcBottomNegativeTv.setText(negativeBtnAction.first);
                        cdcBottomNegativeTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dismiss();
                                negativeBtnAction.second.onClick(v);
                            }
                        });
                    }
                }
            }

            @Override
            public void setWindowStyle(Window window) {
            }

            @Override
            public void onCancel(BaseDialogFragment dialog) {

            }

            @Override
            public void onDismiss(BaseDialogFragment dialog) {

            }
        });
        return this;
    }
}
