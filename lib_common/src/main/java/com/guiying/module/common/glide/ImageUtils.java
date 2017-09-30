package com.guiying.module.common.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.guiying.module.common.utils.Utils;

/**
 * <p> 图片加载工具类</p>
 *
 * @name ImageUtils
 */
public class ImageUtils {

    /**
     * 默认加载
     */
    public static void loadImageView(String path, ImageView mImageView) {
        Glide.with(mImageView.getContext()).load(path).into(mImageView);
    }

    public static void loadImageWithError(String path, int errorRes, ImageView mImageView) {
        Glide.with(mImageView.getContext())
                .load(path)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(errorRes)
                .into(mImageView);
    }

    /**
     * 设置加载中以及加载失败图片
     */
    public static void loadImageWithLoading(String path, ImageView mImageView, int lodingImage, int errorRes) {
        Glide.with(mImageView.getContext()).load(path).placeholder(lodingImage).
                error(errorRes).into(mImageView);
    }

    /**
     * 设置加载动画
     * api也提供了几个常用的动画：比如crossFade()
     */
    public static void loadImageViewAnim(String path, int anim, ImageView mImageView) {
        Glide.with(mImageView.getContext()).load(path).animate(anim).into(mImageView);
    }


    /**
     * 加载为bitmap
     *
     * @param path     图片地址
     * @param listener 回调
     */
    public static void loadBitMap(String path, final onLoadBitmap listener) {
        Glide.with(Utils.getContext()).load(path).asBitmap().into(new SimpleTarget<Bitmap>() {

            @Override
            public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                listener.onReady(bitmap);
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                listener.onFailed();
            }
        });
    }

    /**
     * 显示加载进度
     *
     * @param path       图片地址
     * @param mImageView 图片控件
     * @param loadView   加载view
     */
    public static void loadImageWithProgress(String path, final ImageView mImageView, final View loadView, int errorRes) {
        Glide.with(mImageView.getContext()).load(path).error(errorRes).into(new GlideDrawableImageViewTarget(mImageView) {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                super.onResourceReady(resource, animation);
                loadView.setVisibility(View.GONE);
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
                loadView.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 清除view上的图片
     *
     * @param view 视图
     */
    public static void clearImageView(View view) {
        Glide.clear(view);
    }

    /**
     * 清理磁盘缓存需要在子线程中执行
     */
    public static void GuideClearDiskCache(Context mContext) {
        Glide.get(mContext).clearDiskCache();
    }

    /**
     * 清理内存缓存可以在UI主线程中进行
     */
    public static void GuideClearMemory(Context mContext) {
        Glide.get(mContext).clearMemory();
    }

    /**
     * 加载bitmap回调
     */
    public interface onLoadBitmap {
        void onReady(Bitmap resource);

        void onFailed();
    }

}