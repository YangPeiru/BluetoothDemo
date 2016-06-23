package com.example.yang.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;


import com.example.yang.myapplication.R;
import com.example.yang.myapplication.controller.BaseController;
import com.example.yang.myapplication.controller.NewestListController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ypr on 2016-06-17 10:08
 * 描述:显示广场的页面
 * TODO:
 */
public class NewsListFragment extends BaseFragment {
    private ViewPager mViewPager;
    private List<BaseController> mPageDatas;                //
    @Override
    public int getContentLayoutRes() {
        return R.layout.fragment_noscroll_viewpager;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewPager = (ViewPager) view.findViewById(R.id.noscroll_viewpager);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {
        mPageDatas = new ArrayList<>();
        mPageDatas.add(new NewestListController(getActivity()));
        mViewPager.setAdapter(new MyNewsListPagerAdapter());
    }

    private class MyNewsListPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mPageDatas!=null?mPageDatas.size():0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view ==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BaseController controller = mPageDatas.get(position);
            View rootView = controller.getRootView();
            container.addView(rootView);
            controller.initData();
            return rootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
