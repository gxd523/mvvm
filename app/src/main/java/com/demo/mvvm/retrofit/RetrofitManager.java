package com.demo.mvvm.retrofit;

import com.demo.mvvm.constant.SystemConst;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    private RetrofitApiService retrofitApiService;

    private RetrofitManager() {
        initRetrofit();
    }

    public static RetrofitManager getInstance() {
        return Holder.instance;
    }

    public RetrofitApiService getApiService() {
        return Holder.instance.retrofitApiService;
    }

    private void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SystemConst.DEFAULT_SERVER)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        retrofitApiService = retrofit.create(RetrofitApiService.class);
    }

    private static class Holder {
        private static RetrofitManager instance = new RetrofitManager();
    }
}