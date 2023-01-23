package com.example.weather_forecast;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.weather_forecast.bean.WeatherInfoBean;
import com.example.weather_forecast.common.BaseFragment;
import com.example.weather_forecast.common.URLHelper;
import com.example.weather_forecast.db.DBManager;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeatherFragment#} factory method to
 * create an instance of this fragment.
 */
public class WeatherFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "WeatherFragment";
    private static final String ARG_CITY = "city";

    private ScrollView mWeatherSv;

    private TextView mTempTv;
    private TextView mCityTv;
    private TextView mCondTv;
    private TextView mDateTv;
    private TextView mWindTv;
    private TextView mTempRangeTv;
    private ImageView mPicTv;

    private LinearLayout mFutureLl;

    private TextView mIndexDressTv;
    private TextView mIndexCarTv;
    private TextView mIndexColdTv;
    private TextView mIndexSportTv;
    private TextView mIndexRayTv;

    private String city;
    private List<WeatherInfoBean.ResultsBean.IndexBean> mIndexBeanList;

    public static WeatherFragment newInstance(String city) {
        Bundle args = new Bundle();
        args.putString(ARG_CITY, city);
        WeatherFragment fragment = new WeatherFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        initView(view);

        setBg();

        Bundle bundle = getArguments();
        city = bundle.getString(ARG_CITY);

        String url = URLHelper.getWeatherUrl(city);
        Log.i(TAG, "onCreateView: " + url);
        loadData(url);

        return view;
    }

    private void setBg() {
        SharedPreferences preferences = getActivity().getSharedPreferences("bg", Context.MODE_PRIVATE);
        int id = preferences.getInt("bg", 0);
        if (id == 0) {
            mWeatherSv.setBackgroundResource(R.mipmap.bg);
        } else if (id == 1) {
            mWeatherSv.setBackgroundResource(R.mipmap.bg2);
        } else if (id == 2) {
            mWeatherSv.setBackgroundResource(R.mipmap.bg3);
        }
    }

    @Override
    public void onSuccess(String result) {
        super.onSuccess(result);
        parseData(result);
        int nRows = DBManager.updateContentByCity(city, result);
        if (nRows <= 0) {
            Log.i(TAG, "onSuccess: add " + city);
            DBManager.addCity(city, result);
        }
    }

    private void parseData(String result) {
        WeatherInfoBean weatherInfoBean = new Gson().fromJson(result, WeatherInfoBean.class);
        if (weatherInfoBean.getError() != 0) {
            Log.i(TAG, "parseData: " + weatherInfoBean.getStatus());
            return;
        }
        WeatherInfoBean.ResultsBean resultsBean = weatherInfoBean.getResults().get(0);
        resultsBean.setCurrentCity(city);
        mIndexBeanList = resultsBean.getIndex();
        WeatherInfoBean.ResultsBean.WeatherDataBean todayWeatherBean = resultsBean.getWeather_data().get(0);

        String[] split = todayWeatherBean.getDate().split("[：|)]");
        mTempTv.setText(split[1]);
        mCityTv.setText(resultsBean.getCurrentCity());
        mCondTv.setText(todayWeatherBean.getWeather());
        mDateTv.setText(weatherInfoBean.getDate());
        mWindTv.setText(todayWeatherBean.getWind());
        mTempRangeTv.setText(todayWeatherBean.getTemperature());
        Picasso.with(getActivity()).load(todayWeatherBean.getDayPictureUrl()).into(mPicTv);

        List<WeatherInfoBean.ResultsBean.WeatherDataBean> futureWeatherList = resultsBean.getWeather_data();
        futureWeatherList.remove(0);
        for (int i = 0; i < futureWeatherList.size(); i++) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_weather_future, null);
            view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            mFutureLl.addView(view);
            TextView fDateTv = view.findViewById(R.id.item_future_date_tv);
            TextView fCondTv = view.findViewById(R.id.item_future_condition_tv);
            TextView fTempTv = view.findViewById(R.id.item_future_temp_tv);
            ImageView fPicIv = view.findViewById(R.id.item_future_pic_iv);

            WeatherInfoBean.ResultsBean.WeatherDataBean weatherDataBean = futureWeatherList.get(i);
            fDateTv.setText(weatherDataBean.getDate());
            fCondTv.setText(weatherDataBean.getWeather());
            fTempTv.setText(weatherDataBean.getTemperature());
            Picasso.with(getActivity()).load(weatherDataBean.getDayPictureUrl()).into(fPicIv);
        }
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        super.onError(ex, isOnCallback);
        String result = DBManager.queryContentByCity(city);
        if (!TextUtils.isEmpty(result)) {
            parseData(result);
        }
    }

    private void initView(View view) {
        mWeatherSv = view.findViewById(R.id.weather_sv);

        mTempTv = view.findViewById(R.id.weather_temp_tv);
        mCityTv = view.findViewById(R.id.weather_city_tv);
        mCondTv = view.findViewById(R.id.weather_condition_tv);
        mDateTv = view.findViewById(R.id.weather_date_tv);
        mWindTv = view.findViewById(R.id.weather_wind_tv);
        mTempRangeTv = view.findViewById(R.id.weather_temprange_tv);
        mPicTv = view.findViewById(R.id.weather_pic_tv);

        mFutureLl = view.findViewById(R.id.weather_future_ll);

        mIndexDressTv = view.findViewById(R.id.weather_index_dress_tv);
        mIndexCarTv = view.findViewById(R.id.weather_index_car_tv);
        mIndexColdTv = view.findViewById(R.id.weather_index_cold_tv);
        mIndexSportTv = view.findViewById(R.id.weather_index_sport_tv);
        mIndexRayTv = view.findViewById(R.id.weather_index_ray_tv);

        mIndexDressTv.setOnClickListener(this);
        mIndexCarTv.setOnClickListener(this);
        mIndexColdTv.setOnClickListener(this);
        mIndexSportTv.setOnClickListener(this);
        mIndexRayTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int index = 0;
        switch (view.getId()) {
            case R.id.weather_index_dress_tv:
                index = 0;
                break;
            case R.id.weather_index_car_tv:
                index = 1;
                break;
            case R.id.weather_index_cold_tv:
                index = 2;
                break;
            case R.id.weather_index_sport_tv:
                index = 3;
                break;
            case R.id.weather_index_ray_tv:
                index = 4;
                break;
        }

        WeatherInfoBean.ResultsBean.IndexBean indexBean = mIndexBeanList.get(index);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(indexBean.getTipt());
        builder.setMessage(indexBean.getZs() + "\n" + indexBean.getDes());
        builder.setPositiveButton("确定", null);
        builder.show();
    }
}