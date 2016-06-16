package com.example.yang.myapplication.controller;

import android.content.Context;
import android.view.View;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapView;
import com.example.yang.myapplication.R;

import org.xutils.x;

/**
 * Created by ypr on 2016-06-16 11:15
 * 描述:
 * TODO:
 */
public class BDMapController extends BaseController{
    MapView mMapView = null;
    public BDMapController(Context context) {
        super(context);
    }

    @Override
    protected View initView(Context context) {
        View view =View.inflate(context, R.layout.controller_find_map,null);
        x.view().inject(view);
        //获取地图控件引用
        mMapView = (MapView)view. findViewById(R.id.bmapView);
        return view;
    }

    @Override
    public void initData() {

    }
}
