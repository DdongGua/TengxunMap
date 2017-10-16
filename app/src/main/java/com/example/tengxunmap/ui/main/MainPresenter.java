package com.example.tengxunmap.ui.main;

import com.example.tengxunmap.constant.Constant;
import com.example.tengxunmap.utils.SPUtils;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

/**
 * Created by 亮亮 on 2017/8/16.
 */

public class MainPresenter implements MainContract.Presenter {
    public MainContract.MainView mainView;
    private TencentLocationManager locationManager;
    private TencentLocationListener listener;
    private final MainActivity mainActivity;

    public MainPresenter(MainContract.MainView mainView) {

        this.mainView = mainView;
        mainActivity = (MainActivity) mainView;
    }

    @Override
    public void getLocation(TencentLocation location) {
        //给homefragment发送坐标过去
        listener = new TencentLocationListener() {
            @Override
            public void onLocationChanged(TencentLocation tencentLocation, int i, String s) {
                double latitude = tencentLocation.getLatitude();
                double longitude = tencentLocation.getLongitude();
                mainView.setLocationText(tencentLocation.getName());
                SPUtils.getInstance(mainActivity, Constant.SPNAME).putSp("location",latitude+","+longitude);
                //给homefragment发送坐标过去
                HashMap<Double, Double> map = new HashMap<>();
                map.put(latitude, longitude);
                EventBus.getDefault().post(map);
                cancleLocation();
            }

            @Override
            public void onStatusUpdate(String s, int i, String s1) {

            }
        };
        TencentLocationRequest request = TencentLocationRequest
                       .create();
        locationManager = TencentLocationManager.getInstance(mainActivity);
        locationManager.requestLocationUpdates(request, listener);

    }

    @Override
    public void cancleLocation() {
        locationManager.removeUpdates(listener);

    }


}
