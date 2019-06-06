package com.blankj.utilcode.pkg.feature.image

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.blankj.lib.base.rv.BaseAdapter
import com.blankj.lib.base.rv.BaseCell
import com.blankj.lib.base.rv.BaseViewHolder
import com.blankj.lib.common.CommonTaskActivity
import com.blankj.utilcode.pkg.Config
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.ImageUtils
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.ToastUtils
import kotlinx.android.synthetic.main.activity_image.*

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/09/26
 * desc  : demo about ImageUtils
 * ```
 */
class ImageActivity : CommonTaskActivity<List<BaseCell>>() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, ImageActivity::class.java)
            context.startActivity(starter)
        }
    }

    private lateinit var src: Bitmap

    override fun doInBackground(): List<BaseCell> {
        src = ImageUtils.getBitmap(R.drawable.image_lena)
        val round = ImageUtils.getBitmap(R.drawable.main_avatar_round)
        val watermark = ImageUtils.getBitmap(R.mipmap.ic_launcher)

        val width = src.width
        val height = src.height

        return ArrayList<BaseCell>().apply {
            add(HeaderCell(src))
            add(ImageCell(R.string.image_src, src))
            add(ImageCell(R.string.image_add_color, ImageUtils.drawColor(src, Color.parseColor("#8000FF00"))))
            add(ImageCell(R.string.image_scale, ImageUtils.scale(src, width / 2, height / 2)))
            add(ImageCell(R.string.image_clip, ImageUtils.clip(src, 0, 0, width / 2, height / 2)))
            add(ImageCell(R.string.image_skew, ImageUtils.skew(src, 0.2f, 0.1f)))
            add(ImageCell(R.string.image_rotate, ImageUtils.rotate(src, 90, (width / 2).toFloat(), (height / 2).toFloat())))
            add(ImageCell(R.string.image_to_round, ImageUtils.toRound(src)))
            add(ImageCell(R.string.image_to_round_border, ImageUtils.toRound(src, 16, Color.GREEN)))
            add(ImageCell(R.string.image_to_round_corner, ImageUtils.toRoundCorner(src, 80f)))
            add(ImageCell(R.string.image_to_round_corner_border, ImageUtils.toRoundCorner(src, 80f, 16, Color.GREEN)))
            add(ImageCell(R.string.image_add_corner_border, ImageUtils.addCornerBorder(src, 16, Color.GREEN, 0f)))
            add(ImageCell(R.string.image_add_circle_border, ImageUtils.addCircleBorder(round, 16, Color.GREEN)))
            add(ImageCell(R.string.image_add_reflection, ImageUtils.addReflection(src, 80)))
            add(ImageCell(R.string.image_add_text_watermark, ImageUtils.addTextWatermark(src, "blankj", 40, Color.GREEN, 0f, 0f)))
            add(ImageCell(R.string.image_add_image_watermark, ImageUtils.addImageWatermark(src, watermark, 0, 0, 0x88)))
            add(ImageCell(R.string.image_to_gray, ImageUtils.toGray(src)))
            add(ImageCell(R.string.image_fast_blur, ImageUtils.fastBlur(src, 0.1f, 5f)))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                add(ImageCell(R.string.image_render_script_blur, ImageUtils.renderScriptBlur(src, 10f)))
            }
            add(ImageCell(R.string.image_stack_blur, ImageUtils.stackBlur(src, 10)))
            add(ImageCell(R.string.image_compress_by_scale, ImageUtils.compressByScale(src, 0.5f, 0.5f)))
            add(ImageCell(R.string.image_compress_by_quality_half, ImageUtils.compressByQuality(src, 50)))
            add(ImageCell(R.string.image_compress_by_quality_max_size, ImageUtils.compressByQuality(src, 10L * 1024)))// 10Kb
            add(ImageCell(R.string.image_compress_by_sample_size, ImageUtils.compressBySampleSize(src, 2)))
        }
    }

    override fun runOnUiThread(data: List<BaseCell>) {
        val imageAdapter = BaseAdapter<BaseCell>()
        imageAdapter.data = data
        imageRv.adapter = imageAdapter
        imageRv.layoutManager = LinearLayoutManager(this@ImageActivity)
    }

    override fun bindTitle(): CharSequence {
        return getString(R.string.demo_image)
    }

    override fun initData(bundle: Bundle?) {}

    override fun bindLayout(): Int {
        isSupportScroll = false
        return R.layout.activity_image
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View?) {}

    override fun doBusiness() {}

    override fun onWidgetClick(view: View) {}
}

class ImageCell(@StringRes val resId: Int, private val image: Bitmap) : BaseCell(R.layout.item_image) {

    override fun bind(holder: BaseViewHolder, position: Int) {
        holder.findViewById<TextView>(R.id.imageItemNameTv).text = StringUtils.getString(resId)
        holder.findViewById<ImageView>(R.id.imageItemIv).setImageBitmap(image)
    }
}

class HeaderCell(private val src: Bitmap) : BaseCell(R.layout.item_image_header) {

    override fun bind(holder: BaseViewHolder, position: Int) {
        holder.findViewById<Button>(R.id.imageSaveBtn).setOnClickListener {
            val save = ImageUtils.save(src, Config.CACHE_PATH + "lena.jpg", Bitmap.CompressFormat.JPEG)
            ToastUtils.showLong(if (save) "successful" else "failed")
        }
    }
}