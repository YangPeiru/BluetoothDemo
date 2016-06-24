package com.example.yang.myapplication.controller;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yang.myapplication.R;

import com.example.yang.myapplication.adapter.SwipeRecyclerViewAdapter;
import com.example.yang.myapplication.bean.ListBean;

import com.example.yang.myapplication.utils.Constants;
import com.example.yang.myapplication.utils.YUtils;
import com.example.yang.myapplication.view.SwipeRecyclerView;
import com.squareup.picasso.Picasso;

import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by ypr on 2016-06-08 17:15
 * 描述:广场数据显示类
 * TODO:
 */
public class NewestListController extends BaseController implements SwipeRecyclerView.OnRefreshAndLoadListener {
    private SwipeRefreshLayout mSwipeRefreshWidget;
    private LinearLayoutManager mLayoutManager;
    private SwipeRecyclerView mRecyclerView;
    private NewsListAdapter adapter;
    private int downCount = 1;
    private int upCount = 1;
    private ArrayList<ListBean> mNewsDatas;
    private PopupWindow mPopupWindow;
    private LinearLayout ll_report;
    private LinearLayout ll_save;
    private String[] imageUrls;//图片的地址
    // 用来记录按钮状态的Map
    public static Map<Integer, Boolean> isChecked = null;

