package com.demo.mvvm.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.demo.mvvm.R;
import com.demo.mvvm.base.MyBaseActivity;
import com.demo.mvvm.bean.BannerBean;
import com.demo.mvvm.databinding.ActivityMainBinding;
import com.demo.mvvm.glide.GlideImageLoader;
import com.gonzalez.mvvm.bean.Resource;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.databinding.Observable;
import androidx.lifecycle.Observer;

public class MainActivity extends MyBaseActivity<MainViewModel, ActivityMainBinding> {
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
        binding.requestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.getBannerList().observe(MainActivity.this, new Observer<Resource<List<BannerBean>>>() {
                    @Override
                    public void onChanged(Resource<List<BannerBean>> resource) {
                        resource.handler(new OnCallback<List<BannerBean>>() {
                            @Override
                            public void onSuccess(List<BannerBean> bannerBeanList) {
                                updateBanner(bannerBeanList);
                            }
                        });
                    }
                });
            }
        });
    }

    private void updateBanner(List<BannerBean> bannerBeanList) {
        if (bannerBeanList != null && bannerBeanList.size() > 0) {
            List<String> imageUrlList = new ArrayList<>();
            List<String> titleList = new ArrayList<>();

            for (BannerBean bannerBean : bannerBeanList) {
                imageUrlList.add(bannerBean.imagePath);
                titleList.add(bannerBean.title);
            }

            binding.bannerView.setBannerTitles(titleList);
            binding.bannerView.setImages(imageUrlList);

            binding.bannerView.start();
        }
    }
}
