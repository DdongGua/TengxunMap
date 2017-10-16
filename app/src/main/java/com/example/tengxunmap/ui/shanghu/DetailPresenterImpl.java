package com.example.tengxunmap.ui.shanghu;

import com.example.tengxunmap.httputil.HttpHelper;

/**
 * Created by 亮亮 on 2017/10/12.
 */

public class DetailPresenterImpl implements ShanghuDetailContract.DetaiPresenter {
    private ShanghuDetailContract.DetailView view;

    public DetailPresenterImpl(ShanghuDetailContract.DetailView view) {
        this.view = view;
    }

    @Override
    public void load(String shanghuid) {
        //去通过商户id去加载数据
        HttpHelper.getInstance().getShanghuDetail(shanghuid);
    }
}
