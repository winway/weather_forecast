package com.example.weather_forecast;

import static com.example.weather_forecast.db.DBManager.queryCityCount;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.weather_forecast.adapter.CityManageListAdapter;
import com.example.weather_forecast.db.CityBean;
import com.example.weather_forecast.db.DBManager;

import java.util.ArrayList;
import java.util.List;

public class CityManageActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mAddIv;
    private ImageView mBackIv;
    private ImageView mModifyIv;
    private ListView mCityLv;
    private List<CityBean> mData;
    private CityManageListAdapter mCityManageListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_manage);

        mAddIv = findViewById(R.id.city_manage_add_iv);
        mBackIv = findViewById(R.id.city_manage_top_back_iv);
        mModifyIv = findViewById(R.id.city_manage_top_modify_iv);
        mCityLv = findViewById(R.id.city_manage_lv);

        mData = new ArrayList<>();
        mCityManageListAdapter = new CityManageListAdapter(this, mData);
        mCityLv.setAdapter(mCityManageListAdapter);

        mAddIv.setOnClickListener(this);
        mBackIv.setOnClickListener(this);
        mModifyIv.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<CityBean> cityBeanList = DBManager.queryCityInfo();
        mData.clear();
        mData.addAll(cityBeanList);
        mCityManageListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;

        switch (view.getId()) {
            case R.id.city_manage_add_iv:
                int cityCount = queryCityCount();
                if (cityCount < 5) {
                    intent = new Intent(this, SearchCityActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "城市数量已达上限", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.city_manage_top_back_iv:
                finish();
                break;
            case R.id.city_manage_top_modify_iv:
                intent = new Intent(this, DeleteCityActivity.class);
                startActivity(intent);
                break;
        }
    }
}