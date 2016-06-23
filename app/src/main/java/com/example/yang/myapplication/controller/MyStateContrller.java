package com.example.yang.myapplication.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.yang.myapplication.R;
import com.example.yang.myapplication.view.BatteryView;

import org.xutils.x;

/**
 * Created by Yang on 2016/6/12.
 * 描述:状态数据实现类
 */
public class MyStateContrller extends BaseController{
    private BatteryView mBatteryView;
    private TextView mBatteryNum;

    public MyStateContrller(Context context) {
        super(context);
    }

    @Override
    protected View initView(Context context) {
        View view = View.inflate(context, R.layout.controller_mystate,null);
        x.view().inject(view);
        mBatteryView = (BatteryView) view.findViewById(R.id.view_batterty);
        mBatteryNum = (TextView) view.findViewById(R.id.tv_battery);
        return view;
    }

    @Override
    public void initData() {
       register();
    }


    private void register() {
        mContext.registerReceiver(batteryChangedReceiver,  new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        Log.d("Log","注册电量广播");
    }

    public void unregister() {
            mContext.unregisterReceiver(batteryChangedReceiver);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
