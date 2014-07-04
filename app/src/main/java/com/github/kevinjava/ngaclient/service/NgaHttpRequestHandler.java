package com.github.kevinjava.ngaclient.service;

import com.github.kevinjava.ngaclient.listener.IRequestFinished;
import com.github.kevinjava.ngaclient.model.HttpRequestBean;
import com.loopj.android.http.AsyncHttpResponseHandler;

/**
 * Created by kevliu on 6/24/14.
 */
public class NgaHttpRequestHandler extends AsyncHttpResponseHandler {
    IRequestFinished requestFinished;
    HttpRequestBean bean;
   public NgaHttpRequestHandler(HttpRequestBean bean, IRequestFinished requestHandler){
        this.requestFinished = requestHandler;
        this.bean = bean;
   }

    @Override
    public void onSuccess(String content) {
        super.onSuccess(content);
        requestFinished.onRequestFinished(bean, content);
    }

    @Override
    public void onFailure(Throwable error, String content) {
        super.onFailure(error, content);
        requestFinished.onError(bean, error);
    }
}
