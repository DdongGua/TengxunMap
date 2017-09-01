package com.example.tengxunmap.dao.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import static android.R.attr.id;

/**
 * Created by 亮亮 on 2017/8/31.
 */
@Entity
public class UserInfo {
    @Id
    String phoneNum;
    String openId;
    String nickname;
    String dingdanNum;
    String shoucangNum;
    String youhuquanNum;
    String huiyuankaNum;
    String icon;
    @Generated(hash = 693322939)
    public UserInfo(String phoneNum, String openId, String nickname,
            String dingdanNum, String shoucangNum, String youhuquanNum,
            String huiyuankaNum, String icon) {
        this.phoneNum = phoneNum;
        this.openId = openId;
        this.nickname = nickname;
        this.dingdanNum = dingdanNum;
        this.shoucangNum = shoucangNum;
        this.youhuquanNum = youhuquanNum;
        this.huiyuankaNum = huiyuankaNum;
        this.icon = icon;
    }
    @Generated(hash = 1279772520)
    public UserInfo() {
    }
    public String getPhoneNum() {
        return this.phoneNum;
    }
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
    public String getOpenId() {
        return this.openId;
    }
    public void setOpenId(String openId) {
        this.openId = openId;
    }
    public String getNickname() {
        return this.nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getDingdanNum() {
        return this.dingdanNum;
    }
    public void setDingdanNum(String dingdanNum) {
        this.dingdanNum = dingdanNum;
    }
    public String getShoucangNum() {
        return this.shoucangNum;
    }
    public void setShoucangNum(String shoucangNum) {
        this.shoucangNum = shoucangNum;
    }
    public String getYouhuquanNum() {
        return this.youhuquanNum;
    }
    public void setYouhuquanNum(String youhuquanNum) {
        this.youhuquanNum = youhuquanNum;
    }
    public String getHuiyuankaNum() {
        return this.huiyuankaNum;
    }
    public void setHuiyuankaNum(String huiyuankaNum) {
        this.huiyuankaNum = huiyuankaNum;
    }
    public String getIcon() {
        return this.icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }



}
