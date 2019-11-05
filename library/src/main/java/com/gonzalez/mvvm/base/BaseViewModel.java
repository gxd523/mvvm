package com.gonzalez.mvvm.base;

import android.app.Application;

import com.gonzalez.mvvm.bean.Resource;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 相当于Presenter
 */
public abstract class BaseViewModel<T extends BaseRepository> extends AndroidViewModel {
    /**
     * 这个是为了退出页面，取消请求的
     */
    public CompositeDisposable compositeDisposable;
    protected LifecycleTransformer objectLifecycleTransformer;
    protected MutableLiveData<Resource> liveData;
    private T repository;
    private ArrayList<String> onNetTags;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        this.repository = createRepository();
        compositeDisposable = new CompositeDisposable();
        onNetTags = new ArrayList<>();
    }

    protected abstract T createRepository();

    public void setObjectLifecycleTransformer(LifecycleTransformer objectLifecycleTransformer) {
        this.objectLifecycleTransformer = objectLifecycleTransformer;
    }

    public void setLiveData(MutableLiveData<Resource> liveData) {
        this.liveData = liveData;
    }

    public T getRepository() {
        return repository;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (compositeDisposable != null) {// 销毁后，取消当前页所有在执行的网络请求
            compositeDisposable.dispose();
        }
    }

    public abstract class RxCompatObserver<D> implements Observer<D> {
        @Override
        public void onSubscribe(Disposable d) {
            compositeDisposable.add(d);
        }

        @Override
        public void onError(Throwable throwable) {
            liveData.postValue(Resource.error(throwable));
        }

        @Override
        public void onComplete() {
            liveData.postValue(Resource.complete());
        }
    }
}
