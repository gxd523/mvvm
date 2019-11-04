package com.gonzalez.mvvm.retrofit;

public class RetrofitParams {
    /**
     * 在线缓存时间，不设置就是不用
     */
    private int onlineCacheTime;
    /**
     * 离线缓存时间，不设置就是不用
     */
    private int offlineCacheTime;
    /**
     * 离开页面的时候 是否取消网络。( 默认是取消 )
     */
    private boolean cancleNet;
    /**
     * 重连次数，默认为0 不重连。大于0 开启重连
     */
    private int retryCount;
    /**
     * 同一网络还在加载时，有且只能请求一次(默认可以请求多次)
     * 同一网络，oneTag只能用一次
     */
    private String oneTag;
    /**
     * 是否显示加载loading （默认显示）
     */
    private boolean isShowDialog;
    /**
     * 加载进度条上是否显示文字（默认不显示文字）
     */
    private String loadingMessage;

    private RetrofitParams(Builder builder) {
        this.onlineCacheTime = builder.onlineCacheTime;
    }

    public int getOnlineCacheTime() {
        return onlineCacheTime;
    }

    public int getOfflineCacheTime() {
        return offlineCacheTime;
    }

    public boolean isCancleNet() {
        return cancleNet;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public String getOneTag() {
        return oneTag;
    }

    public boolean isShowDialog() {
        return isShowDialog;
    }

    public String getLoadingMessage() {
        return loadingMessage;
    }

    public static class Builder {
        private int onlineCacheTime;
        private int offlineCacheTime;
        private boolean cancleNet;
        private int retryCount;
        private String oneTag;
        private boolean isShowDialog;
        private String loadingMessage;

        public Builder() {
            cancleNet = true;
            isShowDialog = true;
        }

        public Builder setOnlineCacheTime(int onlineCacheTime) {
            this.onlineCacheTime = onlineCacheTime;
            return this;
        }

        public Builder setOfflineCacheTime(int offlineCacheTime) {
            this.offlineCacheTime = offlineCacheTime;
            return this;
        }

        public Builder setCancleNet(boolean cancleNet) {
            this.cancleNet = cancleNet;
            return this;
        }

        public Builder setRetryCount(int retryCount) {
            this.retryCount = retryCount;
            return this;
        }

        public Builder setOneTag(String oneTag) {
            this.oneTag = oneTag;
            return this;
        }

        public Builder setShowDialog(boolean showDialog) {
            isShowDialog = showDialog;
            return this;
        }

        public Builder setLoadingMessage(String loadingMessage) {
            this.loadingMessage = loadingMessage;
            return this;
        }

        public RetrofitParams build() {
            return new RetrofitParams(this);
        }
    }
}
