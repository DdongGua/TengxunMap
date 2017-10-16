package com.example.tengxunmap.ui.home;

import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.example.tengxunmap.R;
import com.example.tengxunmap.adapter.HomeItemGvAdapter;
import com.example.tengxunmap.adapter.HomeShopAdapter;
import com.example.tengxunmap.adapter.TuijianShopPagerAdapter;
import com.example.tengxunmap.base.BaseFragment;
import com.example.tengxunmap.model.bean.ADBean;
import com.example.tengxunmap.model.bean.HomeShopListBean;
import com.example.tengxunmap.model.bean.TuijianShopBean;
import com.example.tengxunmap.ui.main.MainActivity;
import com.example.tengxunmap.utils.MeasureUtils;
import com.example.tengxunmap.widget.CustomScrollView;

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

public class HomeFragment extends BaseFragment implements HomeContract.HomeView {
    private static final String TAG = "HomeFragment";
    public static final int SCROLLTAGDELAY = 888;
    //指定一个专门scroll事件的标记值
    public static final int SCROLLTAG = 999;
    public View menu_dong;
    public View menu_jing;
    public CustomScrollView ptrsv;
    //定义两个int[]类型的参数，来存储动静menu的坐标
    int locationdong[] = new int[2]; //第一个数保存的是view距离屏幕坐标的值，第二个是距离顶部的值
    int locationjing[] = new int[2];
    int scrolllefttop[] = new int[2];
    //定义变量 检查scrollview是否处于停止状态
    float touchY = 0;
    private View v;
    private SliderLayout slider;
    private HomeContract.HomePresenter presenter;
    private ImageView vp1, vp2;
    private ViewPager vp;
    private GridView gridview;
    //定义一个装载gridview的集合
    private ArrayList<GridView> gridViews = new ArrayList<>();
    private GridView item_gridview;
    int page = 1;
    String paixu = "0";//0表示默认排序
    int netWorkStatus = 0;
    boolean scrollFlag = false;
    //装载总共recyclerview的数组
    ArrayList<HomeShopListBean.ListBean> totalLists = new ArrayList<>();

