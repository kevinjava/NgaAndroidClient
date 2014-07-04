package com.github.kevinjava.ngaclient.service.tasks;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.github.kevinjava.ngaclient.model.HttpRequestBean;
import com.github.kevinjava.ngaclient.service.Task;
import com.github.kevinjava.ngaclient.ui.LoginActivity;

/**
 * Created by kevliu on 6/26/14.
 */
public class NotLoginTask implements Task {
    private Activity mContext;
    private HttpRequestBean bean;
    private Fragment fragment;

    @Override
    public void execue() {
        fragment.startActivityForResult(new Intent(fragment.getActivity(), LoginActivity.class), 100);

    }

    public void setContext(Activity context) {
        this.mContext = context;
    }

    public void setHttpRequestBean(HttpRequestBean bean) {
        this.bean = bean;
    }

    public HttpRequestBean getBean() {
        return bean;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
