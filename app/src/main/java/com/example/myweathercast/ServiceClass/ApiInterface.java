package com.example.myweathercast.ServiceClass;

import com.example.myweathercast.Model.MausamData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
  @GET("data/2.5/weather")

    Call<MausamData> getData
          (@Query("q")String q,
          @Query("appid") String ApiKey,
          @Query("units")String units
   );
}
