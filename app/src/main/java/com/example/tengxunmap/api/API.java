package com.example.tengxunmap.api;

import android.support.annotation.NonNull;

import com.example.tengxunmap.constant.Constant;
import com.example.tengxunmap.model.bean.ADBean;
import com.example.tengxunmap.model.bean.BaseBean;
import com.example.tengxunmap.model.bean.HomeShopBean;
import com.example.tengxunmap.model.bean.HomeShopListBean;
import com.example.tengxunmap.model.bean.LoginBean;
import com.example.tengxunmap.model.bean.Shanghu;
import com.example.tengxunmap.model.bean.ShanghuFuwuListBean;
import com.example.tengxunmap.model.bean.ShanghuFuwuyuangongBean;
import com.example.tengxunmap.model.bean.ShanghuPinglunBean;
import com.example.tengxunmap.model.bean.ShanghuUpDetailsBean;
import com.example.tengxunmap.model.bean.ShopCommentBean;
import com.example.tengxunmap.model.bean.TuijianShopBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 亮亮 on 2017/8/24.
 */

public interface API {
    /**
     * 首页店铺列表
     *
     * @param bs
     * @param openid
     * @param order      （0默认 2距离 1评分）
     * @param coordinate
     * @param page
     * @return
     */
    @GET(Constant.SHOP)
    Observable<HomeShopBean> homeShop(
                   @Query("bs") String bs,
                   @Query("openid") String openid,
                   @Query("paixu") int order,
                   @Query("zuobiao") String coordinate,
                   @Query("page") int page
    );

    /**
     * 登录
     *
     * @param bs
     * @param phone
     * @param sms
     * @param wxOpenId
     * @param unionId
     * @param cid
     * @return
     */
    @FormUrlEncoded
    @POST(Constant.USER)
    Observable<BaseBean<LoginBean>> login(
                   @Field("bs") String bs,
                   @Field("phone") String phone,
                   @Field("sms") String sms,
                   @Field("wxopenid") String wxOpenId,
                   @Field("unionid") String unionId,
                   @Field("cid") String cid,
                   @Field("wxname") String wxName,
                   @Field("wxtouxiang") String wxAvatar
    );

    /**
     * 获得验证码
     *
     * @param bs
     * @param phone
     * @param ip
     * @param mac
     * @param time
     * @param sign
     * @param type
     * @return
     */
    @GET(Constant.USER)
    Observable<BaseBean> getVerifycode(
                   @NonNull @Query("bs") String bs,
                   @NonNull @Query("phone") String phone,
                   @NonNull @Query("ip") String ip,
                   @NonNull @Query("mac") String mac,
                   @NonNull @Query("time") long time,
                   @NonNull @Query("sign") String sign,
                   @NonNull @Query("leixing") String type
    );

    @GET(Constant.USER)
    Observable<BaseBean<ADBean>> getAD(
                   @Query("bs") String bs,
                   @Query("zuobiao") String zuobiao
    );
    //http://www.quanminlebang.com/api100/shanghu.php?bs=shoucangtuijian
    //获得首页第二个viewpager的数据即 推荐商户
    @GET(Constant.SHOP)
    Observable<BaseBean<TuijianShopBean>> getTuijianSHops(
                   @NonNull @Query("bs") String bs
    );
    //http://www.quanminlebang.com/api100/shanghu.php?bs=jingxuanshanghu&openid=104cca5fad614b53e494e5198f4cdb47&paixu=0&zuobiao=116.125586,40.232195&page=1
    @GET(Constant.SHOP)
    Observable<BaseBean<HomeShopListBean>>  getHomeListBean(
                   @NonNull @Query("bs") String bs,
                   @Query("openid") String openid,
                   @NonNull @Query("paixu") String paixu,
                   @NonNull @Query("zuobiao") String zuobiao,
                   @NonNull @Query("page") String page

    );
    /**
     * 访问商户详情的单个条目
     *
     */
    @GET(Constant.SHOP)
    Observable<BaseBean<Shanghu>>  getShanghuDetail(
                   @NonNull @Query("bs") String bs,
                   @Query("openid") String openid,
                   @NonNull @Query("shanghuid") String shanghuid,
                   @Query("zuobiao") String zuobiao
    );
    /*
   获取商户详情数据
    */
    @GET(Constant.SHOP)
    Observable<BaseBean<ShanghuUpDetailsBean>>   getShanghuUpDetail(
                   @Query("bs") String bs,
                   @Query("openid") String openid,
                   @Query("shanghuid") String shanghuid,
                   @Query("zuobiao") String zuobiao
    );
    /**
     * 获得商户详情中服务项目内容
     */
    @GET(Constant.SHOP)
    Observable<BaseBean<ShanghuFuwuListBean>> getShanghuFuwuList(
                   @Query("bs") String bs,
                   @Query("shanghuid") String shanghuid,
                   @Query("page") String page
    );
    /**
     * 获得商户服务员工列表
     * @param bs
     * @param shanghuid
     * @param page
     * @return
     */
    @GET(Constant.SHOP)
    Observable<BaseBean<ShanghuFuwuyuangongBean>> getShanghuFuwuyuangongList(
                   @Query("bs") String bs,
                   @Query("shanghuid") String shanghuid,
                   @Query("page") String page
    );
    /**
     * 获得商户评论
     * @param bs
     * @param shanghuid
     * @param page
     * @return
     */
    @GET(Constant.SHOP)
    Observable<BaseBean<ShopCommentBean>> getShanghuPinglun(
                   @Query("bs") String bs,
                   @Query("shanghuid") String shanghuid,
                   @Query("page") String page
    );
}
