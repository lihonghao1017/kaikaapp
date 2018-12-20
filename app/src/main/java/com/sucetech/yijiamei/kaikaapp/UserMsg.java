package com.sucetech.yijiamei.kaikaapp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lihh on 2018/9/20.
 */

public class UserMsg {
    private static SharedPreferences sp;
    private static final int POSITION_ERROR = 1000;// 定位经纬度偏移误差允许范围

    public static void initUserMsg(Context context) {
        if (sp == null) {
            sp = context.getSharedPreferences("UserMsg", Context.MODE_PRIVATE);
        }
    }

    public static void saveToken(String Token) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("Token", Token);
        edit.commit();
    }

    public static String getToken() {
        return sp.getString("Token", "");
    }
    public static void saveYiyuan(String yiyuan){
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("yiyuan", yiyuan);
        edit.commit();

    }
    public static String getYiyuan(){
        return sp.getString("yiyuan", "");
    }
    public static void saveUserName(String name) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("UserName", name);
        edit.commit();
    }

    public static String getUserName() {
        return sp.getString("UserName", "");
    }
    public static void savePwd(String pwd) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("pwd", pwd);
        edit.commit();
    }

    public static void saveMac(String pwd) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("Mac", pwd);
        edit.commit();
    }

    public static String getMac() {
        return sp.getString("Mac", "");
    }

    public static String getPwd() {
        return sp.getString("pwd", "");
    }

    public static void savePizhongByCarId(String carId,String json) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(carId, json);
        edit.commit();
    }

    public static String getPizhongByCarId(String carId) {
        return sp.getString(carId, "");
    }


    public static void saveMapZoomLevel(int size) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt("ZoomLevel", size);
        edit.commit();
    }

    public static int getMapZoomLevel() {
        return sp.getInt("ZoomLevel", -1);
    }
}
