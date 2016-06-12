package com.example.yang.myapplication.controller;

import android.content.Context;
import android.view.View;

/**
 * Created by ypr on 2016-06-08 17:13
 * 描述:
 * TODO:
 */
public abstract class BaseController {
    protected View mRootView;	// 控制器的根布局
    protected Context mContext;

    public BaseController(Context context) {
        this.mContext = context;
        // 初始化根布局
        mRootView = initView(context);
    }

    public View getRootView()
    {
        return mRootView;
    }
    public void initData()
    {
        // controller自己去实现自己加载数据的方式
    }
    protected abstract View initView(Context context);
}
