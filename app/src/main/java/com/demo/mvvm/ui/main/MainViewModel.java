package com.demo.mvvm.ui.main;

import android.app.Application;

import com.demo.mvvm.base.BaseViewModel;
import com.demo.mvvm.bean.BannerBean;
import com.demo.mvvm.bean.base.ResponModel;
import com.demo.mvvm.retrofit.RetrofitManager;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by guoxiaodong on 2019-11-02 13:47
 */
public class MainViewModel extends BaseViewModel {
    public final ObservableField<String> title = new ObservableField<>();
    private List<BannerBean> bannerBeanList;

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<BannerBean>> getBannerList() {
        MutableLiveData<List<BannerBean>> bannerList = new MutableLiveData<>();
        RetrofitManager.getInstance().getApiService().getBanner()
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<ResponModel<List<BannerBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(ResponModel<List<BannerBean>> listResponModel) {
                        bannerBeanList = listResponModel.data;
                        bannerList.postValue(listResponModel.data);
                        if (bannerBeanList != null && bannerBeanList.size() > 0) {
                            title.set(bannerBeanList.get(0).title);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
        return bannerList;
    }

    public void update(int position) {
        if (bannerBeanList != null && bannerBeanList.size() > position) {
            title.set(bannerBeanList.get(position).title);
        }
    }
}
