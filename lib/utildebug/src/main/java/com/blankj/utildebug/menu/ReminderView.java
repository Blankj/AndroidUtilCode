package com.blankj.utildebug.menu;

import android.widget.Switch;

import com.blankj.utildebug.R;
import com.blankj.utildebug.base.view.BaseContentFloatView;
import com.blankj.utildebug.config.DebugConfig;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/09/10
 *     desc  :
 * </pre>
 */
public class ReminderView extends BaseContentFloatView<ReminderView> {

    private Switch reminderNoMoreSwitch;

    @Override
    public int bindTitle() {
        return R.string.du_reminder;
    }

    @Override
    public int bindContentLayout() {
        return R.layout.du_reminder_view;
    }

    @Override
    public void initContentView() {
        reminderNoMoreSwitch = findViewById(R.id.reminderNoMoreSwitch);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (reminderNoMoreSwitch.isChecked()) {
            DebugConfig.saveNoMoreReminder();
        }
    }
}
