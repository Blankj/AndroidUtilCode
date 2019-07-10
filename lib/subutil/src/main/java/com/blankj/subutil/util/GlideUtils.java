//package com.blankj.subutil.util;
//
//import android.content.Context;
//import android.graphics.drawable.PictureDrawable;
//import android.widget.ImageView;
//
//import com.blankj.subutil.R;
//import com.blankj.subutil.util.image.GlideApp;
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.engine.DiskCacheStrategy;
//import com.bumptech.glide.request.RequestOptions;
//
///**
// * <pre>
// *     author: Blankj
// *     blog  : http://blankj.com
// *     time  : 2018/05/16
// *     desc  :
// * </pre>
// */
//public final class GlideUtils {
//
//
//
//    public static void setCircleImage(Context context, String url, ImageView view) {
//        RequestOptions requestOptions = new RequestOptions()
//                .placeholder(R.drawable.def_img_round_holder)
//                .error(R.drawable.def_img_round_error)
//                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
//                .circleCrop().dontAnimate();
//        Glide.with(context).load(url).apply(requestOptions).into(view);
//    }
//
//    public static void setImage(Context context, String url, ImageView view) {
//        if (url.endsWith(".svg") || url.endsWith(".SVG")) {
//            setSvgImage(context, url, view);
//            return;
//        }
//
//        RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.def_img)
//                .error(R.drawable.def_img).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).dontAnimate();
//        Glide.with(context).load(url).apply(requestOptions).into(view);
//    }
//
//    private static void setSvgImage(Context context, String url, ImageView view) {
//        GlideApp.with(context)
//                .as(PictureDrawable.class)
//                .error(R.drawable.def_img).load(url).into(view);
//    }
//}
