package com.guiying.module.girls.girl;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.guiying.module.girls.R;
import com.guiying.module.girls.data.bean.Girls;

import java.util.List;

/**
 * <p> </p>
 *
 * @author 张华洋 2017/5/19 20:31
 * @version V1.1
 * @name GirlAdapter
 */
public class GirlAdapter extends PagerAdapter {

    private Context mContext;
    private List<Girls> mData;
    private LayoutInflater layoutInflater;
    private View mCurrentView;

    public GirlAdapter(Context context, List<Girls> data) {
        mContext = context;
        mData = data;
        layoutInflater = LayoutInflater.from(this.mContext);
    }

    @Override
    public int getCount() {
        if (mData == null) {
            return 0;
        }
        return mData.size();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        mCurrentView = (View) object;
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        final String imageUrl = mData.get(position).getUrl();
        View view = layoutInflater.inflate(R.layout.item_girl_detail, container, false);
        PhotoView imageView = (PhotoView) view.findViewById(R.id.girl_image);
        Glide.with(mContext)
                .load(imageUrl)
                .thumbnail(0.2f)
                .into(imageView);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public View getPrimaryItem() {
        return mCurrentView;
    }
}
