package com.demo.mvvm.ui.main;

import android.util.Log;
import android.view.View;

import com.demo.mvvm.R;
import com.gonzalez.mvvm.base.BaseActivity;
import com.demo.mvvm.bean.BannerBean;
import com.demo.mvvm.databinding.ActivityMainBinding;
import com.demo.mvvm.glide.GlideImageLoader;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.Observable;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends BaseActivity<MainViewModel, ActivityMainBinding> {
    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void processLogic() {
        mDataBinding.setViewModel(mViewModel);
        mViewModel.title.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                Log.d("gxd", "onPropertyChanged-->" + propertyId);
            }
        });
        mDataBinding.bannerView.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        mDataBinding.bannerView.setImageLoader(new GlideImageLoader());
        mDataBinding.bannerView.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mViewModel.update(position);
            }
        });
        mDataBinding.requestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.getBannerList().observe(MainActivity.this, new Observer<List<BannerBean>>() {
                    @Override
                    public void onChanged(List<BannerBean> bannerBeanList) {
                        updateBanner(bannerBeanList);
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

            mDataBinding.bannerView.setBannerTitles(titleList);
            mDataBinding.bannerView.setImages(imageUrlList);

            mDataBinding.bannerView.start();
        }
    }
}
