package com.example.yang.myapplication.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.example.yang.myapplication.R;
import com.example.yang.myapplication.fragment.ContentFragment;

import java.util.Set;

/**
 * Created by ypr on 2016-06-08 11:21
 * 描述:
 * TODO:
 */
public class MainUI extends BaseActivity {
    private static final String TAG_CONTENT = "tag_content";

    private BluetoothAdapter mBluetoothAdapter;
    private final int REQUEST_ENABLE_BT = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        SDKInitializer.initialize(getApplication());
        setContentView(R.layout.activity_main);
        startBluetooth();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //开启事务加载fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_content_container, new ContentFragment(), TAG_CONTENT);
        transaction.commit();
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
        }else if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)){
            Toast.makeText(this, "设备不支持蓝牙4.0", Toast.LENGTH_SHORT).show();
            finish();
        } else if(!mBluetoothAdapter.isEnabled()){
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }else{
            Toast.makeText(getApplicationContext(), "蓝牙已开启", Toast.LENGTH_LONG).show();
            scanBtDevices();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("Log", "requestCode" + requestCode + "\n resultCode=" + resultCode);
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                mBluetoothAdapter.enable();
                Toast.makeText(getApplicationContext(), "开启蓝牙", Toast.LENGTH_LONG).show();
                scanBtDevices();
            }else if(resultCode==RESULT_CANCELED){
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