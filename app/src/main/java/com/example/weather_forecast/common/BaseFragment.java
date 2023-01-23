package com.example.weather_forecast.common;

import androidx.fragment.app.Fragment;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * @PackageName: com.example.weather_forecast.common
 * @ClassName: BaseFragment
 * @Author: winwa
 * @Date: 2023/1/19 7:32
 * @Description:
 **/
public class BaseFragment extends Fragment implements Callback.CommonCallback<String> {
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
