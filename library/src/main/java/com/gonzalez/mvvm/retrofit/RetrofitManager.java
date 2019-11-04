package com.gonzalez.mvvm.retrofit;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager<T> {
    private RetrofitConfig retrofitConfig;
    private OkHttpClient okHttpClient;
    private T apiService;

    public RetrofitManager(Class<T> tClass, RetrofitConfig retrofitConfig) {
        this.retrofitConfig = retrofitConfig;
        initOkHttpClient();
        initRetrofit(tClass);
    }

    public T getApiService() {
        return apiService;
    }

    private void initRetrofit(Class<T> tClass) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(retrofitConfig.getHost())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        apiService = retrofit.create(tClass);
    }

    private void initOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .cache(new Cache(new File(retrofitConfig.getCachePath()), retrofitConfig.getMaxCacheSize()))
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addNetworkInterceptor(retrofitConfig.getNetworkInterceptor());

        for (Interceptor interceptor : retrofitConfig.getInterceptors()) {
            builder.addInterceptor(interceptor);
        }
        okHttpClient = builder.build();
    }
}
