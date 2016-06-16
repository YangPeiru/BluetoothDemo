package com.example.yang.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import com.example.yang.myapplication.R;
import com.example.yang.myapplication.controller.BDMapController;
import com.example.yang.myapplication.controller.BaseController;
import com.example.yang.myapplication.controller.MyStateContrller;
import com.example.yang.myapplication.controller.MyStateContrller;
import com.example.yang.myapplication.controller.NewestListController;
import com.example.yang.myapplication.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ypr on 2016-06-08 16:06
 * 描述:
 * TODO:
 */
public class ContentFragment extends BaseFragment {

    private NoScrollViewPager mViewPager;
    private RadioGroup mRgTabs;
    private List<BaseController> mPageDatas;                //
    private int mCurrentTab;                // 记录当前选中的tab
    private MyStateContrller myStateContrller;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return View.inflate(getActivity(), R.layout.fragment_content, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewPager = (NoScrollViewPager) view.findViewById(R.id.content_viewpager);
        mRgTabs = (RadioGroup) view.findViewById(R.id.content_rg_tabs);
        mRgTabs.setOnCheckedChangeListener(new TabChangedListener());
        // 设置默认选中项
        mRgTabs.check(R.id.content_rb_state);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {
        // 模拟数据显示 TODO:
        mPageDatas = new ArrayList<>();
        myStateContrller = new MyStateContrller(getActivity());
        mPageDatas.add(myStateContrller);
        mPageDatas.add(new NewestListController(getActivity()));
        mPageDatas.add(new BDMapController(getActivity()));
        mPageDatas.add(new NewestListController(getActivity()));
        mViewPager.setAdapter(new ContentPagerAdapter());
    }

    private class TabChangedListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.content_rb_state:
                    mCurrentTab = 0;
                    break;
                case R.id.content_rb_plaza:
                    mCurrentTab = 1;
                    break;
                case R.id.content_rb_find:
                    mCurrentTab = 2;
                    break;
                case R.id.content_rb_setting:
                    mCurrentTab = 3;
                    break;
                default:
                    break;
            }
            mViewPager.setCurrentItem(mCurrentTab);
        }
    }

    private class ContentPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mPageDatas != null ? mPageDatas.size() : 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BaseController controller = mPageDatas.get(position);
            // 通过控制器提供显示的view
            View rootView = controller.getRootView();
            container.addView(rootView);// 显示的view
            // 提供加载数据的方式
            controller.initData();
            return rootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
