package com.github.kevinjava.ngaclient.listener;

import com.kevinjava.ngaclient.controller.NetRequestType;
import com.github.kevinjava.ngaclient.model.HttpRequestBean;


public interface Observable{
	void add(ForumObserver observable);
	void remove(ForumObserver observable);
	void notifyChange(HttpRequestBean bean);
}