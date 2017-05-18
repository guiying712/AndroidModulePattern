package com.guiying.girls.main;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;

import com.guiying.girls.R;
import com.guiying.girls.data.bean.Girls;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>类说明</p>
 *
 * @author 张华洋 2017/2/22 20:33
 * @version V1.2.0
 * @name GirlsView
 */
public class GirlsView extends FrameLayout implements GirlsContract.View, SwipeRefreshLayout.OnRefreshListener, RecyclerArrayAdapter.OnLoadMoreListener {

    private GirlsContract.Presenter mPresenter;
    private boolean mActive;

    private EasyRecyclerView mGirlsRecyclerView;
    private ViewStub mNetworkErrorLayout;
    private View networkErrorView;
    private GirlsAdapter mAdapter;
    private ArrayList<Girls> mData;
    private int page = 1;
    private int size = 10;

    public GirlsView(Context context) {
        super(context);
        initView();
    }

    public GirlsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }


    private void initView() {
        inflate(getContext(), R.layout.view_girls_content, this);
        mNetworkErrorLayout = (ViewStub) findViewById(R.id.network_error_layout);
        mGirlsRecyclerView = (EasyRecyclerView) findViewById(R.id.girls_recycler_view);

        mData = new ArrayList<>();
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mGirlsRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        mAdapter = new GirlsAdapter(getContext());

        mGirlsRecyclerView.setAdapterWithProgress(mAdapter);

        mAdapter.setMore(R.layout.layout_load_more, this);
        mAdapter.setNoMore(R.layout.layout_load_no_more);
        mAdapter.setError(R.layout.layout_load_error);
        mAdapter.setOnMyItemClickListener(new GirlsAdapter.OnMyItemClickListener() {
            @Override
            public void onItemClick(int position, BaseViewHolder holder) {

            }
        });

        mGirlsRecyclerView.setRefreshListener(this);

        mActive = true;
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mActive = true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mActive = false;
    }

    @Override
    public void setPresenter(GirlsContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public boolean isActive() {
        return mActive;
    }


    @Override
    public void refresh(List<Girls> data) {
        mData.clear();
        mData.addAll(data);
        mAdapter.clear();
        mAdapter.addAll(data);
    }

    @Override
    public void load(List<Girls> data) {
        mData.addAll(data);
        mAdapter.addAll(data);
    }

    @Override
    public void showError() {
        mGirlsRecyclerView.showError();

        if (networkErrorView != null) {
            networkErrorView.setVisibility(View.VISIBLE);
            return;
        }

        networkErrorView = mNetworkErrorLayout.inflate();
    }

    @Override
    public void showNormal() {
        if (networkErrorView != null) {
            networkErrorView.setVisibility(View.GONE);
        }
    }


    @Override
    public void onLoadMore() {
        if (mData.size() % size == 0) {
            page++;
            mPresenter.getGirls(size, page, false);
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.getGirls(size, page, true);
        page = 1;
    }
}
