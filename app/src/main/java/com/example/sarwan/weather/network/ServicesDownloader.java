package com.example.sarwan.weather.network;

import com.example.sarwan.weather.model.WeatherData;
import com.example.sarwan.weather.model.WeatherDetailedData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServicesDownloader {

    @GET("data/2.5/weather")
    Call<WeatherData> getWeatherData(
            @Query("lat") Double lat,
            @Query("lon") Double lon,
            @Query("appid") String appid
    );

    @GET("data/2.5/forecast")
    Call<WeatherDetailedData> getdetailedWeatherData(
            @Query("lat") Double lat,
            @Query("lon") Double lon,
            @Query("appid") String appid
    );
}
