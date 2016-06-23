package com.example.yang.myapplication.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 */
public class TouchedViewPager extends ViewPager
{

	private float	mDownX;
	private float	mDownY;

	public TouchedViewPager(Context context) {
		this(context, null);
	}

	public TouchedViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev)
	{
		// 获得当前item的位置
		int currentItem = getCurrentItem();
		int count = getAdapter().getCount();

		switch (ev.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				mDownX = ev.getX();
				mDownY = ev.getY();

				// 请求父容器不要拦截touch事件
				// true:请求不要拦截
				// false:请求拦截
				getParent().requestDisallowInterceptTouchEvent(true);

				break;
			case MotionEvent.ACTION_MOVE:
				float moveX = ev.getX();
				float moveY = ev.getY();

				// 从右往左 --》 mDownX > moveX
				// 从左往右 --》 mDownX < moveX

				// 如果是水平操作时
				if (Math.abs(moveX - mDownX) > Math.abs(moveY - mDownY))
				{
					// 如果在最后一个页面，从右往左滑动,应该让父容器响应touch-->让父容器拦截我的touch
					if ((currentItem == count - 1) && (mDownX > moveX))
					{
						getParent().requestDisallowInterceptTouchEvent(false);
					}
					else if ((currentItem == 0) && (mDownX < moveX))
					{
						// 如果在第一个页面，从左往右滑动,应该让父容器响应touch-->让父容器拦截我的touch
						getParent().requestDisallowInterceptTouchEvent(false);
					}
					else
					{
						// 其他情况，自己响应
						getParent().requestDisallowInterceptTouchEvent(true);
					}
				}
				else
				{
					// 垂直操作时让父容器响应
					getParent().requestDisallowInterceptTouchEvent(false);
				}
				break;
			case MotionEvent.ACTION_UP:
				break;
			default:
				break;
		}

		return super.dispatchTouchEvent(ev);
	}
}
