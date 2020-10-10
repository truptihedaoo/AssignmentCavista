package com.example.assignment;


import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface RetrofitApiV2 {

    @GET()
    Call<OrderWrapper> getCashTest(@Url String s);
}
