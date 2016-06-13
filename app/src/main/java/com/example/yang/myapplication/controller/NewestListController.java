package com.example.yang.myapplication.controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;


import android.support.v7.widget.ViewUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yang.myapplication.R;
import com.example.yang.myapplication.bean.ListBean;
import com.example.yang.myapplication.bean.PConst;
import com.example.yang.myapplication.utils.PUtils;
import com.example.yang.myapplication.utils.SwipeListViewOnScrollListener;
import com.example.yang.myapplication.view.SwipeRefreshLayout;

import com.squareup.picasso.Picasso;

import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ypr on 2016-06-08 17:15
 * 描述:
 * TODO:
 */
public class NewestListController extends BaseController {

    private ArrayList<ListBean> mNewsDatas;
    private NewsListAdapter mNewsAdapter;
    private SwipeRefreshLayout srl;
    private ListView lv_hottest;
    private PopupWindow mPopupWindow;
    private LinearLayout ll_report;
    private LinearLayout ll_save;
    private String[] imageUrls;//图片的地址
    // 用来记录按钮状态的Map
    public static Map<Integer, Boolean> isChecked;

    public NewestListController(Context context) {
        super(context);
    }

    @Override
    protected View initView(Context context) {
        View view = View.inflate(context, R.layout.newest_and_hottest_list, null);
        // 注入
        x.view().inject(view);
        srl = (SwipeRefreshLayout) view.findViewById(R.id.srl_society);
        lv_hottest = (ListView) view.findViewById(R.id.lv_society_list);
        srl.setColorScheme(R.color.colorWhiteFragmentBg,
                R.color.colorOrange,
                R.color.colorBlueColor,R.color.colorRedText);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View popupView = inflater.inflate(R.layout.popup_window_more, null);
        x.view().inject(view);
        mPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        // 这里设置显示PopuWindow之后在外面点击是否有效。如果为false的话，那么点击PopuWindow外面并不会关闭PopuWindow。
        mPopupWindow.setOutsideTouchable(true);//不能在没有焦点的时候使用
        ll_report = (LinearLayout) popupView.findViewById(R.id.ll_pop_more_report);
        ll_save = (LinearLayout) popupView.findViewById(R.id.ll_pop_clue);
        return view;
    }

    private SwipeRefreshLayout.OnRefreshListener orl = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            //TODO 更新请求
            srl.setRefreshing(false); // 关闭刷新
            Toast.makeText(mContext, "当前已经是最新的了~", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void initData() {
        srl.setOnRefreshListener(orl);
        lv_hottest.setOnScrollListener(new SwipeListViewOnScrollListener(srl));
//		模拟数据显示 TODO:
        mNewsDatas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ListBean bean = new ListBean();
            bean.name = "名字" + i;
            mNewsDatas.add(bean);
        }
        // 给listView加载数据
        mNewsAdapter = new NewsListAdapter();
        lv_hottest.setAdapter(mNewsAdapter);
    }

    private boolean flag = false;//用于判断点赞图标变化
    ViewHolder holder = null;

    private class NewsListAdapter extends BaseAdapter implements View.OnClickListener {

        public NewsListAdapter() {
            init(); // 一定要在这里调用，在构造Adapter对象时方便初始化
        }

        // 初使化操作，默认都是false
        private void init() {
            isChecked = new HashMap<Integer, Boolean>();
            for (int i = 0; i < mNewsDatas.size(); i++) {
                isChecked.put(i, false);
            }
        }

