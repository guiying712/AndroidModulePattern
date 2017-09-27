package com.guiying.module.news.main;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

import com.guiying.module.news.R;
import com.guiying.module.news.data.bean.StoryList;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;

/**
 * <p>类说明</p>
 *
 * @author 张华洋 2017/4/20 22:21
 * @version V1.2.0
 * @name NewListView
 */
public class NewsListView extends EasyRecyclerView implements NewsListContract.View, SwipeRefreshLayout.OnRefreshListener {
    private NewsListContract.Presenter mPresenter;
    private String mDate;
    private NewsListAdapter mAdapter;
    private boolean isActive = false;

    public NewsListView(Context context, String date) {
        super(context);
        mDate = date;
        initView();
    }

    public NewsListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        setRefreshingColor(
                ContextCompat.getColor(getContext(), R.color.colorPrimary),
                ContextCompat.getColor(getContext(), android.R.color.holo_blue_light),
                ContextCompat.getColor(getContext(), android.R.color.holo_green_light)
        );
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        setLayoutManager(layoutManager);
        DividerDecoration dividerDecoration = new DividerDecoration(getResources().getColor(R.color.gray_e0), 20, 20, 0);
        dividerDecoration.setDrawLastItem(true);
        addItemDecoration(dividerDecoration);
        mAdapter = new NewsListAdapter(getContext());
        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });

        setAdapterWithProgress(mAdapter);
        setRefreshListener(this);
        new NewsListPresenter(this);
        isActive = true;
    }

    @Override
    public void onRefresh() {
        mPresenter.getNewMessages(1, 20, mDate);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        isActive = true;
        mPresenter.getNewMessages(1, 20, mDate);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        isActive = false;
    }

    @Override
    public void setPresenter(NewsListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public void showNewsList(StoryList info) {
        if (info != null) {
            mAdapter.clear();
            mAdapter.addAll(info.getStories());
        }
    }
}
