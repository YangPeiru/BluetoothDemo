package com.example.yang.myapplication.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yang.myapplication.R;

/**
 * Created by Yang on 2016/6/12.
 */
public abstract class BaseFragment extends Fragment {

    private ViewGroup childContainer;
    public View rootView;

    /**
     * 获取绑定Activity的上下文参数
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    /**
     * 抽象方法，实现此方法子类返回对应布局资源ID
     *
     * @return 子类资源ID
     */
    public abstract int getContentLayoutRes();

    /**
     * 在Fragment onCreateView()阶段完成布局填充
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return 返回BaseFragment子类填充布局
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_base, container, false);
            childContainer = (ViewGroup) rootView.findViewById(R.id.fl_base_container);
            inflater.inflate(getContentLayoutRes(), childContainer, true);
        }
        return rootView;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
