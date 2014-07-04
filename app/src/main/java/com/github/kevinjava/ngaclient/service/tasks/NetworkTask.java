package com.github.kevinjava.ngaclient.service.tasks;

import android.view.SurfaceView;

import com.github.kevinjava.ngaclient.NgaApp;
import com.github.kevinjava.ngaclient.model.HttpRequestBean;
import com.github.kevinjava.ngaclient.manager.NetworkManager;
import com.github.kevinjava.ngaclient.service.NgaHttpRequestHandler;
import com.github.kevinjava.ngaclient.service.Task;
import com.github.kevinjava.ngaclient.util.URLCreator;
import com.kevinjava.ngaclient.controller.NetRequestType;
import com.kevinjava.ngaclient.states.ResultStates;

/**
 * Created by kevliu on 6/24/14.
 */
public class NetworkTask implements Task {

    String url;
    NetworkManager networkManager;
    HttpRequestBean bean;

    public NetworkTask(NetRequestType type, int fatherIndex, int subIndex, int page) {
        this.url = URLCreator.getForumUrl(fatherIndex, page, subIndex);
        bean = new HttpRequestBean(type,
                fatherIndex, URLCreator.getFid(fatherIndex, subIndex), url, true);
        networkManager = NetworkManager.getInstance();
    }

    @Override
    public void execue() {

        ResultStates status = NetworkManager.getInstance().asyncGetRequest(
                url,
                new NgaHttpRequestHandler(bean, networkManager.getInstance().getDatastore()));
    }
}
