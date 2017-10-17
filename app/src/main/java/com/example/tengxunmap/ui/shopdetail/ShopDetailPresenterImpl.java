package com.example.tengxunmap.ui.shopdetail;

import com.example.tengxunmap.httputil.HttpHelper;

/**
 * Created by 亮亮 on 2017/10/12.
 */

public class ShopDetailPresenterImpl implements ShopDetailContract.presenter {
    ShopDetailContract.View view;
    ShopDetailActivity activity;

    public ShopDetailPresenterImpl(ShopDetailContract.View view) {
        this.view = view;
    }

    @Override
    public void getShopDetail(String shanghuid) {
        getShopUpDetail(shanghuid);
        getShopBelowDetail(shanghuid);


    }

    @Override
    public void getShopUpDetail(String shanghuid) {
        HttpHelper.getInstance().getShanghuUpDetail(shanghuid);
    }

    @Override
    public void getShopBelowDetail(String shanghuid) {
        HttpHelper.getInstance().getShanghuFuwu(shanghuid);
    }
}
