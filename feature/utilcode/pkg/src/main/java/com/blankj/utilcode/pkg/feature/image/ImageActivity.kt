package com.blankj.utilcode.pkg.feature.image

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import com.blankj.common.activity.CommonActivity
import com.blankj.common.helper.PermissionHelper
import com.blankj.common.item.CommonItem
import com.blankj.common.item.CommonItemClick
import com.blankj.common.item.CommonItemImage
import com.blankj.common.item.CommonItemTitle
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.pkg.Config
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.*
import java.io.File
import java.util.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/09/26
 * desc  : demo about ImageUtils
 * ```
 */
class ImageActivity : CommonActivity() {

    private val savePath = Config.CACHE_PATH + "lena.jpg"
    private val titleItem: CommonItemTitle = CommonItemTitle("isImage: $savePath", "");

    companion object {
        fun start(context: Context) {
            PermissionHelper.request(context, object : PermissionUtils.SimpleCallback {
                override fun onGranted() {
                    val starter = Intent(context, ImageActivity::class.java)
                    context.startActivity(starter)
                }

                override fun onDenied() {
                }
            }, PermissionConstants.STORAGE)
        }
    }

    private val bgTask: ThreadUtils.SimpleTask<List<CommonItem<*>>> = object : ThreadUtils.SimpleTask<List<CommonItem<*>>>() {
        override fun doInBackground(): List<CommonItem<*>> {
            return bindItems()
        }

        override fun onSuccess(result: List<CommonItem<*>>) {
            dismissLoading()
            itemsView.updateItems(result)
        }
    }

    override fun bindTitleRes(): Int {
        return R.string.demo_image
    }

    override fun bindItems(): ArrayList<CommonItem<*>> {
        if (ThreadUtils.isMainThread()) return arrayListOf()
        val src = ImageUtils.getBitmap(R.drawable.image_lena)
        val round = ImageUtils.getBitmap(R.drawable.common_avatar_round)
        val watermark = ImageUtils.getBitmap(R.mipmap.ic_launcher)

        val width = src.width
        val height = src.height

        titleItem.setContent(ImageUtils.isImage(savePath).toString())

        return CollectionUtils.newArrayList<CommonItem<*>>().apply {
            add(titleItem)
            add(CommonItemClick("Save to $savePath") {
                ThreadUtils.executeBySingle(object : ThreadUtils.SimpleTask<Boolean>() {
                    override fun doInBackground(): Boolean {
                        return ImageUtils.save(src, savePath, Bitmap.CompressFormat.JPEG)
                    }

                    override fun onSuccess(result: Boolean) {
                        titleItem.setContent(ImageUtils.isImage(savePath).toString())
                        titleItem.update()
                        SnackbarUtils.with(mContentView)
                                .setDuration(SnackbarUtils.LENGTH_LONG)
                                .apply {
                                    if (result) {
                                        setMessage("save successful.")
                                                .showSuccess(true)
                                    } else {
                                        setMessage("save failed.")
                                                .showError(true)
                                    }
                                }
                    }
                })
            })
            add(CommonItemClick("Save to Album") {
                ThreadUtils.executeBySingle(object : ThreadUtils.SimpleTask<File?>() {
                    override fun doInBackground(): File? {
                        return ImageUtils.save2Album(src, Bitmap.CompressFormat.JPEG)
                    }

                    override fun onSuccess(result: File?) {
                        SnackbarUtils.with(mContentView)
                                .setDuration(SnackbarUtils.LENGTH_LONG)
                                .apply {
                                    if (result != null) {
                                        setMessage("save successful.")
                                                .showSuccess(true)
                                    } else {
                                        setMessage("save failed.")
                                                .showError(true)
                                    }
                                }
                    }
                })
            })
            add(CommonItemImage(R.string.image_src, Utils.Consumer {
                it.setImageBitmap(src)
            }))
            add(CommonItemImage(R.string.image_add_color, Utils.Consumer {
                it.setImageBitmap(ImageUtils.drawColor(src, Color.parseColor("#8000FF00")))
            }))
            add(CommonItemImage(R.string.image_scale, Utils.Consumer {
                it.setImageBitmap(ImageUtils.scale(src, width / 2, height / 2))
            }))
            add(CommonItemImage(R.string.image_clip, Utils.Consumer {
                it.setImageBitmap(ImageUtils.clip(src, 0, 0, width / 2, height / 2))
            }))
            add(CommonItemImage(R.string.image_skew, Utils.Consumer {
                it.setImageBitmap(ImageUtils.skew(src, 0.2f, 0.1f))
            }))
            add(CommonItemImage(R.string.image_rotate, Utils.Consumer {
                it.setImageBitmap(ImageUtils.rotate(src, 90, (width / 2).toFloat(), (height / 2).toFloat()))
            }))
            add(CommonItemImage(R.string.image_to_round) { it ->
                it.setImageBitmap(ImageUtils.toRound(src))
            })
            add(CommonItemImage(R.string.image_to_round_border, Utils.Consumer {
                it.setImageBitmap(ImageUtils.toRound(src, 16, Color.GREEN))
            }))
            add(CommonItemImage(R.string.image_to_round_corner, Utils.Consumer {
                it.setImageBitmap(ImageUtils.toRoundCorner(src, 80f))
            }))
            add(CommonItemImage(R.string.image_to_round_corner_border, Utils.Consumer {
                it.setImageBitmap(ImageUtils.toRoundCorner(src, 80f, 16, Color.GREEN))
            }))
            add(CommonItemImage(R.string.image_add_corner_border, Utils.Consumer {
                it.setImageBitmap(ImageUtils.addCornerBorder(src, 16, Color.GREEN, 0f))
            }))
            add(CommonItemImage(R.string.image_add_circle_border, Utils.Consumer {
                it.setImageBitmap(ImageUtils.addCircleBorder(round, 16, Color.GREEN))
            }))
            add(CommonItemImage(R.string.image_add_reflection, Utils.Consumer {
                it.setImageBitmap(ImageUtils.addReflection(src, 80))
            }))
            add(CommonItemImage(R.string.image_add_text_watermark, Utils.Consumer {
                it.setImageBitmap(ImageUtils.addTextWatermark(src, "blankj", 40, Color.GREEN, 0f, 0f))
            }))
            add(CommonItemImage(R.string.image_add_image_watermark, Utils.Consumer {
                it.setImageBitmap(ImageUtils.addImageWatermark(src, watermark, 0, 0, 0x88))
            }))
            add(CommonItemImage(R.string.image_to_gray, Utils.Consumer {
                it.setImageBitmap(ImageUtils.toGray(src))
            }))
            add(CommonItemImage(R.string.image_fast_blur, Utils.Consumer {
                it.setImageBitmap(ImageUtils.fastBlur(src, 0.1f, 5f))
            }))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                add(CommonItemImage(R.string.image_render_script_blur, Utils.Consumer {
                    it.setImageBitmap(ImageUtils.renderScriptBlur(src, 10f))
                }))
            }
            add(CommonItemImage(R.string.image_stack_blur, Utils.Consumer {
                it.setImageBitmap(ImageUtils.stackBlur(src, 10))
            }))
            add(CommonItemImage(R.string.image_compress_by_scale, Utils.Consumer {
                it.setImageBitmap(ImageUtils.compressByScale(src, 0.5f, 0.5f))
            }))
            add(CommonItemImage(R.string.image_compress_by_sample_size, Utils.Consumer {
                it.setImageBitmap(ImageUtils.compressBySampleSize(src, 2))
            }))
        }
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View?) {
        super.initView(savedInstanceState, contentView)
        showLoading()
        ThreadUtils.executeByIo(bgTask)
    }

    override fun onDestroy() {
        super.onDestroy()
        ThreadUtils.cancel(bgTask)
    }
}