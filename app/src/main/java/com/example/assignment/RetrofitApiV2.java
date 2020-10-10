package com.example.assignment;


import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitApiV2 {


    @FormUrlEncoded
    @POST("")
    Call<JsonElement> getCuisineType(
            @Field("type") String type
    );


    @GET("url")
    Call<String> getCountries();

    @GET("1?q=vanilla")
    Call<JsonElement> getCash();
}
