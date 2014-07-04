package com.github.kevinjava.ngaclient.listener;

import com.github.kevinjava.ngaclient.model.HttpRequestBean;

/**
 * Created by kevliu on 6/24/14.
 */
public interface IRequestFinished {

    void onRequestFinished(HttpRequestBean bean, String response);

    void onError(HttpRequestBean bean, Throwable error);
}
