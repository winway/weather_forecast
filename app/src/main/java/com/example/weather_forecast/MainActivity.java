package com.example.weather_forecast;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.weather_forecast.adapter.WeatherFragmentPagerAdapter;
import com.example.weather_forecast.db.DBManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout mMainRl;
    private ImageView mAddIv;
    private ImageView mMoreIv;
    private LinearLayout mIndicatorLl;
    private ViewPager mVp;

    private List<Fragment> mFragmentList;
    private List<String> mCityList;
    private List<ImageView> mIndicatorList;
    private WeatherFragmentPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainRl = findViewById(R.id.main_rl);
        mAddIv = findViewById(R.id.main_add_iv);
        mMoreIv = findViewById(R.id.main_more_iv);
        mIndicatorLl = findViewById(R.id.main_indicator_ll);
        mVp = findViewById(R.id.main_vp);

        setBg();

        mAddIv.setOnClickListener(this);
        mMoreIv.setOnClickListener(this);

        mFragmentList = new ArrayList<>();
        mCityList = DBManager.queryCities();
        mIndicatorList = new ArrayList<>();

        if (mCityList.size() == 0) {
            mCityList.add("西安");
        }

        Intent intent = getIntent();
        String city = intent.getStringExtra("city");
        if (!TextUtils.isEmpty(city) && !mCityList.contains(city)) {
            mCityList.add(city);
        }

        initFragmentList();

        mPagerAdapter = new WeatherFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList);
        mVp.setAdapter(mPagerAdapter);

        initIndicator();

        mVp.setCurrentItem(mFragmentList.size() - 1);
        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < mIndicatorList.size(); i++) {
                    if (i == position) {
                        mIndicatorList.get(i).setImageResource(R.mipmap.a2);
                    } else {
                        mIndicatorList.get(i).setImageResource(R.mipmap.a1);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setBg() {
        SharedPreferences preferences = getSharedPreferences("bg", MODE_PRIVATE);
        int id = preferences.getInt("bg", 0);
        if (id == 0) {
            mMainRl.setBackgroundResource(R.mipmap.bg);
        } else if (id == 1) {
            mMainRl.setBackgroundResource(R.mipmap.bg2);
        } else if (id == 2) {
            mMainRl.setBackgroundResource(R.mipmap.bg3);
        }
    }

    private void initIndicator() {
        mIndicatorList.clear();
        mIndicatorLl.removeAllViews();
        for (int i = 0; i < mFragmentList.size(); i++) {
            ImageView view = new ImageView(this);
            view.setImageResource(R.mipmap.a1);
            view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
            params.setMargins(0, 0, 20, 0);
            mIndicatorList.add(view);
            mIndicatorLl.addView(view);
        }

        mIndicatorList.get(mIndicatorList.size() - 1).setImageResource(R.mipmap.a2);
    }

    private void initFragmentList() {
        mFragmentList.clear();
        for (int i = 0; i < mCityList.size(); i++) {
            WeatherFragment fragment = WeatherFragment.newInstance(mCityList.get(i));
            mFragmentList.add(fragment);
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();

        switch (view.getId()) {
            case R.id.main_add_iv:
                intent.setClass(this, CityManageActivity.class);
                break;
            case R.id.main_more_iv:
                intent.setClass(this, MoreActivity.class);
                break;
        }

        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        List<String> cities = DBManager.queryCities();
        if (cities.size() == 0) {
            cities.add("北京");
        }
        mCityList.clear();
        mCityList.addAll(cities);

        initFragmentList();
        mPagerAdapter.notifyDataSetChanged();

        initIndicator();

        mVp.setCurrentItem(mFragmentList.size() - 1);
    }
}