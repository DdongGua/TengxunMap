package com.example.tengxunmap.ui.home;

import com.daimajia.slider.library.SliderLayout;
import com.example.tengxunmap.model.bean.ADBean;
import com.example.tengxunmap.model.bean.TuijianShopBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 亮亮 on 2017/8/30.
 */

public interface HomeContract {
    interface HomeView{
        //更新viewPager，slider
        void updateSlider();
        //显示网络异常
        void showNetError();
        //初始化首页推荐商户的view
        void showVp2(ArrayList<TuijianShopBean.ListBean> list );
    }
    interface HomePresenter{
        //获取首页的推荐商户的信息
        void requestTuijianShops();
        //请求广告图片，数据
        void requestADdata(String zuobiao);

        //解析广告数据，展示slider
        void readADdata(List<ADBean.ContentBean> content, SliderLayout slider);
    }
}