        @Override
        public int getCount() {
            if (mNewsDatas != null) {
                return mNewsDatas.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (mNewsDatas != null) {
                return mNewsDatas.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView =LayoutInflater.from(mContext).inflate(R.layout.item_society, null);
                holder = new ViewHolder();
                holder.sriHead = (ImageView) convertView.findViewById(R.id.sri_society_item_head);
                holder.tvName = (TextView) convertView.findViewById(R.id.tv_society_item_name);
                holder.tvTime = (TextView) convertView.findViewById(R.id.tv_society_item_time);
                holder.tvDate = (TextView) convertView.findViewById(R.id.tv_society_item_text);
                holder.ivPic = (ImageView) convertView.findViewById(R.id.iv_society_item_pic);
                holder.tvBrowse = (TextView) convertView.findViewById(R.id.tv_society_item_browsenum);
                holder.tvDate = (TextView) convertView.findViewById(R.id.tv_society_item_text);
                holder.tvPraiseNum = (TextView) convertView.findViewById(R.id.tv_society_item_praisenum);
                holder.rlPraise = (RelativeLayout) convertView.findViewById(R.id.rl_society_item_praise);
                holder.ivPraisePic = (ImageView) convertView.findViewById(R.id.iv_society_item_praisepic);
                holder.rlComment = (RelativeLayout) convertView.findViewById(R.id.rl_society_item_comment);
                holder.tvCommentNum = (TextView) convertView.findViewById(R.id.tv_society_item_commentnum);
                holder.ivMore = (ImageView) convertView.findViewById(R.id.iv_society_item_more);
                holder.ivShare = (ImageView) convertView.findViewById(R.id.iv_society_item_share);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            // 给holder的view设置数据
            ListBean bean = mNewsDatas.get(position);
            holder.rlPraise.setOnClickListener(new MyPraiseListener(position));
            holder.rlComment.setOnClickListener(this);
            holder.ivShare.setOnClickListener(this);
            holder.ivMore.setOnClickListener(this);
            String str = "http://b.hiphotos.baidu.com/album/pic/item/caef76094b36acafe72d0e667cd98d1000e99c5f.jpg?psign=e72d0e667cd98d1001e93901213fb80e7aec54e737d1b867";
            // 设置默认图片
            holder.ivPic.setImageResource(R.mipmap.logo);
            Picasso.with(mContext).load(str).placeholder(R.mipmap.logo).error(R.mipmap.logo).fit().into(holder.ivPic);
            holder.tvName.setText(bean.name);
            /*
            * 下面就是在复用时对其进行判断，根据其状态来显示相应的内容，这样在滑动时条目就不会再错乱了
		    */
            if (isChecked.get(position)==true) {
                holder.ivPraisePic.setImageResource(R.mipmap.society_great_press);
            } else {
                holder.ivPraisePic.setImageResource(R.mipmap.society_great_normal);
            }
            return convertView;
        }

        class MyPraiseListener implements View.OnClickListener {
            private int position;

            public MyPraiseListener(int pos){  // 在构造时将position传给它，这样就知道点击的是哪个条目的按钮
                this.position = pos;
            }
            @Override
            public void onClick(View v) {
                int vid=v.getId();
                if (vid ==  R.id.rl_society_item_praise){
                    if (isChecked.get(position) == false){
                        isChecked.put(position, true);       // 根据点击的情况来将其位置和相应的状态存入
                    } else if (isChecked.get(position) == true){
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
                    intent = new Intent(PConst.INTO_PARTICULARS);
//                    mContext.sendBroadcast(intent);
                    PUtils.showToast(mContext, "评论");
                    break;
                case R.id.iv_society_item_share:
                    PUtils.showToast(mContext, "分享");
                    break;
                case R.id.iv_society_item_more:
                    ColorDrawable cd = new ColorDrawable(0000);
                    mPopupWindow.setBackgroundDrawable(cd);
                    mPopupWindow.showAsDropDown(v);
//                    mPopupWindow.showAtLocation(, Gravity.BOTTOM,);
                    ll_save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PUtils.showToast(mContext, "保存图片");
                        }
                    });
                    ll_report.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PUtils.showToast(mContext, "举报照片");
                        }
                    });
                default:
                    break;
            }
        }
    }

    private class ViewHolder {
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

    }
}
