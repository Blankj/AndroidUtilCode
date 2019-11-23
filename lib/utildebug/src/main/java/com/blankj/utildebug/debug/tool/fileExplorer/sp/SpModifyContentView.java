package com.blankj.utildebug.debug.tool.fileExplorer.sp;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ClickUtils;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utildebug.R;
import com.blankj.utildebug.base.view.BaseContentView;
import com.blankj.utildebug.base.view.FloatToast;
import com.blankj.utildebug.debug.tool.fileExplorer.FileExplorerFloatView;
import com.blankj.utildebug.helper.SpHelper;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/09/09
 *     desc  :
 * </pre>
 */
public class SpModifyContentView extends BaseContentView<FileExplorerFloatView> {

    private SPUtils mSPUtils;
    private String  mKey;
    private Object  mValue;
    private Class   mClass;

    private TextView spModifyTitleTv;
    private EditText spModifyEt;
    private TextView spModifyCancelTv;
    private TextView spModifyYesTv;

    public static void show(FileExplorerFloatView floatView, SPUtils spUtils, String key, Object value) {
        new SpModifyContentView(spUtils, key, value).attach(floatView, true);
    }

    public SpModifyContentView(SPUtils spUtils, String key, Object value) {
        mSPUtils = spUtils;
        mKey = key;
        mValue = value;
        mClass = value.getClass();
    }

    @Override
    public int bindLayout() {
        return R.layout.du_debug_file_explorer_sp_modify;
    }

    @Override
    public void onAttach() {
        spModifyTitleTv = findViewById(R.id.spModifyTitleTv);
        spModifyEt = findViewById(R.id.spModifyEt);
        spModifyCancelTv = findViewById(R.id.spModifyCancelTv);
        spModifyYesTv = findViewById(R.id.spModifyYesTv);

        SpanUtils.with(spModifyTitleTv)
                .append(mKey)
                .append("(" + SpHelper.getSpClassName(mClass) + ")").setForegroundColor(ColorUtils.getColor(R.color.loveGreen))
                .create();
        spModifyEt.setText(mValue.toString());

        ClickUtils.applyPressedViewScale(spModifyCancelTv, spModifyYesTv);

        spModifyCancelTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getFloatView().back();
            }
        });

        spModifyYesTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = spModifyEt.getText().toString();
                boolean isSuccess = SpHelper.putValue(mSPUtils, mKey, val, mClass);
                if (isSuccess) {
                    getFloatView().back();
                } else {
                    FloatToast.showShort("Type is wrong.");
                }
            }
        });
    }
}
