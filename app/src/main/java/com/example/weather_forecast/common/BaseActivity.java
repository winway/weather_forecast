package com.example.weather_forecast.common;

import androidx.appcompat.app.AppCompatActivity;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * @PackageName: com.example.weather_forecast.common
 * @ClassName: BaseActivity
 * @Author: winwa
 * @Date: 2023/1/23 8:26
 * @Description:
 **/
public class BaseActivity extends AppCompatActivity implements Callback.CommonCallback<String> {
    public void loadData(String url) {
        RequestParams params = new RequestParams(url);
        x.http().get(params, this);
    }

    @Override
    public void onSuccess(String result) {

    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        ex.printStackTrace();
    }

    @Override
    public void onCancelled(CancelledException cex) {

    }

    @Override
    public void onFinished() {

    }
}
