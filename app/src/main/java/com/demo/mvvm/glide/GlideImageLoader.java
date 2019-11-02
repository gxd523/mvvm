package com.demo.mvvm.glide;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.demo.mvvm.R;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by leo
 * on 2019/10/16.
 */
public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context)
                .load(path)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .centerCrop()
                .into(imageView);
    }
}
