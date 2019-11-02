package com.demo.mvvm.retrofit;

import com.demo.mvvm.bean.BannerBean;
import com.demo.mvvm.bean.base.ResponModel;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

/**
 * Created by guoxiaodong on 2019-11-02 13:37
 */
public interface RetrofitApiService {
    @GET("banner/json")
    Single<ResponModel<List<BannerBean>>> getBanner();
}
