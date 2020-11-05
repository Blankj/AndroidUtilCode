package com.blankj.utilcode.pkg.feature.uiMessage

import android.content.Context
import android.content.Intent
import com.blankj.common.activity.CommonActivity
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemClick
import com.blankj.common.item.CommonItemTitle
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.CollectionUtils
import com.blankj.utilcode.util.UiMessageUtils


/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2020/04/14
 * desc  : demo about UiMessageUtils
 * ```
 */
class UiMessageActivity : CommonActivity(), UiMessageUtils.UiMessageCallback {

    private val titleItem: CommonItemTitle = CommonItemTitle("", true);
    private var sendContent: String = ""

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, UiMessageActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitleRes(): Int {
        return R.string.demo_uiMessage
    }

    override fun bindItems(): List<CommonItem<*>> {
        return CollectionUtils.newArrayList(
                titleItem,
                CommonItemClick(R.string.uiMessage_add_listener_id) {
                    UiMessageUtils.getInstance().addListener(R.id.utilCodeUiMessageAddListenerId, this)
                },
                CommonItemClick(R.string.uiMessage_remove_all_id) {
                    UiMessageUtils.getInstance().removeListeners(R.id.utilCodeUiMessageAddListenerId)
                },
                CommonItemClick(R.string.uiMessage_add_listener) {
                    UiMessageUtils.getInstance().addListener(this)
                },
                CommonItemClick(R.string.uiMessage_remove_listener) {
                    UiMessageUtils.getInstance().removeListener(this)
                },
                CommonItemClick(R.string.uiMessage_send) {
                    sendContent = "send: UiMessageActivity#${UiMessageActivity.hashCode()}"
                    titleItem.title = ""
                    UiMessageUtils.getInstance().send(R.id.utilCodeUiMessageAddListenerId, UiMessageActivity)
                }
        )
    }

    override fun handleMessage(localMessage: UiMessageUtils.UiMessage) {
        if (localMessage.id == R.id.utilCodeUiMessageAddListenerId) {
            var content: String = sendContent
            content += "\nreceive: UiMessageActivity#${localMessage.getObject().hashCode()}"
            titleItem.title = if (titleItem.title.toString().isEmpty()) {
                content
            } else {
                titleItem.title.toString() + "\n" + content
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        UiMessageUtils.getInstance().removeListeners(R.id.utilCodeUiMessageAddListenerId)
        UiMessageUtils.getInstance().removeListener(this)
    }
}