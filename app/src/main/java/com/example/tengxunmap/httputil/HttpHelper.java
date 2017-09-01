package com.example.tengxunmap.httputil;

import android.util.Log;

import com.example.tengxunmap.api.API;
import com.example.tengxunmap.app.App;
import com.example.tengxunmap.constant.BSConstant;
import com.example.tengxunmap.constant.Constant;
import com.example.tengxunmap.dao.bean.DaoSession;
import com.example.tengxunmap.dao.bean.UserInfo;
import com.example.tengxunmap.model.bean.ADBean;
import com.example.tengxunmap.model.bean.BaseBean;
import com.example.tengxunmap.model.bean.HomeShopBean;
import com.example.tengxunmap.model.bean.LoginBean;
import com.example.tengxunmap.model.bean.LoginInfo;
import com.example.tengxunmap.model.bean.TuijianShopBean;
import com.example.tengxunmap.model.bean.UserInfoBean;
import com.example.tengxunmap.ui.home.HomeFragment;
import com.example.tengxunmap.ui.login.LoginActivity;
import com.example.tengxunmap.ui.main.MainActivity;
import com.example.tengxunmap.utils.AppUtil;
import com.example.tengxunmap.utils.SPUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by 亮亮 on 2017/8/24.
 */

public class HttpHelper {
    private static final String TAG = "HttpHelper";
    private static HttpHelper helper = new HttpHelper();
    private Retrofit retrofit;
    private final API api;

    private HttpHelper() {
        retrofit = new Retrofit.Builder()
                       .baseUrl(Constant.ROOT + Constant.URL_VERSION)
                       .addConverterFactory(GsonConverterFactory.create())
                       .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                       .build();
        api = retrofit.create(API.class);
    }

    public static HttpHelper getInstance() {

        return helper;
    }

    /*
     访问商户列表
      */
    public void getShopList(String openId, String location) {
        Observable<HomeShopBean> jingxuanshanghu = api.homeShop("jingxuanshanghu", openId, 0, location, 1);
        jingxuanshanghu.subscribeOn(Schedulers.io())
                       .observeOn(AndroidSchedulers.mainThread())
                       .subscribe(new Action1<HomeShopBean>() {
                           @Override
                           public void call(HomeShopBean homeShopBean) {
                               Log.e(TAG, "call: " + homeShopBean.getCode());
                               EventBus.getDefault().post(new MainActivity.MyEvent(homeShopBean));
                           }
                       });

    }

    /*
   发送验证码
    */
    public void getVerifyCode(String phone, long time, String ip, String mac) {
        Observable<BaseBean> verifycode = api.getVerifycode(BSConstant.VERIFY_CODE, phone, ip, mac, time, AppUtil.encryptSign((time - 444) + "", "", phone, ip), "1");
        verifycode.subscribeOn(Schedulers.io())
                       .observeOn(AndroidSchedulers.mainThread())
                       .subscribe(new Action1<BaseBean>() {
                           @Override
                           public void call(BaseBean baseBean) {
                               Log.e(TAG, "call: " + baseBean.getCode());
                           }
                       });

    }

    /*
     登录
     */
    public void login(final String phone, final String code) {
        Observable<BaseBean<LoginBean>> baseBean = api.login(BSConstant.LOGIN, phone, code, null, null, null, null, null);
        baseBean.subscribeOn(Schedulers.io())
                       .observeOn(AndroidSchedulers.mainThread())
                       .subscribe(new Action1<BaseBean<LoginBean>>() {
                           @Override
                           public void call(BaseBean<LoginBean> baseBean) {
                               if (baseBean.getCode() == 200) {
                                   LoginBean obj = baseBean.getObj();
                                   LoginInfo loginInfo = new LoginInfo(phone, code, obj);
                                   UserInfoBean user = obj.getUser();

//                           UserInfoBean user = obj.getUser();
//                           user.getAvatar();//头像
//                           user.getNickname();//昵称
//                           user.getPhone();//电话号码
//                           user.getSex();//拿到性别
//                           user.getXiadanshu();//订单数量
//                           user.getShoucangshanghu();//收藏店铺
//                           user.getYouhuiquan();//优惠券
//                           user.getHuiyuanka();//会员卡
                                   EventBus.getDefault().post(loginInfo);
                                   SPUtils instance = SPUtils.getInstance(App.appContext, Constant.SPNAME);
                                   instance.putSp("phonenum", phone);
                                   instance.putSp("logincode", code);
                                   instance.putSp("islogin", true);

                                   //跟以前的token一样，可以携带用户数据
                                   DaoSession daoSession = App.getDaoSession();
                                   UserInfo userInfo = new UserInfo(phone,
                                                  user.getOpenid(),
                                                  user.getNickname(),
                                                  user.getXiadanshu(),
                                                  user.getShoucangshanghu(),
                                                  user.getYouhuiquan(),
                                                  user.getHuiyuanka(),
                                                  user.getAvatar());
                                   //如果主键存在就替换，如果不存在就存入
                                   daoSession.insertOrReplace(userInfo);
                                   EventBus.getDefault().post(userInfo);
                                   EventBus.getDefault().post(new LoginActivity.FinishEvent());

                               } else {
                                   EventBus.getDefault().post("ERROR");

                               }

                           }
                       });
    }

    /*
   拿到广告轮播图片
    */
    public void getAD(String zuobiao) {
        Observable<BaseBean<ADBean>> ad = api.getAD(BSConstant.AD, zuobiao);
        ad.subscribeOn(Schedulers.io())
                       .observeOn(AndroidSchedulers.mainThread())
                       .subscribe(new Action1<BaseBean<ADBean>>() {
                           @Override
                           public void call(BaseBean<ADBean> baseBean) {
                               if (baseBean.getCode() == 200) {
                                   ADBean obj = baseBean.getObj();
                                   List<ADBean.ContentBean> content = obj.getContent();
                                   EventBus.getDefault().post(content);

                               } else {
                                   EventBus.getDefault().post(new HomeFragment());
                               }
                           }
                       });

    }

    /*
  获取首页推荐商户的信息
   */
    public void getTuijianShops() {
        Observable<BaseBean<TuijianShopBean>> shops = api.getTuijianSHops(BSConstant.HOME_COLLECTION);
        shops.subscribeOn(Schedulers.io())
                       .observeOn(AndroidSchedulers.mainThread())
                       .subscribe(new Action1<BaseBean<TuijianShopBean>>() {
                           @Override
                           public void call(BaseBean<TuijianShopBean> shops) {
                               TuijianShopBean obj = shops.getObj();
                               EventBus.getDefault().post(obj);
                           }
                       });
    }
}
