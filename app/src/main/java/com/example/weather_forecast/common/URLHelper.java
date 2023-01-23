package com.example.weather_forecast.common;

/**
 * @PackageName: com.example.weather_forecast.utils
 * @ClassName: URLHelper
 * @Author: winwa
 * @Date: 2023/1/18 21:21
 * @Description:
 **/
public class URLHelper {
    public static String getWeatherUrl(String cityName) {
        return "https://www.fastmock.site/mock/80cdea24781c3dec709e535376570531/testapi/weather?city=" + cityName;
    }
}
