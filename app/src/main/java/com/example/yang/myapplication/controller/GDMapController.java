package com.example.yang.myapplication.controller;

import android.content.Context;
import android.view.View;

import com.example.yang.myapplication.R;

import org.xutils.x;

/**
 * Created by ypr on 2016-06-16 11:15
 * 描述:
 * TODO:
 */
public class GDMapController extends BaseController{
    public GDMapController(Context context) {
        super(context);
    }

    @Override
    protected View initView(Context context) {
        View view =View.inflate(context, R.layout.controller_find_map,null);
        x.view().inject(view);
        //获取地图控件引用
        return view;
    }

    @Override
    public void initData() {

    }
}