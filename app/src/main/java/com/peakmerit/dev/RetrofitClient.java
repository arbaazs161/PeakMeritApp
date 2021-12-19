package com.peakmerit.dev;

import com.peakmerit.dev.api.CourseApi;

import io.reactivex.plugins.RxJavaPlugins;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private String BASE_URL = "http://192.168.1.101:3000/";
    private static RetrofitClient retrofitClient;
    private static Retrofit retrofit;

    private RetrofitClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getInstance() {
        if(retrofitClient == null) {
            retrofitClient = new RetrofitClient();
        }
        return retrofitClient;
    }

    public CourseApi getApi(){
        return retrofit.create(CourseApi.class);
    }
}
