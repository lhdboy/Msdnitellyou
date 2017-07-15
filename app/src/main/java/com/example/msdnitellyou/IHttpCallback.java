package com.example.msdnitellyou;

/**
 * Created by Administrator on 2017/4/26.
 */

public interface IHttpCallback {
    <T> void onSuccess(T result);
}
