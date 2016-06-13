package com.example.yang.myapplication.activity;

import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Window;

import com.example.yang.myapplication.R;
import com.example.yang.myapplication.fragment.ContentFragment;

/**
 * Created by ypr on 2016-06-08 11:21
 * 描述:
 * TODO:
 */
public class MainUI extends BaseActivity {
    private static final String	TAG_CONTENT	= "tag_content";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //开启事务加载fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_content_container,new ContentFragment(),TAG_CONTENT);
        transaction.commit();
    }

}
