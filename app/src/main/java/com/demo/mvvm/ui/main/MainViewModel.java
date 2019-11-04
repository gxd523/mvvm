package com.demo.mvvm.ui.main;

import android.app.Application;

import com.demo.mvvm.bean.BannerBean;
import com.gonzalez.mvvm.base.BaseViewModel;
import com.gonzalez.mvvm.bean.Resource;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;

/**
 * Created by guoxiaodong on 2019-11-02 13:47
 */
public class MainViewModel extends BaseViewModel<MainRepository> {
    public final ObservableField<String> title = new ObservableField<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected MainRepository createRepository() {
        return new MainRepository();
    }

    public LiveData<Resource<List<BannerBean>>> getBannerList() {
        return getRepository().getBannerList();
    }
}
