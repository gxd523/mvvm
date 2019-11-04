package com.demo.mvvm.retrofit;

import com.demo.mvvm.interceptor.HttpLogInterceptor;
import com.demo.mvvm.interceptor.NetCacheInterceptor;
import com.demo.mvvm.interceptor.OfflineCacheInterceptor;
import com.gonzalez.mvvm.retrofit.RetrofitConfig;
import com.gonzalez.mvvm.retrofit.RetrofitManager;

/**
 * Created by guoxiaodong on 2019-11-04 17:24
 */
public class RetrofitSingleton {
    private RetrofitManager<RetrofitApiService> retrofitManager;

    private RetrofitSingleton() {
        retrofitManager = new RetrofitManager<>(
                RetrofitApiService.class,
                new RetrofitConfig
                        .Builder()
                        .setHost("https://www.wanandroid.com/")
                        .setInterceptors(new HttpLogInterceptor(), OfflineCacheInterceptor.getInstance())
                        .setNetworkInterceptor(NetCacheInterceptor.getInstance())
                        .build()
        );
    }

    public static RetrofitSingleton getInstance() {
        return Holder.instance;
    }

    public RetrofitApiService getApiService() {
        return retrofitManager.getApiService();
    }

    private static class Holder {
        private static RetrofitSingleton instance = new RetrofitSingleton();
    }
}
