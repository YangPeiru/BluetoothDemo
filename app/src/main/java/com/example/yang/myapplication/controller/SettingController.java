package com.example.yang.myapplication.controller;

import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.yang.myapplication.R;

import org.xutils.x;

/**
 * Created by ypr on 2016/6/25.
 * 描述:
 * TODO:
 */
public class SettingController extends BaseController {

	private WebView mWebView;

	public SettingController(Context context) {
		super(context);
	}

	@Override
	protected View initView(Context context) {
		View view = View.inflate(context, R.layout.controller_setting, null);
		x.view().inject(view);
		mWebView = (WebView) view.findViewById(R.id.wv_setting);
		return view;
	}

	@Override
	public void initData() {
		super.initData();
		//设置返回键监听
		mWebView.setFocusable(true);
		mWebView.setFocusableInTouchMode(true);
		mWebView.setOnKeyListener(backListener);
		//获得webView属性对象
		WebSettings mWebSettings = mWebView.getSettings();
		//设置支持js
		mWebSettings.setJavaScriptEnabled(true);
		//设置可以支持缩放
		mWebSettings.setSupportZoom(true);
		//设置出现缩放工具
		mWebSettings.setBuiltInZoomControls(true);
		//扩大比例的缩放
		mWebSettings.setUseWideViewPort(true);
		//自适应屏幕
		mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
		mWebSettings.setLoadWithOverviewMode(true);
		//使用缓存
		mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		//不使用缓存
//		mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		//加载的url
		mWebView.loadUrl("http://www.vzg.com/");
		//设置web视图,重用当前浏览器
		mWebView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
		//设置加载数据时的操作
		mWebView.setWebChromeClient(mWebChromeClient);
		Log.d("Log", "数据加载完成");
	}

	private View.OnKeyListener backListener = new View.OnKeyListener() {
		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_BACK
					&& event.getAction() == KeyEvent.ACTION_DOWN) {
				if (mWebView.canGoBack()) {
					mWebView.goBack();//返回上一页面
					return true;
				}
			}
			return false;
		}
	};

	private WebChromeClient mWebChromeClient = new WebChromeClient() {
		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			// TODO Auto-generated method stub
			if (newProgress == 100) {
				// 网页加载完成
			} else {
				// 加载中
			}

		}
	};
}
