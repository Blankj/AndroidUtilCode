package com.blankj.utildebug.debug.tool.appInfo;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ClickUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utildebug.DebugUtils;
import com.blankj.utildebug.R;
import com.blankj.utildebug.base.rv.BaseItem;
import com.blankj.utildebug.base.rv.ItemViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/09/02
 *     desc  :
 * </pre>
 */
public class AppInfoItem extends BaseItem<AppInfoItem> {

    private String          mTitle;
    private String          mContent;
    private OnClickListener mListener;

    private TextView titleTv;
    private TextView contentTv;

    public AppInfoItem(@StringRes int name, String info) {
        this(name, info, null);
    }

    public AppInfoItem(@StringRes int name, String info, OnClickListener listener) {
        super(R.layout.du_item_base_info);
        mTitle = StringUtils.getString(name);
        mContent = info;
        mListener = listener;
    }

    @Override
    public void bind(@NonNull ItemViewHolder holder, int position) {
        titleTv = holder.findViewById(R.id.baseInfoTitleTv);
        contentTv = holder.findViewById(R.id.baseInfoContentTv);

        titleTv.setText(mTitle);
        contentTv.setText(mContent);
        if (mListener != null) {
            ClickUtils.applyPressedBgDark(holder.itemView);
            ClickUtils.applyGlobalDebouncing(holder.itemView, mListener);
            holder.findViewById(R.id.baseInfoGoIv).setVisibility(View.VISIBLE);
        } else {
            holder.itemView.setOnTouchListener(null);
            holder.itemView.setOnClickListener(null);
            holder.findViewById(R.id.baseInfoGoIv).setVisibility(View.GONE);
        }
    }

    public static List<AppInfoItem> getAppInfoItems() {
        final List<AppInfoItem> appInfoItems = new ArrayList<>();
        appInfoItems.add(new AppInfoItem(R.string.du_app_info_pkg_name, AppUtils.getAppPackageName()));
        appInfoItems.add(new AppInfoItem(R.string.du_app_info_version_name, AppUtils.getAppVersionName()));
        appInfoItems.add(new AppInfoItem(R.string.du_app_info_version_code, String.valueOf(AppUtils.getAppVersionCode())));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            appInfoItems.add(new AppInfoItem(R.string.du_app_info_min_sdk_version, String.valueOf(DebugUtils.getApp().getApplicationInfo().minSdkVersion)));
        }
        appInfoItems.add(new AppInfoItem(R.string.du_app_info_target_sdk_version, String.valueOf(DebugUtils.getApp().getApplicationInfo().targetSdkVersion)));
        appInfoItems.add(new AppInfoItem(R.string.du_app_info_open_app_info_page, "", new OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.launchAppDetailsSettings();
            }
        }));
        return appInfoItems;
    }
}
