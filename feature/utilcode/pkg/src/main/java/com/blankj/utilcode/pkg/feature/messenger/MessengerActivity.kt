package com.blankj.utilcode.pkg.feature.messenger

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.common.CommonTitleActivity
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.MessengerUtils
import com.blankj.utilcode.util.SnackbarUtils
import kotlinx.android.synthetic.main.activity_messenger.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2019/03/12
 * desc  : demo about MessengerUtils
 * ```
 */
class MessengerActivity : CommonTitleActivity() {

    companion object {
        const val MESSENGER_KEY = "MessengerActivity"

        fun start(context: Context) {
            val starter = Intent(context, MessengerActivity::class.java)
            context.startActivity(starter)
            MessengerUtils.register()
        }

        val BUNDLE = Bundle()

        init {
            BUNDLE.putString(MESSENGER_KEY, "MessengerActivity")
        }
    }

    override fun bindTitle(): CharSequence {
        return getString(R.string.demo_messenger)
    }

    override fun initData(bundle: Bundle?) {}

    override fun bindLayout(): Int {
        return R.layout.activity_messenger
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View?) {
        applyDebouncingClickListener(
                messengerPost2MainServerBtn,
                messengerStartRemoteBtn
        )
    }

    override fun doBusiness() {
        MessengerUtils.subscribe(MESSENGER_KEY, object : MessengerUtils.MessageCallback {
            override fun messageCall(data: Bundle?) {
                SnackbarUtils.with(mContentView)
                        .setMessage(data!!.getString(MESSENGER_KEY))
                        .setDuration(SnackbarUtils.LENGTH_INDEFINITE)
                        .show()
            }
        })
    }

    override fun onDebouncingClick(view: View) {
        when (view.id) {
            R.id.messengerPost2MainServerBtn -> {
                MessengerUtils.post(MESSENGER_KEY, BUNDLE)
            }
            R.id.messengerStartRemoteBtn -> MessengerRemoteActivity.start(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        MessengerUtils.unregister()
    }
}
