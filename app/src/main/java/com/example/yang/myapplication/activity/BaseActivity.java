package com.example.yang.myapplication.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by ypr on 2016-06-13 17:01
 * 描述:
 * TODO:
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }
    private long clickTime = -1L;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 两次Back键退出系统
     */
    private void exit() {
        if (System.currentTimeMillis() - clickTime > 2000) {
            Toast.makeText(this,"再按一次退出", Toast.LENGTH_SHORT).show();
            clickTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

}
