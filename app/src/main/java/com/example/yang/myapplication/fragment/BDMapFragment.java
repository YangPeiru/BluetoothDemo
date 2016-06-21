package com.example.yang.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.example.yang.myapplication.R;

import org.xutils.x;

/**
 * Created by ypr on 2016-06-17 10:08
 * 描述:
 * TODO:
 */
public class BDMapFragment extends BaseFragment {

    private MapView mMapView = null;
    // 百度地图对象
    private BaiduMap mBaiduMap;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getActivity().getApplication());
    }

    @Override
    public int getContentLayoutRes() {
        SDKInitializer.initialize(getActivity().getApplication());
        return R.layout.fragment_bdmap;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView = (MapView) view.findViewById(R.id.bmapView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {
        mBaiduMap = mMapView.getMap();
        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        //卫星地图
//        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
        //开启热力图
//        mBaiduMap.setBaiduHeatMapEnabled(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView=null;
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }
}
