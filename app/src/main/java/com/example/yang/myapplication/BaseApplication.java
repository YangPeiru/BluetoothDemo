package com.example.yang.myapplication;

import android.app.Application;
import android.content.Context;

import org.xutils.x;

/**
 * Created by ypr on 2016-06-13 10:58
 * 描述:
 * TODO:
 */
public class BaseApplication extends Application {
    private  Application application;
    public BaseApplication() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }

    public  Context getApplicationContextInstance() {
        return application.getApplicationContext();
    }

}
