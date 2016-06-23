package com.example.yang.myapplication.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
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
