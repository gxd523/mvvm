package com.demo.mvvm.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.demo.mvvm.R;
import com.demo.mvvm.base.MyBaseActivity;
import com.demo.mvvm.databinding.ActivityMainBinding;
import com.demo.mvvm.glide.GlideImageLoader;
import com.youth.banner.BannerConfig;

import androidx.annotation.Nullable;
import androidx.databinding.Observable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

public class MainActivity extends MyBaseActivity<MainViewModel, ActivityMainBinding> implements View.OnClickListener {
    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel.title.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                Log.d("gxd", "onPropertyChanged-->" + propertyId);
            }
        });
        binding.bannerView.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        binding.bannerView.setImageLoader(new GlideImageLoader());
        binding.requestBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MutableLiveData<Object> liveData = new MutableLiveData<>();
        liveData.observe(this, new Observer<Object>() {
            @Override
            public void onChanged(Object o) {
                binding.bannerView.start();
            }
        });
        viewModel.getBannerList(
                imageUrlList -> binding.bannerView.setImages(imageUrlList),
                titleList -> binding.bannerView.setBannerTitles(titleList),
                liveData
        );
    }
}
