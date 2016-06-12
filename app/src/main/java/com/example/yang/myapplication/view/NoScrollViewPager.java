package com.example.yang.myapplication.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @项目名: Zhbj15
 * @包名: org.itheima15.zhbj.view
 * @类名: NoScrollViewPager
 * @作者: 肖琦
 * @创建时间: 2015-11-13 下午4:54:04
 * @描述: 不可滑动的viewPager
 * 
 * @更新时间: $Date: 2015-11-15 10:10:57 +0800 (Sun, 15 Nov 2015) $
 * @更新人: $Author: xq $
 * @版本: $Rev: 21 $
 * @更新内容: TODO:
 */
public class NoScrollViewPager extends LazyViewPager
{

	public NoScrollViewPager(Context context) {
		this(context, null);
	}

	public NoScrollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev)
	{
		// 不拦截
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev)
	{
		// 不去消费
		return false;
	}
	
//	public void setOffscreenPageLimit(int limit) {
////        if (limit < DEFAULT_OFFSCREEN_PAGES) {
////            Log.w(TAG, "Requested offscreen page limit " + limit + " too small; defaulting to " +
////                    DEFAULT_OFFSCREEN_PAGES);
////            limit = DEFAULT_OFFSCREEN_PAGES;
////        }
//        if (limit != mOffscreenPageLimit) {
//            mOffscreenPageLimit = limit;
//            populate();
//        }
//    }

}
