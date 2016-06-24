package com.example.yang.myapplication.activity;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.yang.myapplication.R;
import com.example.yang.myapplication.fragment.GDMapFragment;
import com.example.yang.myapplication.fragment.MyStateFragment;
import com.example.yang.myapplication.fragment.NewsListFragment;
import com.example.yang.myapplication.fragment.SettingFragment;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by ypr on 2016-06-08 11:21
 * 描述:
 * TODO:
 */
public class MainUI extends BaseActivity {

    private static final String TAG_MAP = "TAG_MAP";
    private BluetoothAdapter mBluetoothAdapter;
    private final int REQUEST_ENABLE_BT = 1;
    private ArrayList<Fragment> fragments;
    private RadioGroup mRgTabs;
    private int PERMISSIONS_SUCCESS = 110;
    private Fragment mMyStateFragment;
    private Fragment mGDMapFragment;
    private Fragment mNewsListFragment;
    private Fragment mSettingFragment;
    private FragmentTransaction transaction;
    private MyBroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        //SDK在Android 6.0下需要进行运行检测的权限如下：
        //申请权限
        ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE},
                PERMISSIONS_SUCCESS);//自定义的code
        initView();
        initData();
    }

    private void initView() {
        mRgTabs = (RadioGroup) findViewById(R.id.content_rg_tabs);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //可在此继续其他操作。
    }

    private class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
        }
    }

    private void initData() {
        receiver = new MyBroadcastReceiver();
        //实例化过滤器并设置要过滤的广播
        IntentFilter intentFilter = new IntentFilter("mapChange");
        //注册广播
        registerReceiver(receiver, intentFilter);
        fragments = new ArrayList<>();
        mMyStateFragment = new MyStateFragment();
        mNewsListFragment = new NewsListFragment();
        mGDMapFragment = new GDMapFragment();
        mSettingFragment = new SettingFragment();
        fragments.add(mMyStateFragment);
        fragments.add(mNewsListFragment);
        fragments.add(mGDMapFragment);
        fragments.add(mSettingFragment);
        mRgTabs.setOnCheckedChangeListener(new TabChangedListener());
        //利用RadioGroup脚标索引切换Fragment,默认首页选中
        ((RadioButton) mRgTabs.getChildAt(0)).setChecked(true);
        startBluetooth();
    }

    private class TabChangedListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            int index = group.indexOfChild(group.findViewById(checkedId));
            //开启事务加载fragment
            transaction = getFragmentManager().beginTransaction();
            Fragment fragment = fragments.get(index);
            for (int i = 0; i < fragments.size(); i++) {
                if (index == i) {
                    continue;
                }
                transaction.hide(fragments.get(i));
            }
            if (!fragment.isAdded()) {
                transaction.add(R.id.main_content_container, fragment).show(fragment);
            } else {
                transaction.show(fragment);
            }
            transaction.commit();
        }
    }

    private void startBluetooth() {
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);//整个系统只有一个蓝牙适配器
        mBluetoothAdapter = bluetoothManager.getAdapter();
        // 确保蓝牙在设备上可以开启
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            Toast.makeText(getApplicationContext(), "没有蓝牙设备", Toast.LENGTH_LONG).show();
            finish();
        } else if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "设备不支持蓝牙4.0", Toast.LENGTH_SHORT).show();
            finish();
        } else if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            Toast.makeText(getApplicationContext(), "蓝牙已开启", Toast.LENGTH_LONG).show();
            scanBtDevices();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                mBluetoothAdapter.enable();
                Toast.makeText(getApplicationContext(), "开启蓝牙", Toast.LENGTH_LONG).show();
                scanBtDevices();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "拒绝开启蓝牙", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "蓝牙开启失败!", Toast.LENGTH_LONG).show();
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void scanBtDevices() {
        //获取蓝牙适配器中已经配对的设备,配对是要实现在Android系统中配对好
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        // If there are paired devices
        if (pairedDevices.size() > 0) {
            //ArrayAdapter mArrayAdapter = new ArrayAdapter();

            // Loop through paired devices
            for (BluetoothDevice btDev : pairedDevices) {
                //mArrayAdapter.add(btDev.getName() + "\n" + btDev.getAddress());
                Toast.makeText(getApplicationContext(), btDev.getName() + "\n" + btDev.getAddress(), Toast.LENGTH_LONG).show();
            }
        }
    }
}