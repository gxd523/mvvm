package com.demo.mvvm.bean.base;

import java.io.Serializable;

public class ResponModel<T> implements Serializable {
    public static final int RESULT_SUCCESS = 0;
    public T data;
    public int errorCode;
    public String errorMsg;

    public boolean isSuccess() {
        return RESULT_SUCCESS == errorCode;
    }
}