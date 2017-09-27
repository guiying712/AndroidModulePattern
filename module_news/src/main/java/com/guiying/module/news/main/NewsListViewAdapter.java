package com.guiying.module.news.main;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * <p>类说明</p>
 *
 * @author 张华洋 2017/4/20 23:41
 * @version V1.2.0
 * @name NewsListViewAdapter
 */
public class NewsListViewAdapter extends PagerAdapter {

    private final List<? extends View> mViewList;
    private final List<String> mTabList;
    private View mCurrentView;

    public NewsListViewAdapter(List<? extends View> list, List<String> dates) {
        mViewList = list;
        mTabList = dates;
    }

    @Override
    public int getCount() {
        return mViewList == null ? 0 : mViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView((mViewList.get(position)));
        return mViewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTabList == null) {
            return null;
        }
        Calendar displayDate = Calendar.getInstance();
        displayDate.add(Calendar.DAY_OF_YEAR, -position);

        return DateFormat.getDateInstance().format(displayDate.getTime());
    }

    /**
     * 获取当前view的方法
     */
    View getCurrentView() {
        return mCurrentView;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        if (mViewList.size() > 0) {
            mCurrentView = mViewList.get(position);
        }
    }

}
