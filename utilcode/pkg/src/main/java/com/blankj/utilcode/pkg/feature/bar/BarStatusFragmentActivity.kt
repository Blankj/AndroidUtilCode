package com.blankj.utilcode.pkg.feature.bar

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import com.blankj.lib.base.BaseActivity
import com.blankj.utilcode.pkg.R
import kotlinx.android.synthetic.main.activity_bar_status_fragment.*
import java.util.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2017/05/27
 * desc  : demo about BarUtils
 * ```
 */
class BarStatusFragmentActivity : BaseActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, BarStatusFragmentActivity::class.java)
            context.startActivity(starter)
        }
    }

    private val itemIds = intArrayOf(
            R.id.navigation_color,
            R.id.navigation_alpha,
            R.id.navigation_image_view,
            R.id.navigation_custom
    )

    private val mFragmentList = ArrayList<Fragment>()

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener l@{ item ->
        when (item.itemId) {
            R.id.navigation_color -> {
                statusBarVp.currentItem = 0
                return@l true
            }
            R.id.navigation_alpha -> {
                statusBarVp.currentItem = 1
                return@l true
            }
            R.id.navigation_image_view -> {
                statusBarVp.currentItem = 2
                return@l true
            }
            R.id.navigation_custom -> {
                statusBarVp.currentItem = 3
                return@l true
            }
            else -> false
        }
    }

    override fun initData(bundle: Bundle?) {

    }

    override fun bindLayout(): Int {
        return R.layout.activity_bar_status_fragment
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        mFragmentList.add(BarStatusColorFragment.newInstance())
        mFragmentList.add(BarStatusAlphaFragment.newInstance())
        mFragmentList.add(BarStatusImageViewFragment.newInstance())
        mFragmentList.add(BarStatusCustomFragment.newInstance())

        statusBarVp.offscreenPageLimit = 3
        statusBarVp.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return mFragmentList[position]
            }

            override fun getCount(): Int {
                return mFragmentList.size
            }
        }

        statusBarVp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                statusBarNav.selectedItemId = itemIds[position]
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        statusBarNav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun doBusiness() {

    }

    override fun onWidgetClick(view: View) {

    }
}
