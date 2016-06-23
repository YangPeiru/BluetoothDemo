package com.example.yang.myapplication.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.yang.myapplication.R;
import com.example.yang.myapplication.controller.BaseController;
import com.example.yang.myapplication.controller.MyStateContrller;
import com.example.yang.myapplication.view.BatteryView;
import com.example.yang.myapplication.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ypr on 2016-06-17 10:07
 * 描述:
 * TODO:
 */
public class MyStateFragment extends BaseFragment {
    private ViewPager mViewPager;
    private List<BaseController> mPageDatas;                //
    private BatteryView mBatteryView;
    private TextView mBatteryNum;

    @Override
    public int getContentLayoutRes() {
        return R.layout.fragment_noscroll_viewpager;
//        return R.layout.controller_mystate;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewPager = (ViewPager) view.findViewById(R.id.noscroll_viewpager);
//        mBatteryView = (BatteryView) view.findViewById(R.id.view_batterty);
//        mBatteryNum = (TextView) view.findViewById(R.id.tv_battery);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {
        mPageDatas = new ArrayList<>();
        mPageDatas.add(new MyStateContrller(getActivity().getApplicationContext()));
        mViewPager.setAdapter(new MyStatePagerAdapter());
//        getActivity().registerReceiver(batteryChangedReceiver,  new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

        // 接受广播
    private BroadcastReceiver batteryChangedReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
                int level = intent.getIntExtra("level", 0);
                int scale = intent.getIntExtra("scale", 0);
                int power = level * 100 / scale;
                mBatteryNum.setText("电池电量 "+power+"%");
                mBatteryView.setPower(power);
            }
        }
    };
    private class MyStatePagerAdapter extends PagerAdapter {
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
