package com.example.weather_forecast.db;

/**
 * @PackageName: com.example.weather_forecast.db
 * @ClassName: CityBean
 * @Author: winwa
 * @Date: 2023/1/22 8:02
 * @Description:
 **/
public class CityBean {
    private int _id;
    private String city;
    private String content;

    public CityBean() {
    }

    public CityBean(int _id, String city, String content) {
        this._id = _id;
        this.city = city;
        this.content = content;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
