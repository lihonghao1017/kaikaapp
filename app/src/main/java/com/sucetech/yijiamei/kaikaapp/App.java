package com.sucetech.yijiamei.kaikaapp;

import android.app.Application;

import com.mapbar.scale.ScaleCalculator;

/**
 * Created by lihh on 2018/9/19.
 */

public class App extends Application {
//    public Vibrator mVibrator;
    @Override
    public void onCreate() {
        super.onCreate();
        UserMsg.initUserMsg(this);
        ScaleCalculator.init(this, 0, 2199, 1080, 2f);
//       UserMsg.initUserMsg(this);
//        locationService = new LocationService(getApplicationContext());
//        mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
//        SDKInitializer.initialize(getApplicationContext());
    }
}
