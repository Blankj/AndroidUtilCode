package com.blankj.main.pkg

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.view.View
import android.widget.ImageView
import com.blankj.common.CommonDrawerActivity
import com.blankj.subutil.export.api.SubUtilApi
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.export.api.UtilCodeApi
import com.blankj.utilcode.util.*
import kotlinx.android.synthetic.main.activity_main.*


/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/09/29
 * desc  : MainActivity
 * ```
 */
class MainActivity : CommonDrawerActivity() {

    override fun initData(bundle: Bundle?) {

        PermissionUtils.permission(PermissionConstants.CALENDAR)
                .callback(object : PermissionUtils.SimpleCallback {
                    override fun onGranted() {
                        LogUtils.e()
                    }

                    override fun onDenied() {
                        LogUtils.e()
                    }
                })
    }

    override fun bindLayout(): Int {
        return R.layout.activity_main
    }

    private var view: ImageView? = null

    override fun initView(savedInstanceState: Bundle?, contentView: View?) {
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

        applyDebouncingClickListener(
                launcherMainCoreUtilBtn,
                launcherMainSubUtilBtn
        )
    }

    override fun doBusiness() {}

    override fun onDebouncingClick(view: View) {
        when (view.id) {
            R.id.launcherMainCoreUtilBtn -> {
                ApiUtils.getApi(UtilCodeApi::class.java).startUtilCodeActivity(this)
            }
            R.id.launcherMainSubUtilBtn -> {
                ApiUtils.getApi(SubUtilApi::class.java).startSubUtilActivity(this)
            }
        }
    }

    override fun onBackPressed() {
        ActivityUtils.startHomeActivity()
    }
}
