package com.example.tengxunmap.api;

import android.support.annotation.NonNull;

import com.example.tengxunmap.constant.Constant;
import com.example.tengxunmap.model.bean.ADBean;
import com.example.tengxunmap.model.bean.BaseBean;
import com.example.tengxunmap.model.bean.HomeShopBean;
import com.example.tengxunmap.model.bean.LoginBean;
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
}
