package com.example.tengxunmap.ui.shanghu;

/**
 * Created by 亮亮 on 2017/10/12.
 */

public interface ShanghuDetailContract{  //详情视图
interface DetailView{
    //展示信息逻辑
    void showInfo();
    //网络异常
    //加载中

}
//详情presenter
interface  DetaiPresenter{
    //加载数据
    void  load(String shanghuid);
}
}
