package com.blankj.launcher.pkg

import android.graphics.Color
import android.opengl.Visibility
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import com.blankj.base.BaseDrawerActivity
import com.blankj.launcher.R
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.BusUtils
import com.blankj.utilcode.util.ImageUtils
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

    override fun initData(bundle: Bundle?) {

    }

    override fun bindLayout(): Int {
        return R.layout.activity_main
    }

    private var view: ImageView? = null

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        launcherMainCtl.setExpandedTitleColor(Color.TRANSPARENT)
        setSupportActionBar(launcherMainToolbar)
        val toggle = ActionBarDrawerToggle(this,
                mDrawerRootLayout,
                launcherMainToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
        mDrawerRootLayout.addDrawerListener(toggle)
        toggle.syncState()

        BarUtils.setStatusBarAlpha4Drawer(this, mDrawerRootLayout, launcherMainFakeStatusBar, 0, false)
        BarUtils.addMarginTopEqualStatusBarHeight(launcherMainToolbar)

        launcherMainCoreUtilBtn.setOnClickListener {
            BusUtils.post<Any>("CoreUtilActivity#start", this)
        }

        launcherMainSubUtilBtn.setOnClickListener {
            BusUtils.post<Any>("showDialog")
//            BusUtils.post<Any>("SubUtilActivity#start", this)
        }


        contentView.post(Runnable {
            val topActivity = ActivityUtils.getTopActivity() ?: return@Runnable
            val decorView = topActivity.window.decorView as ViewGroup
            val bitmapForView = ImageUtils.view2Bitmap(decorView)

            val bitmap = ImageUtils.fastBlur(bitmapForView, 0.125f, 2f, true, true)

            view = ImageView(topActivity)
            view?.setImageBitmap(bitmap)
            view?.tag = "blur"
            decorView.addView(view, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
            view?.visibility = View.GONE
        })

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

    }

    override fun doBusiness() {

    }

    override fun onWidgetClick(view: View) {

    }

    override fun onBackPressed() {
        ActivityUtils.startHomeActivity()
    }

    override fun onResume() {
        view?.visibility = View.GONE
        super.onResume()
    }

    override fun onPause() {
        view?.visibility = View.VISIBLE
//        val topActivity = ActivityUtils.getTopActivity() ?: return
//        val decorView = topActivity.window.decorView as ViewGroup
//        val bitmapForView = ImageUtils.view2Bitmap(decorView)
//
//        val bitmap = ImageUtils.fastBlur(bitmapForView, 0.125f, 2f, true, true)
//
//        val view = ImageView(topActivity)
//        view.setImageBitmap(bitmap)
//        view.tag = "blur"
//        decorView.addView(view, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
//        val windowManager = topActivity.windowManager
//        val mParams = WindowManager.LayoutParams()
//
//        mParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL
//        mParams.height = WindowManager.LayoutParams.MATCH_PARENT
//        mParams.width = WindowManager.LayoutParams.MATCH_PARENT
//
//        val view = ImageView(topActivity)
//        view.setImageBitmap(bitmap)
//        view.tag = "blur"
//        windowManager.addView(view, mParams)
        super.onPause()
    }
}
