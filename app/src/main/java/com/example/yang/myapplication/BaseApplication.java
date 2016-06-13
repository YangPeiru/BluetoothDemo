package com.example.yang.myapplication;

import android.app.Application;

import org.xutils.x;

/**
 * Created by ypr on 2016-06-13 10:58
 * 描述:
 * TODO:
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
