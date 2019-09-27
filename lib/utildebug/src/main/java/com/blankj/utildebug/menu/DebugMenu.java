package com.blankj.utildebug.menu;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.blankj.utildebug.R;
import com.blankj.utildebug.base.rv.BaseItemAdapter;
import com.blankj.utildebug.base.view.BaseContentFloatView;
import com.blankj.utildebug.config.DebugConfig;
import com.blankj.utildebug.debug.IDebug;
import com.blankj.utildebug.icon.DebugIcon;

import java.util.List;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/08/29
 *     desc  :
 * </pre>
 */
public class DebugMenu extends BaseContentFloatView<DebugMenu> {

    private static final DebugMenu INSTANCE = new DebugMenu();

    private List<IDebug> mDebugs;

    private RecyclerView debugMenuRv;

    public static DebugMenu getInstance() {
        return INSTANCE;
    }

    @Override
    public int bindTitle() {
        return R.string.du_menus;
    }

    @Override
    public int bindContentLayout() {
        return R.layout.du_debug_menu;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        DebugIcon.setVisibility(false);
        if (!DebugConfig.isNoMoreReminder()) {
            new ReminderView().show();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        DebugIcon.setVisibility(true);
        super.onDetachedFromWindow();
    }

    @Override
    public void initContentView() {
        setSwipeBackEnabled(false);

        debugMenuRv = findViewById(R.id.debugMenuRv);
        BaseItemAdapter<DebugMenuItem> adapter = new BaseItemAdapter<>();
        adapter.setItems(DebugMenuItem.getDebugMenuItems(mDebugs));
        debugMenuRv.setAdapter(adapter);
        debugMenuRv.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void setDebugs(List<IDebug> debugs) {
        mDebugs = debugs;
    }
}
