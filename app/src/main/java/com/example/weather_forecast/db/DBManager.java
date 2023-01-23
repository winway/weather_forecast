package com.example.weather_forecast.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * @PackageName: com.example.weather_forecast.db
 * @ClassName: DBManager
 * @Author: winwa
 * @Date: 2023/1/22 8:11
 * @Description:
 **/
public class DBManager {
    public static SQLiteDatabase sDatabase;

    public static void initDB(Context context) {
        DBHelper helper = new DBHelper(context);
        sDatabase = helper.getWritableDatabase();
    }

    public static List<String> queryCities() {
        List<String> cityList = new ArrayList<>();

        Cursor cursor = sDatabase.query("city", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String city = cursor.getString(cursor.getColumnIndex("city"));
            cityList.add(city);
        }

        return cityList;
    }

    public static int updateContentByCity(String city, String content) {
        ContentValues values = new ContentValues();
        values.put("content", content);
        return sDatabase.update("city", values, "city=?", new String[]{city});
    }

    public static long addCity(String city, String content) {
        ContentValues values = new ContentValues();
        values.put("city", city);
        values.put("content", content);
        return sDatabase.insert("city", null, values);
    }

    public static String queryContentByCity(String city) {
        Cursor cursor = sDatabase.query("city", null, "city=?", new String[]{city}, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            return cursor.getString(cursor.getColumnIndex("content"));
        }

        return null;
    }

    public static int queryCityCount() {
        Cursor cursor = sDatabase.query("city", null, null, null, null, null, null);
        return cursor.getCount();
    }

    public static List<CityBean> queryCityInfo() {
        Cursor cursor = sDatabase.query("city", null, null, null, null, null, null);
        List<CityBean> cityBeanList = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String city = cursor.getString(cursor.getColumnIndex("city"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            cityBeanList.add(new CityBean(id, city, content));
        }

        return cityBeanList;
    }

    public static int deleteByCity(String city) {
        return sDatabase.delete("city", "city=?", new String[]{city});
    }

    public static void deleteAll() {
        sDatabase.execSQL("delete from city");
    }
}
