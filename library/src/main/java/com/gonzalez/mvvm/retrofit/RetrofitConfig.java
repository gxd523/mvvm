package com.gonzalez.mvvm.retrofit;

import android.os.Environment;

import okhttp3.Interceptor;

/**
 * Created by guoxiaodong on 2019-11-04 16:09
 */
public class RetrofitConfig {
    private String host;
    private long connectTimeout;
    private long readTimeout;
    private long writeTimeout;
    private long maxCacheSize;
    private String cachePath;
    private Interceptor[] interceptors;
    private Interceptor networkInterceptor;

    private RetrofitConfig(Builder builder) {
        this.host = builder.host;
        this.connectTimeout = builder.connectTimeout;
        this.readTimeout = builder.readTimeout;
        this.writeTimeout = builder.writeTimeout;
        this.maxCacheSize = builder.maxCacheSize;
        this.cachePath = builder.cachePath;
        this.interceptors = builder.interceptors;
        this.networkInterceptor = builder.networkInterceptor;
    }

    public String getHost() {
        return host;
    }

    public long getConnectTimeout() {
        return connectTimeout;
    }

    public long getReadTimeout() {
        return readTimeout;
    }

    public long getWriteTimeout() {
        return writeTimeout;
    }

    public long getMaxCacheSize() {
        return maxCacheSize;
    }

    public String getCachePath() {
        return cachePath;
    }

    public Interceptor[] getInterceptors() {
        return interceptors;
    }

    public Interceptor getNetworkInterceptor() {
        return networkInterceptor;
    }

    public static class Builder {
        private String host;
        private long connectTimeout;
        private long readTimeout;
        private long writeTimeout;
        private long maxCacheSize;
        private String cachePath;
        private Interceptor[] interceptors;
        private Interceptor networkInterceptor;

        public Builder() {
            host = "";
            connectTimeout = 10;
            readTimeout = 10;
            writeTimeout = 10;
            maxCacheSize = 50 * 1024 * 1024;
            cachePath = Environment.getExternalStorageDirectory() + "/okhttp_cache/";
            interceptors = new Interceptor[0];
            networkInterceptor = null;
        }

        public Builder setHost(String host) {
            this.host = host;
            return this;
        }

        public Builder setConnectTimeout(long connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        public Builder setReadTimeout(long readTimeout) {
            this.readTimeout = readTimeout;
            return this;
        }

        public Builder setWriteTimeout(long writeTimeout) {
            this.writeTimeout = writeTimeout;
            return this;
        }

        public Builder setMaxCacheSize(long maxCacheSize) {
            this.maxCacheSize = maxCacheSize;
            return this;
        }

        public Builder setCachePath(String cachePath) {
            this.cachePath = cachePath;
            return this;
        }

        public Builder setInterceptors(Interceptor... interceptors) {
            this.interceptors = interceptors;
            return this;
        }

        public Builder setNetworkInterceptor(Interceptor networkInterceptor) {
            this.networkInterceptor = networkInterceptor;
            return this;
        }

        public RetrofitConfig build() {
            return new RetrofitConfig(this);
        }
    }
}
