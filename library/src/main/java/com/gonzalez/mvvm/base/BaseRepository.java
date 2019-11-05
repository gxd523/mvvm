package com.gonzalez.mvvm.base;

/**
 * 相当于Interactor
 */
public abstract class BaseRepository<T> {
    public abstract T getApiService();

    //设置在线网络缓存
    public abstract void setOnlineCacheTime(int time);

    //设置离线网络缓存
    public abstract void setOfflineCacheTime(int time);
}
