package com.guiying.module.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.guiying.module.common.base.BaseActivity;
import com.guiying.module.common.base.BaseFragment;
import com.guiying.module.common.base.ClassUtils;
import com.guiying.module.common.base.IViewDelegate;
import com.guiying.module.common.base.ViewManager;
import com.guiying.module.common.widget.NoScrollViewPager;

import java.util.List;

/**
 * <p> </p>
 *
 * @author 张华洋 2017/9/27 10:23
 * @version V1.1
 * @name BottomNavigationActivity
 */
public class BottomNavigationActivity extends BaseActivity {

    private NoScrollViewPager mPager;
    private List<BaseFragment> mFragments;
    private FragmentAdapter mAdapter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int i = item.getItemId();
            if (i == R.id.navigation_home) {
                mPager.setCurrentItem(0);
                return true;
            } else if (i == R.id.navigation_dashboard) {
                mPager.setCurrentItem(1);
                return true;
            } else if (i == R.id.navigation_notifications) {
                mPager.setCurrentItem(2);
                return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        initViewPager();
    }

    private void initViewPager() {
        mFragments = ViewManager.getInstance().getAllFragment();//这几个Fragment是主动添加到ViewManager中的
        BaseFragment newsFragment = getNewsFragment();//主动寻找
        mFragments.add(newsFragment);
        mPager = (NoScrollViewPager) findViewById(R.id.container_pager);
        mAdapter = new FragmentAdapter(getSupportFragmentManager(), mFragments);
        mPager.setPagerEnabled(false);
        mPager.setAdapter(mAdapter);
    }


    /**
     * 在News模块中寻找实现的Fragment
     *
     * @return Fragment
     */
    private BaseFragment getNewsFragment() {
        BaseFragment newsFragment = null;
        List<IViewDelegate> viewDelegates = ClassUtils.getObjectsWithInterface(this, IViewDelegate.class, "com.guiying.module.news");
        if (viewDelegates != null && !viewDelegates.isEmpty()) {
            newsFragment = viewDelegates.get(0).getFragment("");
        }
        return newsFragment;
    }

}
