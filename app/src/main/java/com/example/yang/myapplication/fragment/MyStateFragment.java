package com.example.yang.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.yang.myapplication.R;


/**
 * Created by ypr on 2016-06-17 10:07
 * 描述:
 * TODO:
 */
public class MyStateFragment extends BaseFragment {

    @Override
    public int getContentLayoutRes() {
        return R.layout.fragment_mystate;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {

    }

}
