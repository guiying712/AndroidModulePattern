package com.guiying.module.news.detail;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.guiying.module.news.R;
import com.guiying.module.news.data.bean.MessageDetail;

/**
 * <p>类说明</p>
 *
 * @author 张华洋 2017/7/1 13:18
 * @version V1.2.0
 * @name NewsDetailView
 */

public class NewsDetailView extends FrameLayout implements NewsDetailContract.View {

    private boolean isActive = false;
    private NewsDetailContract.Presenter mPresenter;
    private ImageView mToolbarImage;
    private TextView mToolbarText;
    private CollapsingToolbarLayout mCollapsingLayout;
    private TextView mDetailText;


    public NewsDetailView(Context context) {
        super(context);
        initView();
    }

    public NewsDetailView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.view_news_detail, this);
        mToolbarImage = (ImageView) findViewById(R.id.toolbar_image);
        mToolbarText = (TextView) findViewById(R.id.toolbar_text);
        mCollapsingLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_layout);
        mDetailText = (TextView) findViewById(R.id.news_detail_text);

        isActive = true;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        isActive = true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        isActive = false;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }


    @Override
    public void setPresenter(NewsDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showNewsDetail(MessageDetail detail) {
        mCollapsingLayout.setTitle(detail.getTitle());
        //设置还没收缩时状态下字体颜色
        mCollapsingLayout.setExpandedTitleColor(Color.WHITE);
        //设置收缩后Toolbar上字体的颜色
        mCollapsingLayout.setCollapsedTitleTextColor(Color.WHITE);
        mToolbarText.setText(detail.getImage_source());
        Glide.with(getContext())
                .load(detail.getImage())
                .thumbnail(0.2f)
                .into(mToolbarImage);
        mDetailText.setMovementMethod(LinkMovementMethod.getInstance());
        mDetailText.setText(Html.fromHtml(detail.getBody(), new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
                final URLDrawable urlDrawable = new URLDrawable();
                Glide.with(getContext()).load(source).asBitmap().into(new SimpleTarget<Bitmap>() {

                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        urlDrawable.bitmap = resource;
                        urlDrawable.setBounds(0, 0, resource.getWidth(), resource.getHeight());
                        mDetailText.invalidate();
                        // 解决图文重叠
                        mDetailText.setText(mDetailText.getText());
                    }
                });
                return urlDrawable;
            }
        }, null));
    }


    private class URLDrawable extends BitmapDrawable {

        private Bitmap bitmap;

        @Override
        public void draw(Canvas canvas) {
            if (bitmap != null) {
                canvas.drawBitmap(bitmap, 0, 0, getPaint());
            }
        }
    }
}
