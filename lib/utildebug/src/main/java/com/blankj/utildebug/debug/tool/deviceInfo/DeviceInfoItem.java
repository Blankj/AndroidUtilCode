package com.blankj.utildebug.debug.tool.deviceInfo;

import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ArrayUtils;
import com.blankj.utilcode.util.ClickUtils;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
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
public class DeviceInfoItem extends BaseItem<DeviceInfoItem> {

    private String          mTitle;
    private String          mContent;
    private OnClickListener mListener;

    private TextView titleTv;
    private TextView contentTv;

    public DeviceInfoItem(@StringRes int name, String info) {
        this(name, info, null);
    }

    public DeviceInfoItem(@StringRes int name, String info, OnClickListener listener) {
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

    public static List<DeviceInfoItem> getAppInfoItems() {
        List<DeviceInfoItem> appInfoItems = new ArrayList<>();
        appInfoItems.add(new DeviceInfoItem(R.string.du_device_info_name, Build.MANUFACTURER + " " + Build.MODEL));
        appInfoItems.add(new DeviceInfoItem(R.string.du_device_info_android_version, DeviceUtils.getSDKVersionName() + " (" + DeviceUtils.getSDKVersionCode() + ")"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            appInfoItems.add(new DeviceInfoItem(R.string.du_device_info_adb_enabled, String.valueOf(DeviceUtils.isAdbEnabled())));
        }
        appInfoItems.add(new DeviceInfoItem(R.string.du_device_info_support_abis, ArrayUtils.toString(DeviceUtils.getABIs())));
        appInfoItems.add(new DeviceInfoItem(R.string.du_device_info_screen_info, getScreenInfo()));
        appInfoItems.add(new DeviceInfoItem(R.string.du_device_info_open_development_settings_page, "", new OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.startActivity(new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS));
            }
        }));
        return appInfoItems;
    }

    private static String getScreenInfo() {
        return "width=" + ScreenUtils.getScreenWidth() +
                ", height=" + ScreenUtils.getScreenHeight() +
                ", density=" + ScreenUtils.getScreenDensity();
    }
}
