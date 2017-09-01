package com.example.tengxunmap.ui.home;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.example.tengxunmap.R;
import com.example.tengxunmap.adapter.HomeItemGvAdapter;
import com.example.tengxunmap.adapter.TuijianShopPagerAdapter;
import com.example.tengxunmap.base.BaseFragment;
import com.example.tengxunmap.model.bean.ADBean;
import com.example.tengxunmap.model.bean.TuijianShopBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by 亮亮 on 2017/8/30.
 */

public class HomeFragment extends BaseFragment implements HomeContract.HomeView{

    private View v;
    private SliderLayout slider;
    private HomeContract.HomePresenter presenter;
    private ImageView vp1,vp2;
    private ViewPager vp;
    private GridView gridview;
    //定义一个装载gridview的集合
    private ArrayList<GridView> gridViews = new ArrayList<>();
    private GridView item_gridview;

    @Override
    protected void initData() {
        //请求数据
        presenter.requestTuijianShops();

    }

    @Override
    protected View initView(LayoutInflater inflater) {
        EventBus.getDefault().register(this);
        presenter=new HomePresenterImpl(this);
        v = inflater.inflate(R.layout.fragment_home,null,false);
        slider = (SliderLayout) v.findViewById(R.id.slider);
        vp1 = (ImageView) v.findViewById(R.id.vp1);
        vp2 = (ImageView) v.findViewById(R.id.vp2);
        vp = (ViewPager) v.findViewById(R.id.vp);
        //初始化推荐商户的布局
        return v;
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateVP(List<ADBean.ContentBean> content){
        presenter.readADdata(content,slider);
        updateSlider();

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateVP(HashMap<Double,Double> map){
        //读map的数据
        Set<Map.Entry<Double, Double>> entries = map.entrySet();
        //拿到第一条记录 ，因为只有一条
        Map.Entry<Double, Double> next = entries.iterator().next();

        presenter.requestADdata(next.getKey()+","+next.getValue());

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showError(HomeFragment homefragment){
        showNetError();

    }
    //当获取到推荐商户时，怎么做
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showVP2(TuijianShopBean obj){
        //TODO 更新第二个viewpager
        ArrayList<TuijianShopBean.ListBean> list = (ArrayList<TuijianShopBean.ListBean>) obj.getList();
        showVp2(list);
    }

    @Override
    public void updateSlider() {
         //开始自动轮播
        slider.startAutoCycle();
    }

    @Override
    public void showNetError() {
        Toast.makeText(getActivity(), "网络异常，请检查您的网络", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showVp2(ArrayList<TuijianShopBean.ListBean> list) {
        //先初始化gridview
        double v = list.size() / 8.0d;
        //往上取值
        int ceil =(int) Math.ceil(v);
        for(int i=0;i<ceil;i++){
            //添加新集合，里面装载8条数据
            ArrayList<TuijianShopBean.ListBean> listBeen = generateNewList(list, i);
            item_gridview = (GridView) View.inflate(getActivity(),R.layout.item_gridview, null);
            //根据新集合出来的适配器
            HomeItemGvAdapter homeItemGvAdapter = new HomeItemGvAdapter(getActivity(), listBeen);
            item_gridview.setAdapter(homeItemGvAdapter);
            gridViews.add(item_gridview);

        }
        TuijianShopPagerAdapter ad = new TuijianShopPagerAdapter(gridViews);
        vp.setAdapter(ad);
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position==0){
                    resetViewPagerIndi();
                    vp1.setBackgroundResource(R.drawable.viewpager_selected);
                }else{
                    resetViewPagerIndi();
                    vp2.setBackgroundResource(R.drawable.viewpager_selected);
                }
            }



            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }
    private void resetViewPagerIndi() {
        vp1.setBackgroundResource(R.drawable.viewpager_normal);
        vp2.setBackgroundResource(R.drawable.viewpager_normal);
    }
    //每八条创建新的集合
    private ArrayList<TuijianShopBean.ListBean> generateNewList(ArrayList<TuijianShopBean.ListBean> lists, int page) {
        ArrayList<TuijianShopBean.ListBean> listBeen = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            if (8 * page + i <= lists.size()) {
                listBeen.add(lists.get(8 * page + i));
            }
        }
        return listBeen;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
