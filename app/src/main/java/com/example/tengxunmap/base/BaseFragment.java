package com.example.tengxunmap.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 亮亮 on 2017/8/24.
 */

public abstract class BaseFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=initView(inflater);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initData();
    }

    protected abstract void initData();
    //初始化view,让子类实现
    protected abstract View initView(LayoutInflater inflater) ;
      
    
}
