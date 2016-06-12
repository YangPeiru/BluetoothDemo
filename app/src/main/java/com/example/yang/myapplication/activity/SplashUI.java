package com.example.yang.myapplication.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Toast;

import com.example.yang.myapplication.R;
import com.example.yang.myapplication.utils.Constants;
import com.example.yang.myapplication.utils.PreferenceUtils;


/**
 * Created by ypr on 2016-06-08 11:21
 * 描述:
 * TODO:
 */
public class SplashUI extends AppCompatActivity {

    private static final long DURATION = 1500;
    private View mRootView;
    private long clickTime = -1L;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mRootView = findViewById(R.id.splash_root);

        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_PARENT, 0.5f);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0,1);

        AnimationSet set = new AnimationSet(true);
        set.setDuration(DURATION);
        set.addAnimation(scaleAnimation);
        set.addAnimation(alphaAnimation);
        set.setInterpolator(new LinearInterpolator());
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                boolean flag = PreferenceUtils.getBoolean(SplashUI.this, Constants.KEY_FIRST_USED, true);
                if (flag){
                    //第一次进入程序,进入引导界面
                    startActivity(new Intent(SplashUI.this,GuideUI.class));
                }else{
                    //不是第一次直接进入主界面
                    startActivity(new Intent(SplashUI.this,MainUI.class));
                }
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mRootView.setAnimation(set);
    }

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
