package com.example.tengxunmap.model.bean;

import android.os.Parcel;

/**
 * Created by 亮亮 on 2017/8/25.
 */

public class LoginBean {
    private UserInfoBean user;

    public UserInfoBean getUser() {
        return user;
    }

    public void setUser(UserInfoBean user) {
        this.user = user;
    }



    public LoginBean() {
    }

    protected LoginBean(Parcel in) {
        this.user = in.readParcelable(UserInfoBean.class.getClassLoader());
    }
}
