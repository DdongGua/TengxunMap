package com.example.tengxunmap.ui.mine;

import android.text.TextUtils;

import com.example.tengxunmap.app.App;
import com.example.tengxunmap.constant.Constant;
import com.example.tengxunmap.dao.bean.DaoSession;
import com.example.tengxunmap.dao.bean.UserInfo;
import com.example.tengxunmap.dao.bean.UserInfoDao;
import com.example.tengxunmap.httputil.HttpHelper;
import com.example.tengxunmap.model.bean.LoginBean;
import com.example.tengxunmap.model.bean.LoginInfo;
import com.example.tengxunmap.utils.SPUtils;

/**
 * Created by 亮亮 on 2017/8/25.
 */

public class MinePresenterImpl implements MineContract.MinePresenter {
    private MineContract.MineView view;

    public MinePresenterImpl(MineContract.MineView view) {
        this.view = view;
    }

    @Override

    public void hide() {

        view.hideActionBar();
    }

    @Override
    public void readSavedLoginInfo(SPUtils spUtils) {
        //拿到最近登录的账号
        String phonenum = (String) spUtils.getSp("phonenum", String.class);
//        String code = (String) spUtils.getSp("logincode", String.class);
//        if (!TextUtils.isEmpty(phonenum) && !TextUtils.isEmpty(code)) {
//            HttpHelper.getInstance().login(phonenum, code);
//        }
        //从数据库中拿缓存数据
        DaoSession daoSession = App.getDaoSession();
        UserInfoDao userInfoDao = daoSession.getUserInfoDao();
        if (!TextUtils.isEmpty(phonenum)) {
            //拿到数据库中的数据
            UserInfo load = userInfoDao.load(phonenum);

            view.showLoginView(load);
        }

    }

    @Override
    public void saveLoginStatus(LoginInfo bean) {
        MineFragment view = (MineFragment) this.view;
        //三个保存： 状态 ，手机号，验证码
        String phonenum = bean.getPhonenum();
        String code = bean.getCode();
        SPUtils instance = SPUtils.getInstance(view.getActivity(), Constant.SPNAME);
        instance.putSp("phonenum", phonenum);
        instance.putSp("logincode", code);
        instance.putSp("islogin", true);


    }
}
