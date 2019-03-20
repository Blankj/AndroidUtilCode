package com.blankj.launcher.pkg

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.view.View
import android.widget.ImageView
import com.blankj.lib.base.BaseDrawerActivity
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.BusUtils
import kotlinx.android.synthetic.main.activity_main.*


/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/09/29
 * desc  : MainActivity
 * ```
 */
class MainActivity : BaseDrawerActivity() {

    override fun initData(bundle: Bundle?) {}

    override fun bindLayout(): Int {
        return R.layout.activity_main
    }

    private var view: ImageView? = null

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        launcherMainCtl.setExpandedTitleColor(Color.TRANSPARENT)
        setSupportActionBar(launcherMainToolbar)
        val toggle = ActionBarDrawerToggle(this,
                mBaseDrawerRootLayout,
                launcherMainToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
        mBaseDrawerRootLayout.addDrawerListener(toggle)
        toggle.syncState()

        BarUtils.setStatusBarColor4Drawer(mBaseDrawerRootLayout, launcherMainFakeStatusBar, Color.TRANSPARENT, false)
        BarUtils.addMarginTopEqualStatusBarHeight(launcherMainToolbar)

        launcherMainCoreUtilBtn.setOnClickListener {
            BusUtils.post<Any>("CoreUtilActivity#start", this)
        }

        launcherMainSubUtilBtn.setOnClickListener {
            BusUtils.post<Any>("SubUtilActivity#start", this)
        }
    }

    override fun doBusiness() {}

    override fun onWidgetClick(view: View) {}

    override fun onBackPressed() {
        ActivityUtils.startHomeActivity()
    }
}
