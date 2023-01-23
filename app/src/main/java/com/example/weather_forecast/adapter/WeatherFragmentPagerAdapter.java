package com.example.weather_forecast.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * @PackageName: com.example.weather_forecast.adapter
 * @ClassName: WeatherFragmentPagerAdapter
 * @Author: winwa
 * @Date: 2023/1/19 21:39
 * @Description:
 **/
public class WeatherFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private int mChildCount = 0;
    private List<Fragment> mFragmentList;

    public WeatherFragmentPagerAdapter(@NonNull FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.mFragmentList = fragmentList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public void notifyDataSetChanged() {
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        if (mChildCount > 0) {
            mChildCount--;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }
}
