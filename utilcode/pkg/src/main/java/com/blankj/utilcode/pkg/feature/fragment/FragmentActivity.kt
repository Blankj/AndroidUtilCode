package com.blankj.utilcode.pkg.feature.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.View
import com.blankj.lib.base.BaseActivity
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.FragmentUtils
import kotlinx.android.synthetic.main.activity_fragment.*
import kotlinx.android.synthetic.main.fragment_child.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 17/02/01
 * desc  : demo about FragmentUtils
 * ```
 */
class FragmentActivity : BaseActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, FragmentActivity::class.java)
            context.startActivity(starter)
        }
    }

    private val mFragments = arrayListOf<Fragment>()
    private var curIndex: Int = 0

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.fragmentNavigation0 -> {
                showCurrentFragment(0)
                return@OnNavigationItemSelectedListener true
            }
            R.id.fragmentNavigation1 -> {
                showCurrentFragment(1)
                return@OnNavigationItemSelectedListener true
            }
            R.id.fragmentNavigation2 -> {
                showCurrentFragment(2)
                return@OnNavigationItemSelectedListener true
            }
            else -> false
        }
    }

    override fun isSwipeBack(): Boolean {
        return true
    }

    override fun initData(bundle: Bundle?) {}

    override fun bindLayout(): Int {
        return R.layout.activity_fragment
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        if (savedInstanceState != null) {
            curIndex = savedInstanceState.getInt("curIndex")
        }
        fragmentNav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        mFragments.add(RootFragment.newInstance())
        mFragments.add(RootFragment.newInstance())
        mFragments.add(RootFragment.newInstance())
        FragmentUtils.add(
                supportFragmentManager,
                mFragments,
                R.id.fragmentContainer,
                arrayOf("RootFragment0", "RootFragment1", "RootFragment2"),
                curIndex
        )
    }

    override fun doBusiness() {}

    override fun onWidgetClick(view: View) {}

    override fun onBackPressed() {
        if (!FragmentUtils.dispatchBackPress(mFragments[curIndex])) {
            super.onBackPressed()
        }
    }

    private fun showCurrentFragment(index: Int) {
        curIndex = index
        FragmentUtils.showHide(index, mFragments)
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putInt("curIndex", curIndex)
    }
}
