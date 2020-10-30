package com.blankj.utilcode.pkg.feature.intent

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import com.blankj.common.activity.CommonActivity
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemClick
import com.blankj.utilcode.pkg.Config
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.*
import java.util.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2020/05/29
 * desc  : demo about IntentUtils
 * ```
 */
class IntentActivity : CommonActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, IntentActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun bindTitleRes(): Int {
        return R.string.demo_intent
    }

    override fun bindItems(): MutableList<CommonItem<*>> {
        return CollectionUtils.newArrayList(
                CommonItemClick("LaunchApp") {
                    startActivity(IntentUtils.getLaunchAppIntent(packageName))
                },
                CommonItemClick("LaunchAppDetailsSettings") {
                    startActivityForResult(IntentUtils.getLaunchAppDetailsSettingsIntent(packageName), 1)
                },
                CommonItemClick("ShareText") {
                    startActivity(IntentUtils.getShareTextIntent("share content"))
                },
                CommonItemClick("ShareImage") {
                    startActivity(IntentUtils.getShareImageIntent(getShareImagePath()[0]));
                },
                CommonItemClick("ShareTextImage") {
                    startActivity(IntentUtils.getShareTextImageIntent("share content", getShareImagePath()[0]));
                },
                CommonItemClick("ShareImages") {
                    startActivity(IntentUtils.getShareImageIntent(getShareImagePath()));
                },
                CommonItemClick("ShareTextImages") {
                    startActivity(IntentUtils.getShareTextImageIntent("share content", getShareImagePath()));
                }
        )
    }

    private fun getShareImagePath(): LinkedList<String> {
        val shareImagePath0 = Config.CACHE_PATH + "share.jpg"
        if (!FileUtils.isFile(shareImagePath0)) {
            ImageUtils.save(ImageUtils.getBitmap(R.drawable.image_lena), shareImagePath0, Bitmap.CompressFormat.JPEG)
        }
        val shareImagePath1 = Config.CACHE_PATH + "cheetah.jpg"
        if (!FileUtils.isFile(shareImagePath1)) {
            ImageUtils.save(ImageUtils.getBitmap(R.drawable.span_cheetah), shareImagePath1, Bitmap.CompressFormat.JPEG)
        }
        return CollectionUtils.newLinkedList(shareImagePath0, shareImagePath1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        LogUtils.d("onActivityResult() called with: requestCode = $requestCode, resultCode = $resultCode, data = $data")
    }
}
