package com.demo.mvvm.base;

import android.text.TextUtils;

import com.demo.mvvm.R;
import com.demo.mvvm.view.CustomProgress;
import com.gonzalez.mvvm.base.BaseViewModel;
import com.gonzalez.mvvm.bean.Resource;
import com.gonzalez.mvvm.util.ToastUtils;
import com.gonzalez.mvvm.util.network.NetWorkUtils;
import com.google.gson.JsonSyntaxException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import androidx.databinding.ViewDataBinding;

/**
 * Created by guoxiaodong on 2019-11-04 19:49
 */
public abstract class MyBaseActivity<VM extends BaseViewModel, VDB extends ViewDataBinding> extends com.gonzalez.mvvm.base.BaseActivity<VM, VDB> {
    private CustomProgress dialog;

    public abstract class OnCallback<T> implements Resource.OnHandleCallback<T> {
        @Override
        public void onLoading(String msg) {
            if (dialog == null) {
                dialog = CustomProgress.show(MyBaseActivity.this, "", true, null);
            }

            if (!TextUtils.isEmpty(msg)) {
                dialog.setMessage(msg);
            }

            if (!dialog.isShowing()) {
                dialog.show();
            }
        }

        @Override
        public void onError(Throwable throwable) {
            if (!NetWorkUtils.isNetworkConnected(getContext())) {
                ToastUtils.showToast(getContext().getResources().getString(R.string.result_network_error));
                return;
            }

            if (throwable instanceof ConnectException) {
                ToastUtils.showToast(getContext().getResources().getString(R.string.result_server_error));
            } else if (throwable instanceof SocketTimeoutException) {
                ToastUtils.showToast(getContext().getResources().getString(R.string.result_server_timeout));
            } else if (throwable instanceof JsonSyntaxException) {
                ToastUtils.showToast("数据解析出错");
            } else {
                ToastUtils.showToast(getContext().getResources().getString(R.string.result_empty_error));
            }
        }

        @Override
        public void onFailure(String msg) {
            ToastUtils.showToast(msg);
        }

        @Override
        public void onCompleted() {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        }

        @Override
        public void onProgress(int precent, long total) {

        }
    }
}
