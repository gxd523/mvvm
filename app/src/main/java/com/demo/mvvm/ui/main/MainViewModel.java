package com.demo.mvvm.ui.main;

import android.app.Application;

import com.demo.mvvm.bean.BannerBean;
import com.gonzalez.mvvm.base.BaseViewModel;
import com.gonzalez.mvvm.bean.Resource;
import com.gonzalez.mvvm.util.func.XFunc0;
import com.gonzalez.mvvm.util.func.XFunc1;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by guoxiaodong on 2019-11-02 13:47
 */
public class MainViewModel extends BaseViewModel<MainRepository> {
    public final ObservableField<String> title = new ObservableField<>();
    private List<String> imageUrlList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected MainRepository createRepository() {
        return new MainRepository();
    }

    public void getBannerList(XFunc1<List<String>> imageUrlListCallback, XFunc1<List<String>> titleListCallback, XFunc0 xFunc0) {
        getRepository()
                .getBannerList()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        imageUrlList.clear();
                        titleList.clear();
                        liveData.postValue(Resource.loading("loading...."));
                    }
                })
                .observeOn(Schedulers.newThread())
                .filter(bannerBeans -> bannerBeans.size() > 0)
                .flatMap((Function<List<BannerBean>, ObservableSource<BannerBean>>) Observable::fromIterable)
                .doOnNext(bannerBean -> {
                    imageUrlList.add(bannerBean.imagePath);
                    titleList.add(bannerBean.title);
                })
                .toList()
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxCompatObserver<List<BannerBean>>() {
                    @Override
                    public void onNext(List<BannerBean> bannerBeans) {
                        imageUrlListCallback.call(imageUrlList);
                        titleListCallback.call(titleList);
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        xFunc0.call();
                    }
                });
    }
}
