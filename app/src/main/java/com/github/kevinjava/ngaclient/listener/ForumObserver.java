package com.github.kevinjava.ngaclient.listener;

import com.kevinjava.ngaclient.controller.ForumDataModelIF;
import com.github.kevinjava.ngaclient.model.HttpRequestBean;
import com.kevinjava.ngaclient.states.ResultStates;

public interface ForumObserver {
	void update(HttpRequestBean bean);
}
