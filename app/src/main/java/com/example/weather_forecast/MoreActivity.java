package com.example.weather_forecast;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.weather_forecast.db.DBManager;

public class MoreActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mBackIv;
    private TextView mSetBgTv;
    private RadioGroup mSelectBgRg;
    private TextView mVersionTv;
    private TextView mClearCacheTv;
    private TextView mShareTv;

    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        mBackIv = findViewById(R.id.more_top_back_iv);
        mSetBgTv = findViewById(R.id.more_set_wall_paper_tv);
        mSelectBgRg = findViewById(R.id.more_select_wall_paper_rg);
        mVersionTv = findViewById(R.id.more_version_tv);
        mClearCacheTv = findViewById(R.id.more_clear_cache_tv);
        mShareTv = findViewById(R.id.more_share_tv);

        mBackIv.setOnClickListener(this);
        mSetBgTv.setOnClickListener(this);
        mClearCacheTv.setOnClickListener(this);
        mShareTv.setOnClickListener(this);

        String versionName = getVersionName();
        mVersionTv.setText("当前版本：" + versionName);

        mPreferences = getSharedPreferences("bg", MODE_PRIVATE);
        mSelectBgRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int currentBg = mPreferences.getInt("bg", 0);

                int selectBg = 0;
                switch (i) {
                    case R.id.more_select_wall_paper_green_rb:
                        selectBg = 0;
                        break;
                    case R.id.more_select_wall_paper_ping_rb:
                        selectBg = 1;
                        break;
                    case R.id.more_select_wall_paper_blue_rb:
                        selectBg = 2;
                        break;
                }

                SharedPreferences.Editor editor = mPreferences.edit();
                editor.putInt("bg", selectBg);
                editor.apply();

                if (currentBg == selectBg) {
                    Toast.makeText(MoreActivity.this, "无需改变", Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(MoreActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private String getVersionName() {
        PackageManager packageManager = getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo.versionName;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.more_top_back_iv:
                finish();
                break;
            case R.id.more_set_wall_paper_tv:
                if (mSelectBgRg.getVisibility() == View.VISIBLE) {
                    mSelectBgRg.setVisibility(View.GONE);
                } else {
                    mSelectBgRg.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.more_clear_cache_tv:
                clearCache();
                break;
            case R.id.more_share_tv:
                share("快来下载吧！");
                break;
        }
    }

    private void share(String s) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, s);
        startActivity(Intent.createChooser(intent, "说天气"));
    }

    private void clearCache() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MoreActivity.this);
        builder.setTitle("提示信息")
                .setMessage("确定清楚缓存吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DBManager.deleteAll();
                        Toast.makeText(MoreActivity.this, "已清除全部缓存", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MoreActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("取消", null)
                .create().show();
    }
}