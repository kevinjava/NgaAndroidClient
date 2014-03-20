package com.kevinjava.ngaclient.listener;

import com.kevinjava.ngaclient.constant.ToastType;
import com.kevinjava.ngaclient.controller.ForumDataModelIF;
import com.kevinjava.ngaclient.model.HttpRequestBean;

public interface ForumObserver {
	void update(HttpRequestBean bean, ForumDataModelIF dataModel);
	void notifyToast(ToastType type);
}
