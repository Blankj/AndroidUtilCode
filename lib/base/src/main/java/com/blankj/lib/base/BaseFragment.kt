package com.blankj.lib.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.AntiShakeUtils

/**
 * ```
 * author: blankj
 * blog  : http://blankj.com
 * time  : 2018/11/16
 * desc  : base about v4-fragment
 * ```
 */
abstract class BaseFragment : Fragment(), IBaseView {

    companion object {
        private const val TAG = "BaseFragment"
        private const val STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN"
    }

    protected lateinit var mActivity: Activity
    protected lateinit var mInflater: LayoutInflater
    protected lateinit var mContentView: View

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mActivity = context as Activity;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate: ")
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            val isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN)
            fragmentManager?.beginTransaction()?.let {
                if (isSupportHidden) {
                    it.hide(this).commitAllowingStateLoss()
                } else {
                    it.show(this).commitAllowingStateLoss()
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView: ")
        mInflater = inflater
        setRootLayout(bindLayout())
        return mContentView
    }

    override fun setRootLayout(layoutId: Int) {
        if (layoutId <= 0) return
        mContentView = mInflater.inflate(layoutId, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated: ")
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments
        initData(bundle)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated: ")
        super.onActivityCreated(savedInstanceState)
        initView(savedInstanceState, mContentView)
        doBusiness()
    }

    override fun onDestroyView() {
        Log.d(TAG, "onDestroyView: ")
        (mContentView.parent as ViewGroup).removeView(mContentView)
        super.onDestroyView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.d(TAG, "onSaveInstanceState: ")
        super.onSaveInstanceState(outState)
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden)
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy: ")
        super.onDestroy()
    }

    override fun onClick(view: View) {
        if (AntiShakeUtils.isValid(view)) {
            onWidgetClick(view)
        }
    }

    fun <T : View> findViewById(@IdRes id: Int): T {
        return mContentView.findViewById(id)
    }
}
