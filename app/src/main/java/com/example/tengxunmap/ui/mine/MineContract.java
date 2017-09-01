package com.example.tengxunmap.ui.mine;

import com.example.tengxunmap.dao.bean.UserInfo;
import com.example.tengxunmap.model.bean.LoginInfo;
import com.example.tengxunmap.model.bean.UserInfoBean;
import com.example.tengxunmap.utils.SPUtils;

/**
 * Created by 亮亮 on 2017/8/25.
 */

public interface MineContract {
    public interface MineView{
        void hideActionBar();
        void  showLoginView(UserInfo user);
        void showLoginError();
    }
    interface MinePresenter{
        //只需要操作逻辑事件
        //隐藏actionbar
        void hide();
        //读取缓存的登录信息
        void readSavedLoginInfo(SPUtils spUtils);
        //保存登录状态
        void saveLoginStatus(LoginInfo bean);
    }

}
