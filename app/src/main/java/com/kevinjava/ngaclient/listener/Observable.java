package com.kevinjava.ngaclient.listener;

import com.kevinjava.ngaclient.controller.NetRequestType;
import com.kevinjava.ngaclient.model.HttpRequestBean;


public interface Observable{
	void add(NetRequestType type, ForumObserver observable);
	void remove(NetRequestType type, ForumObserver observable);
	void notifyChange(HttpRequestBean bean);
}