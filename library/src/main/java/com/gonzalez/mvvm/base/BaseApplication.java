package com.gonzalez.mvvm.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by leo
 * on 2019/10/15.
 */
public class BaseApplication extends Application {
    private static Application context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}
