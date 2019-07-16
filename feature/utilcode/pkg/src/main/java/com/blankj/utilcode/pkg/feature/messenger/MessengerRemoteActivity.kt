package com.blankj.utilcode.pkg.feature.messenger

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.common.CommonTitleActivity
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.MessengerUtils
import com.blankj.utilcode.util.SnackbarUtils
import kotlinx.android.synthetic.main.activity_messenger_remote.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2019/03/12
 * desc  : demo about MessengerUtils
 * ```
 */
class MessengerRemoteActivity : CommonTitleActivity() {

    companion object {
        const val MESSENGER_KEY = "MessengerRemoteActivity"

        fun start(context: Context) {
            val starter = Intent(context, MessengerRemoteActivity::class.java)
            context.startActivity(starter)
        }

        val BUNDLE = Bundle()

        init {
            BUNDLE.putString(MESSENGER_KEY, "MessengerRemoteActivity")
        }
    }

    override fun bindTitle(): CharSequence {
        return getString(R.string.demo_messenger)
    }

    override fun initData(bundle: Bundle?) {}

    override fun bindLayout(): Int {
        return R.layout.activity_messenger_remote
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View?) {
        applyDebouncingClickListener(
                messengerRegisterRemoteClient,
                messengerUnregisterRemoteClient,
                messengerRemotePost2SelfClientBtn,
                messengerRemotePost2MainClientBtn
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
            R.id.messengerRegisterRemoteClient -> MessengerUtils.register()
            R.id.messengerUnregisterRemoteClient -> MessengerUtils.unregister()
            R.id.messengerRemotePost2SelfClientBtn -> MessengerUtils.post(MESSENGER_KEY, BUNDLE)
            R.id.messengerRemotePost2MainClientBtn -> MessengerUtils.post(MessengerActivity.MESSENGER_KEY, MessengerActivity.BUNDLE)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        MessengerUtils.unsubscribe(MESSENGER_KEY)
        MessengerUtils.unregister()
    }
}
