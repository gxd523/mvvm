package com.gonzalez.mvvm.base;

import android.content.Context;
import android.os.Bundle;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxFragmentActivity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProviders;

public abstract class BaseActivity<VM extends BaseViewModel, VDB extends ViewDataBinding> extends RxFragmentActivity {
    protected VM viewModel;
    protected VDB binding;

    //获取当前activity布局文件
    protected abstract int getContentViewId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getContentViewId());
        binding.setLifecycleOwner(this);
        createViewModel();
    }

    public void createViewModel() {
        if (viewModel == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = BaseViewModel.class;
            }
            viewModel = (VM) ViewModelProviders.of(this).get(modelClass);
            viewModel.setObjectLifecycleTransformer(bindLifecycle());
        }
    }

    public LifecycleTransformer bindLifecycle() {
        return bindToLifecycle();
    }

    public Context getContext() {
        return this;
    }
}
