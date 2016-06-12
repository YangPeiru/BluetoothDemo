package com.example.yang.myapplication.utils;

//import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;

import com.example.yang.myapplication.view.SwipeRefreshLayout;

/**
 * 下拉刷新ListView设置滑动监听,避免冲突bug
 * Created by ypy on 2016/3/15.
 */
public class SwipeListViewOnScrollListener implements AbsListView.OnScrollListener {

    private SwipeRefreshLayout refreshLayout;
    private AbsListView.OnScrollListener mOnScrollListener;//传入需要监听滑动的监听

    public SwipeListViewOnScrollListener(SwipeRefreshLayout refreshLayout) {
        this.refreshLayout = refreshLayout;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        View firstView = view.getChildAt(firstVisibleItem);
        //当firstVisibleItem是第0位。如果firstView==null说明列表为空，需要刷新;或者top==0说明已经到达列表顶部, 也需要刷新
        if (firstVisibleItem == 0 && (firstView == null || firstView.getTop() == 0)) {
            refreshLayout.setEnabled(true);
        } else {
            refreshLayout.setEnabled(false);
        }
        //调用自定义滑动监听
        if (null != mOnScrollListener) {
            mOnScrollListener.onScroll(view, firstVisibleItem,
                    visibleItemCount, totalItemCount);
        }
    }
}
