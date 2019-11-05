package com.demo.mvvm.ui.main;

import com.demo.mvvm.base.MyBaseRepository;
import com.demo.mvvm.bean.BannerBean;
import com.gonzalez.mvvm.bean.ResponModel;

import java.util.List;

import io.reactivex.Observable;

/**
 * 相当于Interactor
 */
public class MainRepository extends MyBaseRepository {
    public Observable<List<BannerBean>> getBannerList() {
        return getApiService().getBanner().map(ResponModel::getData);
    }
}
