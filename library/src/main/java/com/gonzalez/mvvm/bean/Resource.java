package com.gonzalez.mvvm.bean;

/**
 * 这个用来拓展LiveData
 */
public class Resource {
    /**
     * 表示加载中
     */
    public static final int LOADING = 0;
    /**
     * 表示成功
     */
    public static final int SUCCESS = 1;
    /**
     * 表示联网失败
     */
    public static final int ERROR = 2;
    /**
     * 表示接口虽然走通，但走的失败（如：关注失败）
     */
    public static final int FAIL = 3;
    /**
     * 注意只有下载文件和上传图片时才会有
     */
    public static final int PROGRESS = 4;
    public int state;

    public String errorMsg;
    public Throwable error;

    //这里和文件和进度有关了
    public int precent;//文件下载百分比
    public long total;//文件总大小

    public Resource(int state, String errorMsg) {
        this.state = state;
        this.errorMsg = errorMsg;
    }

    public Resource(int state, Throwable error) {
        this.state = state;
        this.error = error;
    }

    public Resource(int state, int precent, long total) {
        this.state = state;
        this.precent = precent;
        this.total = total;
    }


    public static Resource loading(String showMsg) {
        return new Resource(LOADING, showMsg);
    }

    public static Resource failure(String msg) {
        return new Resource(ERROR, msg);
    }

    public static Resource error(Throwable t) {
        return new Resource(ERROR, t);
    }

    public static Resource progress(int precent, long total) {
        return new Resource(PROGRESS, precent, total);
    }

    public void handler(OnHandleCallback callback) {
        switch (state) {
            case LOADING:
                callback.onLoading(errorMsg);
                break;
            case SUCCESS:
                break;
            case FAIL:
                callback.onFailure(errorMsg);
                break;
            case ERROR:
                callback.onError(error);
                break;
            case PROGRESS:
                callback.onProgress(precent, total);
                break;
        }

        if (state != LOADING) {
            callback.onCompleted();
        }
    }

    public interface OnHandleCallback {
        void onLoading(String showMessage);

        void onFailure(String msg);

        void onError(Throwable error);

        void onCompleted();

        void onProgress(int precent, long total);
    }
}
