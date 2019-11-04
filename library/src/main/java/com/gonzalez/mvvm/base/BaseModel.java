package com.gonzalez.mvvm.base;

import android.text.TextUtils;

import com.gonzalez.mvvm.bean.Resource;
import com.gonzalez.mvvm.bean.ResponModel;
import com.gonzalez.mvvm.retrofit.RetrofitParams;
import com.gonzalez.mvvm.util.LogUtils;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public abstract class BaseModel<T> {
    public LifecycleTransformer objectLifecycleTransformer;
    public CompositeDisposable compositeDisposable;
    public ArrayList<String> onNetTags;


    public abstract T getApiService();

    public void setObjectLifecycleTransformer(LifecycleTransformer objectLifecycleTransformer) {
        this.objectLifecycleTransformer = objectLifecycleTransformer;
    }

    public void setCompositeDisposable(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
    }

    public void setOnNetTags(ArrayList<String> onNetTags) {
        this.onNetTags = onNetTags;
    }

    public <T> MutableLiveData<T> observeGo(Observable observable, final MutableLiveData<T> liveData) {
        return observe(observable, liveData, null);
    }

    public <T> MutableLiveData<T> observeGo(Observable observable, final MutableLiveData<T> liveData, RetrofitParams paramsBuilder) {
        int retryCount = paramsBuilder.getRetryCount();
        if (retryCount > 0) {
            return observeWithRetry(observable, liveData, paramsBuilder);
        } else {
            return observe(observable, liveData, paramsBuilder);
        }
    }


    //把统一操作全部放在这，不会重连
    public <T> MutableLiveData<T> observe(Observable observable, final MutableLiveData<T> liveData, RetrofitParams paramsBuilder) {
        if (paramsBuilder == null) {
            paramsBuilder = new RetrofitParams.Builder().build();
        }
        boolean showDialog = paramsBuilder.isShowDialog();
        String loadingMessage = paramsBuilder.getLoadingMessage();
        int onlineCacheTime = paramsBuilder.getOnlineCacheTime();
        int offlineCacheTime = paramsBuilder.getOfflineCacheTime();
        boolean cancleNet = paramsBuilder.isCancleNet();

        if (onlineCacheTime > 0) {
            setOnlineCacheTime(onlineCacheTime);
        }
        if (offlineCacheTime > 0) {
            setOfflineCacheTime(offlineCacheTime);
        }
        String oneTag = paramsBuilder.getOneTag();
        if (!TextUtils.isEmpty(oneTag)) {
            if (onNetTags.contains(oneTag)) {
                return liveData;
            }
        }

        Disposable disposable = observable.subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        if (!TextUtils.isEmpty(oneTag)) {
                            onNetTags.add(oneTag);
                        }
                        if (showDialog) {
                            liveData.postValue((T) Resource.loading(loadingMessage));
                        }
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                //防止RxJava内存泄漏
                .compose(objectLifecycleTransformer)
                .subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        liveData.postValue((T) Resource.response((ResponModel<Object>) o));
                        if (!TextUtils.isEmpty(oneTag)) {
                            onNetTags.remove(oneTag);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        liveData.postValue((T) Resource.error(throwable));
                        if (!TextUtils.isEmpty(oneTag)) {
                            onNetTags.remove(oneTag);
                        }
                    }
                });


        if (cancleNet) {
            compositeDisposable.add(disposable);
        }
        return liveData;
    }

    //把统一操作全部放在这，这是带重连的
    public <T> MutableLiveData<T> observeWithRetry(Observable observable, final MutableLiveData<T> liveData, RetrofitParams paramsBuilder) {
        if (paramsBuilder == null) {
            paramsBuilder = new RetrofitParams.Builder().build();
        }
        boolean showDialog = paramsBuilder.isShowDialog();
        String loadingMessage = paramsBuilder.getLoadingMessage();
        int onlineCacheTime = paramsBuilder.getOnlineCacheTime();
        int offlineCacheTime = paramsBuilder.getOfflineCacheTime();
        boolean cancleNet = paramsBuilder.isCancleNet();

        if (onlineCacheTime > 0) {
            setOnlineCacheTime(onlineCacheTime);
        }
        if (offlineCacheTime > 0) {
            setOfflineCacheTime(offlineCacheTime);
        }

        String oneTag = paramsBuilder.getOneTag();
        if (!TextUtils.isEmpty(oneTag)) {
            if (onNetTags.contains(oneTag)) {
                return liveData;
            }
        }

        final int maxCount = paramsBuilder.getRetryCount();
        final int[] currentCount = {0};

        Disposable disposable = observable.subscribeOn(Schedulers.io())
                .retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
                        return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(Throwable throwable) throws Exception {

                                //如果还没到次数，就延迟5秒发起重连
                                LogUtils.i("我看看是不是在重连", "当前的重连次数 == " + currentCount[0]);
                                if (currentCount[0] <= maxCount) {
                                    currentCount[0]++;
                                    return Observable.just(1).delay(5000, TimeUnit.MILLISECONDS);
                                } else {
                                    //到次数了跑出异常
                                    return Observable.error(new Throwable("重连次数已达最高,请求超时"));
                                }
                            }
                        });
                    }
                })
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        if (!TextUtils.isEmpty(oneTag)) {
                            onNetTags.add(oneTag);
                        }
                        if (showDialog) {
                            liveData.postValue((T) Resource.loading(loadingMessage));
                        }
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                //防止RxJava内存泄漏
                .compose(objectLifecycleTransformer)
                .subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        liveData.postValue((T) Resource.response((ResponModel<Object>) o));
                        if (!TextUtils.isEmpty(oneTag)) {
                            onNetTags.remove(oneTag);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        liveData.postValue((T) Resource.error(throwable));
                        if (!TextUtils.isEmpty(oneTag)) {
                            onNetTags.remove(oneTag);
                        }
                    }
                });


        if (cancleNet) {
            compositeDisposable.add(disposable);
        }
        return liveData;
    }

    //设置在线网络缓存
    public abstract void setOnlineCacheTime(int time);

    //设置离线网络缓存
    public abstract void setOfflineCacheTime(int time);
}
