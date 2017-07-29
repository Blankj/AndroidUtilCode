package com.blankj.androidutilcode.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.blankj.androidutilcode.R;
import com.blankj.androidutilcode.base.BaseBackActivity;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.SizeUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/26
 *     desc  : Image工具类Demo
 * </pre>
 */
public class ImageActivity extends BaseBackActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, ImageActivity.class);
        context.startActivity(starter);
    }

    private ImageView ivSrc;
    private ImageView ivView2Bitmap;

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_image;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        getToolBar().setTitle(getString(R.string.demo_image));

        ivSrc = (ImageView) findViewById(R.id.iv_src);
        ivView2Bitmap = (ImageView) findViewById(R.id.iv_view2Bitmap);
        ImageView ivRound = (ImageView) findViewById(R.id.iv_round);
        ImageView ivRoundCorner = (ImageView) findViewById(R.id.iv_round_corner);
        ImageView ivFastBlur = (ImageView) findViewById(R.id.iv_fast_blur);
        ImageView ivRenderScriptBlur = (ImageView) findViewById(R.id.iv_render_script_blur);
        ImageView ivStackBlur = (ImageView) findViewById(R.id.iv_stack_blur);
        ImageView ivAddFrame = (ImageView) findViewById(R.id.iv_add_frame);
        ImageView ivAddReflection = (ImageView) findViewById(R.id.iv_add_reflection);
        ImageView ivAddTextWatermark = (ImageView) findViewById(R.id.iv_add_text_watermark);
        ImageView ivAddImageWatermark = (ImageView) findViewById(R.id.iv_add_image_watermark);
        ImageView ivGray = (ImageView) findViewById(R.id.iv_gray);

        Bitmap src = ImageUtils.getBitmap(R.drawable.img_lena);
        Bitmap watermark = ImageUtils.getBitmap(R.mipmap.ic_launcher);

        SizeUtils.forceGetViewSize(ivSrc, new SizeUtils.onGetSizeListener() {
            @Override
            public void onGetSize(View view) {
                ivView2Bitmap.setImageBitmap(ImageUtils.view2Bitmap(ivSrc));
            }
        });
        ivRound.setImageBitmap(ImageUtils.toRound(src));
        ivRoundCorner.setImageBitmap(ImageUtils.toRoundCorner(src, 60));
        ivFastBlur.setImageBitmap(ImageUtils.fastBlur(src, 0.1f, 5));
        ivRenderScriptBlur.setImageBitmap(ImageUtils.renderScriptBlur(src, 10));
        src = ImageUtils.getBitmap(R.drawable.img_lena);
        ivStackBlur.setImageBitmap(ImageUtils.stackBlur(src, 10, false));
        ivAddFrame.setImageBitmap(ImageUtils.addFrame(src, 16, Color.GREEN));
        ivAddReflection.setImageBitmap(ImageUtils.addReflection(src, 80));
        ivAddTextWatermark.setImageBitmap(ImageUtils.addTextWatermark(src, "blankj", 40, 0x8800ff00, 0, 0));
        ivAddImageWatermark.setImageBitmap(ImageUtils.addImageWatermark(src, watermark, 0, 0, 0x88));
        ivGray.setImageBitmap(ImageUtils.toGray(src));
    }

    @Override
    public void doBusiness(Context context) {

    }

    @Override
    public void onWidgetClick(View view) {

    }
}