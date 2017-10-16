package com.example.tengxunmap.api;

import android.support.annotation.NonNull;


import com.example.tengxunmap.model.bean.lybbean.Test;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 亮亮 on 2017/9/14.
 */

public interface APILYB {
    @POST("checkUser")
    Observable<Test> login(
                   @NonNull @Query("uname") String username,
                   @NonNull @Query("pass") String pass,
                   @NonNull @Query("errornum") String num,
                   @NonNull @Query("sign") String sign
    );
}
