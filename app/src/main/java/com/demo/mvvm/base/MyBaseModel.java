package com.demo.mvvm.base;

import com.demo.mvvm.interceptor.NetCacheInterceptor;
import com.demo.mvvm.interceptor.OfflineCacheInterceptor;
import com.demo.mvvm.retrofit.RetrofitApiService;
import com.demo.mvvm.retrofit.RetrofitSingleton;
import com.gonzalez.mvvm.base.BaseModel;

/**
 * Created by guoxiaodong on 2019-11-04 19:33
 */
public class MyBaseModel extends BaseModel<RetrofitApiService> {
    @Override
    public RetrofitApiService getApiService() {
        return RetrofitSingleton.getInstance().getApiService();
    }

    @Override
    public void setOnlineCacheTime(int time) {
        NetCacheInterceptor.getInstance().setOnlineTime(time);
    }

    @Override
    public void setOfflineCacheTime(int time) {
        OfflineCacheInterceptor.getInstance().setOfflineCacheTime(time);
    }
}
