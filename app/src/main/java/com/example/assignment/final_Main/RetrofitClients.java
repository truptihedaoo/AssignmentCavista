package com.example.assignment.final_Main;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class RetrofitClients {


    private static RetrofitClients instance = null;
    private Api myApi;

    private RetrofitClients() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.imgur.com/3/gallery/search/1?q=")
                .addConverterFactory(GsonConverterFactory.create())

//        .addRequestProperty("Authorization","Client-ID 137cda6b5008a7c")

                .build();
        myApi = retrofit.create(Api.class);
    }

    public static synchronized RetrofitClients getInstance() {
        if (instance == null) {
            instance = new RetrofitClients();
        }
        return instance;
    }

    public Api getMyApi() {
        return myApi;

    }
}
