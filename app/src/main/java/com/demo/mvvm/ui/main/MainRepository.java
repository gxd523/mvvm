package com.demo.mvvm.ui.main;

import com.demo.mvvm.base.MyBaseModel;
import com.demo.mvvm.bean.BannerBean;
import com.gonzalez.mvvm.bean.Resource;

import java.util.List;

import androidx.lifecycle.MutableLiveData;

/**
 * Created by leo
 * on 2019/10/15.
 */
public class MainRepository extends MyBaseModel {
    public MutableLiveData<Resource<List<BannerBean>>> getBannerList() {
        MutableLiveData<Resource<List<BannerBean>>> liveData = new MutableLiveData<>();
        return observeGo(getApiService().getBanner(), liveData);
    }
}
