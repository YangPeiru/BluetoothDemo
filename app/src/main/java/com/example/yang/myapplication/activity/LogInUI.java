package com.example.yang.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.Toast;

import com.example.yang.myapplication.R;

/**
 * Created by ypr on 2016-06-13 15:21
 * 描述:注册
 * TODO:
 */
public class LogInUI extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
    }
    private long clickTime = -1L;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (System.currentTimeMillis() - clickTime > 2000) {
            Toast.makeText(this,"你不要我了么?再按一次我就消失了...", Toast.LENGTH_SHORT).show();
            clickTime = System.currentTimeMillis();
        } else {
            finish();
        }
        return true;
    }
}
