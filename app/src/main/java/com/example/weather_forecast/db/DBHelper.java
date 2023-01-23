package com.example.weather_forecast.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * @PackageName: com.example.weather_forecast.db
 * @ClassName: DBHelper
 * @Author: winwa
 * @Date: 2023/1/22 8:04
 * @Description:
 **/
public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context) {
        super(context, "forecast.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table city (" +
                "_id integer primary key autoincrement, " +
                "city varchar(20) unique not null, " +
                "content text not null)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
