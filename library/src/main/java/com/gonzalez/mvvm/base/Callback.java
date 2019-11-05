package com.gonzalez.mvvm.base;

/**
 * Created by guoxiaodong on 2019-11-05 14:04
 */
public interface Callback {
    void onLoading(String showMessage);

    void onFailure(String msg);

    void onError(Throwable error);

    void onCompleted();

    void onProgress(int precent, long total);
}
