package com.kevinjava.ngaclient.network;

import org.apache.http.Header;

import com.kevinjava.ngaclient.controller.ForumDataModelIF;
import com.kevinjava.ngaclient.model.HttpRequestBean;
import com.kevinjava.ngaclient.model.ThreadData;
import com.kevinjava.ngaclient.util.ArticleUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class AsyncForumHttpResponseHandler extends AsyncHttpResponseHandler {

	boolean cancel = false;
	
	HttpRequestBean bean;
	ForumDataModelIF forumDataModel;
	
	public void cancelRequest() {
		cancel = true;
	}

	public AsyncForumHttpResponseHandler(HttpRequestBean bean, ForumDataModelIF forumData) {
		this.bean = bean;
		this.forumDataModel = forumData;
	}

	@Override
	public void onSuccess(int statusCode, Header[] headers, String content) {
		super.onSuccess(statusCode, headers, content);
	}

	@Override
	public void onFailure(Throwable error, String content) {
		super.onFailure(error, content);
	}

	public void onSuccess(String content) {
		ThreadData data = ArticleUtil.parseJsonThreadPage(content);
		forumDataModel.addNewForumData(bean, data);
	};

	@Override
	public void onSuccess(int statusCode, String content) {
		super.onSuccess(statusCode, content);
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onFinish() {
		super.onFinish();
	}
}
