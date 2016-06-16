package com.example.yang.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.yang.myapplication.R;
import com.example.yang.myapplication.utils.PUtils;

/**
 * Created by ypr on 2016-06-13 15:20
 * 描述:登录
 * TODO:
 */
public class SignInUI extends BaseActivity implements View.OnClickListener {

    private Button mSign;
    private Button mLogin;
    private EditText mUserName;
    private EditText mPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_signin);
        initView();
        initData();
    }

    private void initView() {
        mUserName = (EditText) findViewById(R.id.et_user);
        mPassword = (EditText) findViewById(R.id.et_password);
        mLogin = (Button) findViewById(R.id.btn_login);
        mSign = (Button) findViewById(R.id.btn_sign);
    }

    private void initData() {
        mLogin.setOnClickListener(this);
        mSign.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                startActivity(new Intent(this,LogInUI.class));
                break;
            case R.id.btn_sign:
                String user = mUserName.getText().toString();
                String paw = mPassword.getText().toString();
                if (user.length()==0||paw.length()==0|| TextUtils.isEmpty(user)||TextUtils.isEmpty(paw)){
                    PUtils.showToast(this,"名字或密码不能为空");
                } else if (user.equals("123")&&paw.equals("123")){
                    PUtils.showToast(this,"登录成功");
                    startActivity(new Intent(this,MainUI.class));
                    finish();
                }else{
                    PUtils.showToast(this,"密码不正确");
                }
                break;
            default:
                break;
        }
    }
}