    public NewestListController(Context context) {
        super(context);
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(mContext, "DOWN", Toast.LENGTH_SHORT).show();
                    mSwipeRefreshWidget.setRefreshing(false);
                    mNewsDatas.clear();
                    initList(11);
                    break;
                case 1:
                    Toast.makeText(mContext, "UP", Toast.LENGTH_SHORT).show();
                    int size = mNewsDatas.size();
                    for (int i = size; i < size + 3; i++) {
                        if (i>=15){
                            continue;
                        }
                        ListBean bean = new ListBean();
                        bean.name = "名字" + i;
                        mNewsDatas.add(bean);
                    }
                    mRecyclerView.completeLoad();
                    if (mNewsDatas.size() > 14) {
                        mRecyclerView.completeAllLoad();
                    }
                    break;
                default:
                    break;
            }
            resetLike();
            adapter.notifyDataSetChanged();
        }

    };

    /**
     * 重置点赞图标
     */
    private void resetLike() {
        isChecked = null;
        isChecked = new HashMap<Integer, Boolean>();
        for (int i = 0; i < mNewsDatas.size(); i++) {
            isChecked.put(i, false);
        }
    }

    /**
     * 加载数据
     *
     * @param size 加载的item数
     */
    private void initList(int size) {
        mNewsDatas = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            ListBean bean = new ListBean();
            bean.name = "名字" + i;
            mNewsDatas.add(bean);
        }
    }

    @Override
    protected View initView(Context context) {
        View view = View.inflate(context, R.layout.newest_and_hottest_list, null);
        // 注入
        x.view().inject(view);
        mSwipeRefreshWidget = (SwipeRefreshLayout) view.findViewById(R.id.srl_society);
        mRecyclerView = (SwipeRecyclerView) view.findViewById(R.id.lv_society_list);
        mSwipeRefreshWidget.setColorSchemeResources(R.color.color1, R.color.color2,
                R.color.color3, R.color.color4);
//		mSwipeRefreshWidget.setOnRefreshListener(this);//不需要再设置刷新功能

        mSwipeRefreshWidget.setProgressViewOffset(true, -50, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, mContext.getResources()
                        .getDisplayMetrics()));
        mRecyclerView.setOnRefreshAndLoadListener(mSwipeRefreshWidget, this);
        mRecyclerView.setHasFixedSize(false);
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        return view;
    }

    @Override
    public void initData() {
//		模拟数据显示 TODO:
        initList(5);
        resetLike();
        adapter = new NewsListAdapter();
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onUpLoad() {
        handler.sendEmptyMessageDelayed(1, 2000);
    }

    @Override
    public void onRefresh() {
        handler.sendEmptyMessageDelayed(0, 3000);
    }

    private class NewsListAdapter extends SwipeRecyclerViewAdapter implements View.OnClickListener {
        public NewsListAdapter() {
        }

        @Override
        public int GetItemCount() {
            return mNewsDatas.size() != 0 ? mNewsDatas.size() : 0;
        }

        @Override
        public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_ITEM) {
                View view = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_society, null);
                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                return new ItemViewHolder(view);
            }
            return null;
        }

        @Override
        public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ItemViewHolder) {
                // 给holder的view设置数据
                ListBean bean = mNewsDatas.get(position);
                ((ItemViewHolder) holder).rlPraise.setOnClickListener(new MyPraiseListener(position));
                ((ItemViewHolder) holder).rlComment.setOnClickListener(this);
                ((ItemViewHolder) holder).ivShare.setOnClickListener(this);
                ((ItemViewHolder) holder).ivMore.setOnClickListener(this);
                String str = "http://b.hiphotos.baidu.com/album/pic/item/caef76094b36acafe72d0e667cd98d1000e99c5f.jpg?psign=e72d0e667cd98d1001e93901213fb80e7aec54e737d1b867";
                // 设置默认图片
                ((ItemViewHolder) holder).ivPic.setImageResource(R.mipmap.logo);
                Picasso.with(mContext).load(str).placeholder(R.mipmap.logo).error(R.mipmap.logo).fit().into(((ItemViewHolder) holder).ivPic);
                ((ItemViewHolder) holder).tvName.setText(bean.name);
            /*
            * 下面就是在复用时对其进行判断，根据其状态来显示相应的内容，这样在滑动时条目就不会再错乱了
		    */
                if (isChecked.get(position) == true) {
                    ((ItemViewHolder) holder).ivPraisePic.setImageResource(R.mipmap.society_great_press);
                } else {
                    ((ItemViewHolder) holder).ivPraisePic.setImageResource(R.mipmap.society_great_normal);
                }
            }
        }

        class MyPraiseListener implements View.OnClickListener {
            private int position;

            public MyPraiseListener(int pos) {  // 在构造时将position传给它，这样就知道点击的是哪个条目的按钮
                this.position = pos;
            }

            @Override
            public void onClick(View v) {
                int vid = v.getId();
                if (vid == R.id.rl_society_item_praise) {
                    if (isChecked.get(position) == false) {
                        isChecked.put(position, true);       // 根据点击的情况来将其位置和相应的状态存入
                    } else if (isChecked.get(position) == true) {
                        isChecked.put(position, false);      // 根据点击的情况来将其位置和相应的状态存入
                    }
                    notifyDataSetChanged();
                }
            }
        }

        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()) {
                case R.id.rl_society_item_comment:
                    intent = new Intent(Constants.INTO_PARTICULARS);
//                    mContext.sendBroadcast(intent);
                    YUtils.showToast(mContext, "评论");
                    break;
                case R.id.iv_society_item_share:
                    YUtils.showToast(mContext, "分享");
                    break;
                case R.id.iv_society_item_more:
                    ColorDrawable cd = new ColorDrawable(0000);
                    mPopupWindow.setBackgroundDrawable(cd);
                    mPopupWindow.showAsDropDown(v);
//                    mPopupWindow.showAtLocation(, Gravity.BOTTOM,);
                    ll_save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            YUtils.showToast(mContext, "保存图片");
                        }
                    });
                    ll_report.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            YUtils.showToast(mContext, "举报照片");
                        }
                    });
                default:
                    break;
            }
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView sriHead;
        TextView tvName;
        TextView tvTime;
        TextView tvDate;
        ImageView ivPic;
        TextView tvBrowse;
        RelativeLayout rlPraise;
        TextView tvPraiseNum;
        ImageView ivPraisePic;
        RelativeLayout rlComment;
        TextView tvCommentNum;
        ImageView ivShare;
        ImageView ivMore;

        public ItemViewHolder(View itemView) {
            super(itemView);
            sriHead = (ImageView) itemView.findViewById(R.id.sri_society_item_head);
            tvName = (TextView) itemView.findViewById(R.id.tv_society_item_name);
            tvTime = (TextView) itemView.findViewById(R.id.tv_society_item_time);
            tvDate = (TextView) itemView.findViewById(R.id.tv_society_item_text);
            ivPic = (ImageView) itemView.findViewById(R.id.iv_society_item_pic);
            tvBrowse = (TextView) itemView.findViewById(R.id.tv_society_item_browsenum);
            tvDate = (TextView) itemView.findViewById(R.id.tv_society_item_text);
            tvPraiseNum = (TextView) itemView.findViewById(R.id.tv_society_item_praisenum);
            rlPraise = (RelativeLayout) itemView.findViewById(R.id.rl_society_item_praise);
            ivPraisePic = (ImageView) itemView.findViewById(R.id.iv_society_item_praisepic);
            rlComment = (RelativeLayout) itemView.findViewById(R.id.rl_society_item_comment);
            tvCommentNum = (TextView) itemView.findViewById(R.id.tv_society_item_commentnum);
            ivMore = (ImageView) itemView.findViewById(R.id.iv_society_item_more);
            ivShare = (ImageView) itemView.findViewById(R.id.iv_society_item_share);
        }
    }

}
