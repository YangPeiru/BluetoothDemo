package com.example.yang.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import com.example.yang.myapplication.R;
import com.example.yang.myapplication.controller.BaseController;
import com.example.yang.myapplication.controller.MyStateContrller;
import com.example.yang.myapplication.view.NoScrollViewPager;

import java.util.List;

/**
 * Created by ypr on 2016-06-08 16:06
 * 描述:显示设置的页面
 * TODO:
 */
public class SettingFragment extends BaseFragment {

    private NoScrollViewPager mViewPager;
    private RadioGroup mRgTabs;
    private List<BaseController> mPageDatas;                //
    private int mCurrentTab;                // 记录当前选中的tab
    private MyStateContrller myStateContrller;

    @Override
    public int getContentLayoutRes() {
        return R.layout.fragment_noscroll_viewpager;
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


    private class SettingPagerAdapter extends PagerAdapter {
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
            //两个contrller是同一个,因为是同一个position
            BaseController controller = mPageDatas.get(position);
//            controller.onDestroy();//调用子类自己的销毁方式
            container.removeView((View) object);
        }
    }
}
