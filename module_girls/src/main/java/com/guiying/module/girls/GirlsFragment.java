package com.guiying.module.girls;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guiying.module.common.base.BaseFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class GirlsFragment extends BaseFragment {


    public GirlsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_girls, container, false);
    }


}
