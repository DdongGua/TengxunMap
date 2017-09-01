package com.example.tengxunmap.ui.home;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.example.tengxunmap.constant.Constant;
import com.example.tengxunmap.httputil.HttpHelper;
import com.example.tengxunmap.model.bean.ADBean;

import java.util.List;

import static com.example.tengxunmap.constant.Constant.IMAGE_AD;

/**
 * Created by 亮亮 on 2017/8/30.
 */

public class HomePresenterImpl implements HomeContract.HomePresenter{
    private HomeContract.HomeView view;

    public HomePresenterImpl(HomeContract.HomeView view) {
        this.view = view;
    }

    @Override
    public void requestTuijianShops() {
        HttpHelper.getInstance().getTuijianShops();
    }

    @Override
    public void requestADdata(String zuobiao) {
        HttpHelper.getInstance().getAD(zuobiao);


    }

    @Override
    public void readADdata(List<ADBean.ContentBean> content, SliderLayout slider) {
        HomeFragment fragment = (HomeFragment) this.view;
        for(ADBean.ContentBean bean:content){
            DefaultSliderView defaultSliderView = new DefaultSliderView(fragment.getActivity());
            String url = Constant.IMAGE_AD + bean.getPic();
            defaultSliderView.image(url);
            slider.addSlider(defaultSliderView);
        }

    }
}
