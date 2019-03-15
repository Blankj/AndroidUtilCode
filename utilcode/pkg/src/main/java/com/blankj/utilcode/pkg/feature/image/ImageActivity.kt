package com.blankj.utilcode.pkg.feature.image

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.blankj.lib.base.BaseTaskActivity
import com.blankj.lib.base.rv.BaseViewHolder
import com.blankj.lib.base.rv.adapter.BaseAdapter
import com.blankj.utilcode.pkg.Config
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.ImageUtils
import com.blankj.utilcode.util.LogUtils
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
class ImageActivity : BaseTaskActivity<List<ImageBean>>() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, ImageActivity::class.java)
            context.startActivity(starter)
        }
    }

    private lateinit var src: Bitmap

    override fun doInBackground(): List<ImageBean> {
        src = ImageUtils.getBitmap(R.drawable.image_lena)
        val round = ImageUtils.getBitmap(R.drawable.main_avatar_round)
        val watermark = ImageUtils.getBitmap(R.mipmap.ic_launcher)

        val width = src.width
        val height = src.height

        return ArrayList<ImageBean>().apply {
            add(ImageBean(R.string.image_src, src))
            add(ImageBean(R.string.image_add_color, ImageUtils.drawColor(src, Color.parseColor("#8000FF00"))))
            add(ImageBean(R.string.image_scale, ImageUtils.scale(src, width / 2, height / 2)))
            add(ImageBean(R.string.image_clip, ImageUtils.clip(src, 0, 0, width / 2, height / 2)))
            add(ImageBean(R.string.image_skew, ImageUtils.skew(src, 0.2f, 0.1f)))
            add(ImageBean(R.string.image_rotate, ImageUtils.rotate(src, 90, (width / 2).toFloat(), (height / 2).toFloat())))
            add(ImageBean(R.string.image_to_round, ImageUtils.toRound(src)))
            add(ImageBean(R.string.image_to_round_border, ImageUtils.toRound(src, 16, Color.GREEN)))
            add(ImageBean(R.string.image_to_round_corner, ImageUtils.toRoundCorner(src, 80f)))
            add(ImageBean(R.string.image_to_round_corner_border, ImageUtils.toRoundCorner(src, 80f, 16, Color.GREEN)))
            add(ImageBean(R.string.image_add_corner_border, ImageUtils.addCornerBorder(src, 16, Color.GREEN, 0f)))
            add(ImageBean(R.string.image_add_circle_border, ImageUtils.addCircleBorder(round, 16, Color.GREEN)))
            add(ImageBean(R.string.image_add_reflection, ImageUtils.addReflection(src, 80)))
            add(ImageBean(R.string.image_add_text_watermark, ImageUtils.addTextWatermark(src, "blankj", 40, Color.GREEN, 0f, 0f)))
            add(ImageBean(R.string.image_add_image_watermark, ImageUtils.addImageWatermark(src, watermark, 0, 0, 0x88)))
            add(ImageBean(R.string.image_to_gray, ImageUtils.toGray(src)))
            add(ImageBean(R.string.image_fast_blur, ImageUtils.fastBlur(src, 0.1f, 5f)))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                add(ImageBean(R.string.image_render_script_blur, ImageUtils.renderScriptBlur(src, 10f)))
            }
            add(ImageBean(R.string.image_stack_blur, ImageUtils.stackBlur(src, 10)))
            add(ImageBean(R.string.image_compress_by_scale, ImageUtils.compressByScale(src, 0.5f, 0.5f)))
            add(ImageBean(R.string.image_compress_by_quality_half, ImageUtils.compressByQuality(src, 50)))
            add(ImageBean(R.string.image_compress_by_quality_max_size, ImageUtils.compressByQuality(src, 10L * 1024)))// 10Kb
            add(ImageBean(R.string.image_compress_by_sample_size, ImageUtils.compressBySampleSize(src, 2)))
            Thread.sleep(2000)
        }
    }

    override fun runOnUiThread(data: List<ImageBean>) {
        val imageAdapter = ImageAdapter(data, R.layout.item_image)
        imageRv.adapter = imageAdapter
        imageAdapter.headerView = View.inflate(this, R.layout.item_image_header, null);
        initHeaderView(imageAdapter.headerView)
        imageRv.layoutManager = LinearLayoutManager(this@ImageActivity)
    }

    private fun initHeaderView(headerView: View) {
        headerView.findViewById<Button>(R.id.imageSaveBtn).setOnClickListener(this)
    }

    override fun bindTitle(): CharSequence {
        return getString(R.string.demo_image)
    }

    override fun initData(bundle: Bundle?) {}

    override fun bindLayout(): Int {
        isSupportScroll = false
        return R.layout.activity_image
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {}

    override fun doBusiness() {}

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.imageSaveBtn -> {
                val save = ImageUtils.save(src, Config.CACHE_PATH + "lena.jpg", Bitmap.CompressFormat.JPEG)
                ToastUtils.showLong(if (save) "successful" else "failed")
            }
        }
    }
}

class ImageAdapter(list: List<ImageBean>, @LayoutRes val layoutId: Int) : BaseAdapter<ImageBean>() {

    override fun bind(holder: BaseViewHolder, data: ImageBean) {
        val textView = holder.getView<TextView>(R.id.imageItemNameTv)
        textView.text = data.name
        val image = holder.getView<ImageView>(R.id.imageItemIv)
        image.setImageBitmap(data.image)
    }

    override fun bindLayout(viewType: Int): Int {
        if (viewType == VIEW_TYPE_HEADER) {
            return R.layout.item_image_header
        }
        return layoutId
    }

    override fun onViewRecycled(holder: BaseViewHolder) {
        super.onViewRecycled(holder)
        LogUtils.e()
    }
}

class ImageBean(@StringRes resId: Int, var image: Bitmap) {
    var name: String = StringUtils.getString(resId)
}