    //为滚动的操作添加handler
    private Handler mScrollHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (SCROLLTAG == msg.what) {
                Log.e(TAG, "handleMessage: touchy" + touchY + "--scrolly" + ptrsv.getScrollY());
                if (touchY != ptrsv.getScrollY()) {
                    //说明惯性事件发生，手指猛滑会发生
                    return;
                }
                 /*
                如果用的是scrollview可以用这个
                 */
//                拿到动静menu的坐标
                menu_dong.getLocationOnScreen(locationdong);
                menu_jing.getLocationOnScreen(locationjing);
//                判断view距离顶部的值
                if (locationdong[1] > locationjing[1]) {
                    menu_jing.setVisibility(View.GONE);
                } else {
                    menu_jing.setVisibility(View.VISIBLE);
                }

                //__________________________________________________________________________________
            }
            if (SCROLLTAGDELAY == msg.what) {
                if (touchY != ptrsv.getScrollY()) {
                    menu_dong.getLocationOnScreen(locationdong);
                    menu_jing.getLocationOnScreen(locationjing);
                    if (locationdong[1] > locationjing[1]) {
                        menu_jing.setVisibility(View.GONE);
                    } else {
                        menu_jing.setVisibility(View.VISIBLE);
                    }
                }
            }

        }
    };
    private MainActivity ma;
    private int downY;
    private int moveY;
    private int upY;
    private FrameLayout fl;
    private int vpheight;
    private View fragment_home_home;
    //变为公有 以便presenter调用
    public RecyclerView rv_list;
    private int endFlag;
    private HomeShopAdapter homeShopAdapter;

    @Override
    protected void initData() {
        //请求数据
        presenter.requestTuijianShops();
        //请求home list数据
        presenter.requestHomeListData(paixu, page+"");

    }

    @Override
    protected View initView(LayoutInflater inflater) {
        EventBus.getDefault().register(this);
        presenter = new HomePresenterImpl(this);
        v = inflater.inflate(R.layout.fragment_home, null, false);
        //拿到首页内容布局
        fragment_home_home = inflater.inflate(R.layout.fragment_home_content, null, false);
        fl = (FrameLayout) v.findViewById(R.id.fl);
        //找到scrollview
        ptrsv = (CustomScrollView) v.findViewById(R.id.ptrsv);
        //设置不允许刷新
//        ptrsv.setEnableRefresh(false);
        vp1 = (ImageView) fragment_home_home.findViewById(R.id.vp1);
        vp2 = (ImageView) fragment_home_home.findViewById(R.id.vp2);
        vp = (ViewPager) fragment_home_home.findViewById(R.id.vp);
        //拿到recyclerView
        rv_list = (RecyclerView) fragment_home_home.findViewById(R.id.rv_list);
        //嵌套scrollview使用时禁用掉自己的滚动
        rv_list.setNestedScrollingEnabled(false);
        //设置内容
        ptrsv.setupContainer(getActivity(), fragment_home_home);
        //两个菜单view
        menu_dong = v.findViewById(R.id.menu_dong);
        menu_jing = v.findViewById(R.id.menu_jing);
        vpheight = getResources().getDimensionPixelSize(R.dimen.viewpagerheightplus);
        ma = (MainActivity) getActivity();
        //给scrollview添加监听
        initScrollViewListener();
        slider = (SliderLayout) v.findViewById(R.id.slider);
        //设置menu条的显示隐藏，设置actionbar的透明度变化
        presenter.controlMenuDisplay();
        return v;
    }

    //为scrollview添加监听事件
    private void initScrollViewListener() {
        ptrsv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //当手指滑动或者当手指抬起的时候给handler发送事件，让handler处理滚动的逻辑
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    //记录当时scrolly的值
                    touchY = ptrsv.getScrollY();
                    mScrollHandler.sendEmptyMessage(SCROLLTAG);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    //当我up的时候touchy的值就不变了，只有当up的时候才会惯性事件
                    touchY = ptrsv.getScrollY();
                    mScrollHandler.sendEmptyMessage(SCROLLTAG);
                    //可能会有flying动作
                }
                //不管move还是up都延迟发送消息，并且消息是另一个what值
                mScrollHandler.sendEmptyMessageDelayed(SCROLLTAGDELAY, 100);
                return false;
            }
        });
        //接口回调
        //5.让用户传入接口
        //背景渐变效果
        ptrsv.setScrollViewListener(new CustomScrollView.ScrollViewListener() {
            @Override
            public void onscroll(CustomScrollView csv, int t) {
                Log.e(TAG, "onscroll:t的值 " + t);
                if (t <= 0) {
                    // 只是layout背景透明(仿知乎滑动效果)
                    ma.customActionbar.setBackgroundColor(Color.argb(0, 255, 255, 255));
                    //添加条目

                } else {
                    //大于某值得时候就需要设置透明度

                    float scale = (float) t / vpheight;  //透明度按照比例去设置
                    float alpha = (255 * scale);  //算出当前的透明度值
                    Log.e(TAG, "onscroll: hehe" + t);
                    // 只是layout背景透明(仿知乎滑动效果)
                    int alphaint = (int) alpha;
                    //因为算出来的值可能永远不能等于255，所以这么设置
                    if (alpha >= 250) {
                        alphaint = 255;
                    }
                    ma.customActionbar.setBackgroundColor(Color.argb(alphaint, 255, 255, 255));
                }
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateVP(List<ADBean.ContentBean> content) {
        presenter.setADdata(content, slider);
//        updateSlider();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateVP(HashMap<Double, Double> map) {
        //读map的数据
        Set<Map.Entry<Double, Double>> entries = map.entrySet();
        //拿到第一条记录 ，因为只有一条
        Map.Entry<Double, Double> next = entries.iterator().next();

        presenter.requestADdata(next.getKey() + "," + next.getValue());

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showError(HomeFragment homefragment) {
        showNetError();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showHomeShopList(HomeShopListBean listBean) {
        scrollFlag = false;
        //设置homelist适配器
        totalLists.addAll((ArrayList<HomeShopListBean.ListBean>) listBean.getList());
        if (homeShopAdapter == null) {
            homeShopAdapter = new HomeShopAdapter(getActivity(), totalLists);
            showHomeShopLists(rv_list, listBean.getList(), homeShopAdapter);
            presenter.setHomeListAdapter(homeShopAdapter);
        } else {
            homeShopAdapter.notifyDataSetChanged();
        }

    }

    //当获取到推荐商户时，怎么做
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showVP2(TuijianShopBean obj) {
        //TODO 更新第二个viewpager
        ArrayList<TuijianShopBean.ListBean> list = (ArrayList<TuijianShopBean.ListBean>) obj.getList();
        showVp2(list);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setErrorNetWork(Throwable throwable) {
        //TODO 更新第二个viewpager
        netWorkStatus = -1;

    }

    @Override
    public void updateSlider() {
        //开始自动轮播
        slider.startAutoCycle();
    }

    @Override
    public void showNetError() {
        //TODO 请检查网络
        Toast.makeText(getActivity(), "网络异常，请检查您的网络", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showVp2(ArrayList<TuijianShopBean.ListBean> list) {
        //先初始化gridview
        double v = list.size() / 8.0d;
        //往上取值
        int ceil = (int) Math.ceil(v);
        for (int i = 0; i < ceil; i++) {
            //添加新集合，里面装载8条数据
            ArrayList<TuijianShopBean.ListBean> listBeen = generateNewList(list, i);
            item_gridview = (GridView) View.inflate(getActivity(), R.layout.item_gridview, null);
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
                if (position == 0) {
                    resetViewPagerIndi();
                    vp1.setBackgroundResource(R.drawable.viewpager_selected);
                } else {
                    resetViewPagerIndi();
                    vp2.setBackgroundResource(R.drawable.viewpager_selected);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void showHomeShopLists(RecyclerView rv, List<HomeShopListBean.ListBean> lists, HomeShopAdapter adapter) {
        //设置layoutmanager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(linearLayoutManager);
        setRecyclerViewListener(rv, lists, adapter);
    }
    //监听RecyclerView的滑动事件
    @Override
    public void setRecyclerViewListener(RecyclerView rv, List<HomeShopListBean.ListBean> lists, final HomeShopAdapter adaper) {
        //拿到除去RecyclerView的高度
        //slider高度
        int sliderHeight = getResources().getDimensionPixelSize(R.dimen.sliderhight);
        //第二个viewpager的高度
        int viewpagermargin = getResources().getDimensionPixelSize(R.dimen.viewpagermargin);
        int viewpagerHeight = getResources().getDimensionPixelSize(R.dimen.viewpagerheight);
        //menu的高度
        int menuHeight = getResources().getDimensionPixelSize(R.dimen.menuheight);
        //recyclerview上边高度的总和
        int upRecyclerView = sliderHeight + viewpagerHeight + menuHeight + viewpagermargin;
        //recyclerview的高度
        int recyclerViewHeight = getResources().getDimensionPixelSize(R.dimen.item_homeshop) * (lists.size());
        //状态栏的高度
        int statusBarHeight = MeasureUtils.getStatusBarHeight(getActivity());
        //底部radiogroup的高度
        int rg_height = getResources().getDimensionPixelSize(R.dimen.rg_height);
        //拿手机屏幕的高度


        endFlag = upRecyclerView + recyclerViewHeight - MeasureUtils.getScreenHeight2(getActivity()) + statusBarHeight + rg_height;

        Log.e(TAG, "upRecyclerView: " + endFlag);

        //给他设置滑动监听
        ptrsv.setMScrollViewListener(new CustomScrollView.MScrollViewListener() {
            @Override
            public void onscroll(CustomScrollView csv, int t) {
                //已经滑动到最底部
                if ((t >= endFlag) && !scrollFlag) {
                    //更新
                    scrollFlag = true;
                    adaper.setIsEnd(true);
                    adaper.notifyDataSetChanged();
                    //再通知加载第二页数据
                    if (netWorkStatus == -1) {
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                presenter.requestHomeListData(paixu, (++page) + "");
                            }
                        }, 3000);
                    } else {
                        presenter.requestHomeListData(paixu, (++page) + "");
                    }
                }
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
