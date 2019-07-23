package com.blankj.subutil.pkg.feature.appStore

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.common.CommonTitleActivity
import com.blankj.subutil.pkg.R
import com.blankj.subutil.util.AppStoreUtils
import com.blankj.utilcode.util.ActivityUtils
import kotlinx.android.synthetic.main.activity_app_store.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 17/02/01
 * desc  : demo about PinyinUtils
 * ```
 */
class AppStoreActivity : CommonTitleActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, AppStoreActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitle(): CharSequence {
        return getString(R.string.demo_app_store)
    }

    override fun initData(bundle: Bundle?) {}

    override fun bindLayout(): Int {
        return R.layout.activity_app_store
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View?) {
        applyDebouncingClickListener(appStoreSystem)
    }

    override fun doBusiness() {}

    override fun onDebouncingClick(view: View) {
        when (view.id) {
            R.id.appStoreSystem -> {
                AppStoreUtils.getAppStoreIntent("com.tencent.mm")?.apply {
                    ActivityUtils.startActivity(this)
                }
            }
        }
    }
}
