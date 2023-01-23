package com.example.weather_forecast.common;

import android.app.Application;

import com.example.weather_forecast.db.DBManager;

import org.xutils.x;

/**
 * @PackageName: com.example.weather_forecast.common
 * @ClassName: MyApplication
 * @Author: winwa
 * @Date: 2023/1/19 7:33
 * @Description:
 **/
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        DBManager.initDB(this);
    }
}
