package com.blankj.androidutilcode.feature.sub.brightness;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.base.BaseActivity;
import com.blankj.subutil.util.BrightnessUtils;
import com.blankj.subutil.util.Utils;
import com.blankj.utilcode.util.SpanUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/02/08
 *     desc  :
 * </pre>
 */
public class BrightnessActivity extends BaseActivity {

    TextView     tvBrightness;
    SeekBar      sbBrightness;
    TextView     tvWindowBrightness;
    SeekBar      sbWindowBrightness;
    ToggleButton tbAutoBrightness;

    private SeekBar.OnSeekBarChangeListener brightnessChangeListener
            = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            BrightnessUtils.setBrightness(progress);
            updateBrightness();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private SeekBar.OnSeekBarChangeListener windowBrightnessChangeListener
            = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            BrightnessUtils.setWindowBrightness(getWindow(), progress);
            updateWindowBrightness();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    public static void start(Context context) {
        Intent starter = new Intent(context, BrightnessActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_brightness;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        tvBrightness = findViewById(R.id.tv_brightness);
        sbBrightness = findViewById(R.id.sb_brightness);
        tvWindowBrightness = findViewById(R.id.tv_window_brightness);
        sbWindowBrightness = findViewById(R.id.sb_window_brightness);
        tbAutoBrightness = findViewById(R.id.tb_set_auto_brightness_enable);

        sbBrightness.setProgress(BrightnessUtils.getBrightness());
        sbBrightness.setOnSeekBarChangeListener(brightnessChangeListener);
        updateBrightness();

        sbWindowBrightness.setProgress(BrightnessUtils.getWindowBrightness(getWindow()));
        sbWindowBrightness.setOnSeekBarChangeListener(windowBrightnessChangeListener);
        updateWindowBrightness();

        tbAutoBrightness.setChecked(BrightnessUtils.isAutoBrightnessEnabled());
        tbAutoBrightness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                        && !Settings.System.canWrite(Utils.getApp())) {
                    Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                    intent.setData(Uri.parse("package:" + Utils.getApp().getPackageName()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Utils.getApp().startActivity(intent);
                }
            }
        });

        tbAutoBrightness.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolean isSuccess = BrightnessUtils.setAutoBrightnessEnabled(isChecked);
                if (!isSuccess) {
                    tbAutoBrightness.toggle();
                }
            }
        });
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {

    }

    private void updateBrightness() {
        tvBrightness.setText(new SpanUtils()
                .append(String.valueOf(BrightnessUtils.getBrightness()))
                .create()
        );
    }

    private void updateWindowBrightness() {
        tvWindowBrightness.setText(new SpanUtils()
                .append(String.valueOf(BrightnessUtils.getWindowBrightness(getWindow())))
                .create()
        );
    }
}
