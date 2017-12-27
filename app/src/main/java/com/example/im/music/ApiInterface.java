package com.example.im.music;


import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Im on 21-11-2017.
 */

public interface ApiInterface {
    String BASE_URL = "http://192.168.16.236:3000/";
    @Headers("Content-Type: application/json")
    @POST("add")   //to fetch data from url
     Call<JsonObject> addToPlaylist(@Body Songinfo body);

    @Headers("Content-Type: application/json")
    @POST("delete")
    Call<JsonObject> delete(@Body Songinfo body);

    @GET("songs")   //to fetch data from url
    Call<List<Songinfo>> getDetails();

}

