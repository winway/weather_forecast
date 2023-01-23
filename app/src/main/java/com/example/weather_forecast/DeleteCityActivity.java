package com.example.weather_forecast;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.weather_forecast.adapter.DeleteCityListAdapter;
import com.example.weather_forecast.db.DBManager;

import java.util.ArrayList;
import java.util.List;

public class DeleteCityActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "DeleteCityActivity";

    private ImageView mCancelIv;
    private ImageView mCompleteIv;
    private ListView mLv;

    private List<String> mData;
    private List<String> mDeletedCities;
    private DeleteCityListAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_city);

        mCancelIv = findViewById(R.id.delete_cancel_iv);
        mCompleteIv = findViewById(R.id.delete_complete_iv);
        mLv = findViewById(R.id.delete_lv);

        mData = new ArrayList<>();
        mDeletedCities = new ArrayList<>();
        mListAdapter = new DeleteCityListAdapter(this, mData, mDeletedCities);
        mLv.setAdapter(mListAdapter);

        mCancelIv.setOnClickListener(this);
        mCompleteIv.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<String> cities = DBManager.queryCities();
        mData.addAll(cities);
        mListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.delete_cancel_iv:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("提示信息")
                        .setMessage("确定舍弃更改吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .setNegativeButton("取消", null);
                builder.create().show();
                break;
            case R.id.delete_complete_iv:
                for (int i = 0; i < mDeletedCities.size(); i++) {
                    DBManager.deleteByCity(mDeletedCities.get(i));
                }
                finish();
                break;
        }
    }
}