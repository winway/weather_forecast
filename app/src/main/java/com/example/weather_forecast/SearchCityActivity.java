package com.example.weather_forecast;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.weather_forecast.bean.WeatherInfoBean;
import com.example.weather_forecast.common.BaseActivity;
import com.example.weather_forecast.common.URLHelper;
import com.google.gson.Gson;

public class SearchCityActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "SearchCityActivity";

    private EditText mInputEt;
    private ImageView mSubmitIv;
    private GridView mHotCityGv;

    private String[] hotCities = {"北京", "上海", "广州", "深圳", "西安", "郑州"};
    private ArrayAdapter<String> mAdapter;
    private String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_city);

        mInputEt = findViewById(R.id.search_input_et);
        mSubmitIv = findViewById(R.id.search_submit_iv);
        mHotCityGv = findViewById(R.id.search_hot_city_gv);

        mSubmitIv.setOnClickListener(this);

        mAdapter = new ArrayAdapter<>(this, R.layout.item_gridview_search_city, hotCities);
        mHotCityGv.setAdapter(mAdapter);
        mHotCityGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                city = hotCities[i];
                String url = URLHelper.getWeatherUrl(city);
                Log.i(TAG, "onClick: " + url);
                loadData(url);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_submit_iv:
                city = mInputEt.getText().toString();
                if (!TextUtils.isEmpty(city)) {
                    String url = URLHelper.getWeatherUrl(city);
                    Log.i(TAG, "onClick: " + url);
                    loadData(url);
                } else {
                    Toast.makeText(this, "请输入城市", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onSuccess(String result) {
        WeatherInfoBean weatherInfoBean = new Gson().fromJson(result, WeatherInfoBean.class);
        if (weatherInfoBean.getError() == 0) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("city", city);
            startActivity(intent);
        } else {
            Toast.makeText(this, "无法获取城市信息", Toast.LENGTH_SHORT).show();
        }
    }
}