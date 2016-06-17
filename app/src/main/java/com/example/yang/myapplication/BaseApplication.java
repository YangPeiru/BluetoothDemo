package com.example.yang.myapplication;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.example.yang.myapplication.crash.CrashHandler;

import org.xutils.x;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * Created by ypr on 2016-06-13 10:58
 * 描述:
 * TODO:
 */
public class BaseApplication extends Application {
    private  Application application;
    private Stack<Activity> mActivityStack;
    private boolean mApplicationExit = false;

    public BaseApplication() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        initImageLoader(getApplicationContext());
        useBugReport();
    }

    private void finishAllActivity() {
        int i = this.mActivityStack.size();
        for (int j = 0; j < i; j++)
            if (this.mActivityStack.get(j) != null)
                ((Activity) this.mActivityStack.get(j)).finish();
        this.mActivityStack.clear();
    }

    public static String getProcessName(Context paramContext, int paramInt) {
        List localList = ((ActivityManager) paramContext.getSystemService("activity")).getRunningAppProcesses();
        if (localList == null)
            return null;
        Iterator localIterator = localList.iterator();
        while (localIterator.hasNext()) {
            ActivityManager.RunningAppProcessInfo localRunningAppProcessInfo = (ActivityManager.RunningAppProcessInfo) localIterator.next();
            if (localRunningAppProcessInfo.pid == paramInt)
                return localRunningAppProcessInfo.processName;
        }
        return null;
    }

    private void useBugReport() {
        CrashHandler.getInstance().init(this);
    }

    public void AppExit() {
        try {
            finishAllActivity();
            return;
        } catch (Exception localException) {
        }
    }

    public void addActivity(Activity paramActivity) {
        if (this.mActivityStack == null)
            this.mActivityStack = new Stack();
        this.mActivityStack.add(paramActivity);
    }

    public Activity currentActivity() {
        return (Activity) this.mActivityStack.lastElement();
    }

    public void finishActivity() {
        finishActivity((Activity) this.mActivityStack.lastElement());
    }

    public void finishActivity(Activity paramActivity) {
        if (paramActivity != null) {
            this.mActivityStack.remove(paramActivity);
            paramActivity.finish();
        }
    }

    public void finishActivity(Class<?> paramClass) {
        Iterator localIterator = this.mActivityStack.iterator();
        while (localIterator.hasNext()) {
            Activity localActivity = (Activity) localIterator.next();
            if (localActivity.getClass().equals(paramClass))
                finishActivity(localActivity);
        }
    }

    public void initImageLoader(Context paramContext) {

    }

    public boolean isApplicationExit() {
        return this.mApplicationExit;
    }

    public void onLowMemory() {
        super.onLowMemory();
    }

    public void onTrimMemory(int paramInt) {
        super.onTrimMemory(paramInt);
    }

    public void setApplicationExit(boolean paramBoolean) {
        this.mApplicationExit = paramBoolean;
    }


}
