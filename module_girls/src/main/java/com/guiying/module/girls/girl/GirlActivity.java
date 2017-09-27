package com.guiying.module.girls.girl;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.WindowManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.guiying.module.common.base.BaseActivity;
import com.guiying.module.common.widget.HackyViewPager;
import com.guiying.module.girls.Constants;
import com.guiying.module.girls.data.bean.Girls;

import java.util.List;

/**
 * <p> </p>
 *
 * @author 张华洋 2017/5/19 20:24
 * @version V1.1
 * @name GirlActivity
 */
@Route(path = "/girls/detail")
public class GirlActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        if (getIntent() != null) {
            List<Girls> mData = getIntent().getParcelableArrayListExtra(Constants.INTENT_GIRLS);
            int mCurrentIndex = getIntent().getIntExtra(Constants.INTENT_INDEX, 0);
            HackyViewPager viewPager = new HackyViewPager(this);
            setContentView(viewPager);
            GirlAdapter adapter = new GirlAdapter(this, mData);
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(mCurrentIndex);
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
    }

}
