package com.example.yang.myapplication.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.TextView;

import com.example.yang.myapplication.R;
import com.example.yang.myapplication.view.BatteryView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Yang on 2016/6/12.
 */
public class MyStateContrller extends BaseController{
    @BindView(R.id.view_batterty)BatteryView mBatteryView;
    @BindView(R.id.tv_battery)TextView mBatteryNum;
    private Context mContext;
    public MyStateContrller(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected View initView(Context context) {
        View view = View.inflate(context, R.layout.mystate,null);
        ButterKnife.bind(context,view);
        return view;
    }

    @Override
    public void initData() {
       register();
    }


    private void register() {
        mContext.registerReceiver(batteryChangedReceiver,  new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    private void unregister() {
        mContext.unregisterReceiver(batteryChangedReceiver);
    }

    // 接受广播
    private BroadcastReceiver batteryChangedReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
                int level = intent.getIntExtra("level", 0);
                int scale = intent.getIntExtra("scale", 100);
                int power = level * 100 / scale;
//				Log.d("Deom", "电池电量：:" + power);
                mBatteryNum.setText("电池电量 "+power+"%");
                mBatteryView.setPower(power);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregister();
    }
}
