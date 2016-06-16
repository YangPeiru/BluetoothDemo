package com.example.yang.myapplication.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;

import com.example.yang.myapplication.R;
import com.example.yang.myapplication.utils.Constants;
import com.example.yang.myapplication.utils.PreferenceUtils;


/**
 * Created by ypr on 2016-06-08 11:21
 * 描述:
 * TODO:
 */
public class SplashUI extends BaseActivity {

    private static final long DURATION = 1500;
    private View mRootView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
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
                    //不是第一次直接进入登录界面
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


}
