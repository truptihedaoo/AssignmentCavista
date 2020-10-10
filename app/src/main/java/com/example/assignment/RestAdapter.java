package com.example.assignment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestAdapter {
    public static RetrofitApiV2 createAPI() {

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .cache(null)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request newRequest  = chain.request().newBuilder()
                                .addHeader("Authorization", "Client-ID 137cda6b5008a7c")
                                .build();
                        return chain.proceed(newRequest);
                    }
                })
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                //https://api.imgur.com/3/gallery/search/1?q=vanilla
                .baseUrl("https://api.imgur.com/3/gallery/search/")
//                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
//                .addRequestProperty("Authorization", "Client-ID 137cda6b5008a7c")

                .client(okHttpClient)
                .build();

        return retrofit.create(RetrofitApiV2.class);


    }

//    public static RetrofitApiV2 createAPI2() {
//
//        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
//        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .connectTimeout(30, TimeUnit.SECONDS)
//                .writeTimeout(30, TimeUnit.SECONDS)
//                .readTimeout(30, TimeUnit.SECONDS)
//                .addInterceptor(httpLoggingInterceptor)
//                .cache(null)
//                .build();
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(MyNetworkConstant.AWS_BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(okHttpClient)
//                .build();
//
//        return retrofit.create(RetrofitApiV2.class);
//
//    }


}
