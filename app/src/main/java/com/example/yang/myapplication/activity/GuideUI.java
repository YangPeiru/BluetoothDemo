package com.example.yang.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.yang.myapplication.R;
import com.example.yang.myapplication.utils.Constants;
import com.example.yang.myapplication.utils.PUtils;
import com.example.yang.myapplication.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ypr on 2016-06-08 11:21
 * 描述:引导
 * TODO:
 */
public class GuideUI extends BaseActivity implements View.OnClickListener {

    private ViewPager mViewPager;
    private LinearLayout mPointContainer;
    private View mPointSelected;
    private Button mBtnStart;

    private List<ImageView> mDatas;
    private int[] icons = new int[]{
            R.mipmap.guide_1,
            R.mipmap.guide_2,
            R.mipmap.guide_3
    };
    private int mPointSpace;            // 两点间的距离

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_guide);
        initView();
        initData();
    }

    private void initView() {
        mBtnStart = (Button) findViewById(R.id.guide_btn_start);
        mViewPager = (ViewPager) findViewById(R.id.guide_viewpager);
        mPointContainer = (LinearLayout) findViewById(R.id.guide_point_container);
        mPointSelected = findViewById(R.id.guide_point_selected);

        mBtnStart.setOnClickListener(this);
    }

    private void initData() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < icons.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(icons[i]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            mDatas.add(imageView);

            // 添加动态的点
            View point = new View(this);
            point.setBackgroundResource(R.drawable.point_normal);
            // 代码中，出现的宽高数据都是指的是px,dp-->px
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(PUtils.dp2px(this, 10), PUtils.dp2px(this, 10));
            if (i != 0) {
                params.leftMargin = PUtils.dp2px(this, 10);
            }
            mPointContainer.addView(point, params);
        }

        mViewPager.setAdapter(new GuideAdapter());
        mViewPager.addOnPageChangeListener(new GuidePageListener());
//        mViewPager.setOnPageChangeListener(new GuidePageListener());
        // 监听layout布局改变完成的监听
        mPointSelected.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // 当布局完成时的回调
                mPointSpace = mPointContainer.getChildAt(1).getLeft() - mPointContainer.getChildAt(0).getLeft();
                // 移除监听
                mPointSelected.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == mBtnStart) {
            clickStart();
        }
    }

    private void clickStart() {
        // 记录已经使用过引导页面
        PreferenceUtils.putBoolean(this, Constants.KEY_FIRST_USED, false);

        // 跳转
        startActivity(new Intent(this, MainUI.class));

        finish();
    }

    private class GuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mDatas != null ? mDatas.size() : 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView child = mDatas.get(position);
            container.addView(child);
            return child;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    private class GuidePageListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // 动态点偏移的像素
            int offsetPx = (int) (mPointSpace * positionOffset + 0.5f) + position * mPointSpace;
            // 给动态的点设置marginLeft
            RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) mPointSelected.getLayoutParams();
            params.leftMargin = offsetPx;
            mPointSelected.setLayoutParams(params);
        }

        @Override
        public void onPageSelected(final int position) {
            if (position == mDatas.size() - 1) {//position从0开始
                // 最后一个页面
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        mBtnStart.setVisibility(View.VISIBLE);
                        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                        AnimationSet set = new AnimationSet(true);
                        set.setDuration(1000);
                        set.addAnimation(rotateAnimation);
                        set.addAnimation(scaleAnimation);
                        mBtnStart.setAnimation(set);
                    }
                }, 1000);

            } else {
                mBtnStart.setVisibility(View.GONE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
