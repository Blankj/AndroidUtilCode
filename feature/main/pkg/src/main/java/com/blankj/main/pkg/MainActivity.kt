package com.blankj.main.pkg

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.view.View
import com.blankj.common.activity.CommonActivity
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemClick
import com.blankj.subutil.export.api.SubUtilApi
import com.blankj.utilcode.export.api.UtilCodeApi
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ApiUtils
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.CollectionUtils
import kotlinx.android.synthetic.main.activity_main.*


/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/09/29
 * desc  : MainActivity
 * ```
 */
class MainActivity : CommonActivity() {

    override fun isSwipeBack(): Boolean {
        return false
    }

    override fun bindDrawer(): Boolean {
        return true
    }

    override fun bindLayout(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setBackgroundDrawable(null)
        super.onCreate(savedInstanceState)
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View?) {
        super.initView(savedInstanceState, contentView)
        setCommonItems(mainRv, CollectionUtils.newArrayList<CommonItem<*>>(
                CommonItemClick(R.string.core_util, true) {
                    ApiUtils.getApi(UtilCodeApi::class.java).startUtilCodeActivity(this)
                },
                CommonItemClick(R.string.sub_util, true) {
                    ApiUtils.getApi(SubUtilApi::class.java).startSubUtilActivity(this)
                }
        ))

        launcherMainCtl.setExpandedTitleColor(Color.TRANSPARENT)
        setSupportActionBar(launcherMainToolbar)
        val toggle = ActionBarDrawerToggle(this,
                drawerView.mBaseDrawerRootLayout,
                launcherMainToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
        drawerView.mBaseDrawerRootLayout.addDrawerListener(toggle)
        toggle.syncState()

        BarUtils.setStatusBarColor4Drawer(drawerView.mBaseDrawerRootLayout, launcherMainFakeStatusBar, Color.TRANSPARENT, false)
        BarUtils.addMarginTopEqualStatusBarHeight(launcherMainToolbar)
    }

    override fun onBackPressed() {
        ActivityUtils.startHomeActivity()
    }
}
