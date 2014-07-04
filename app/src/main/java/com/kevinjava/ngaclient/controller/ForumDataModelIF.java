package com.kevinjava.ngaclient.controller;

import com.github.kevinjava.ngaclient.listener.Observable;
import com.github.kevinjava.ngaclient.model.HttpRequestBean;
import com.github.kevinjava.ngaclient.model.ThreadData;


public interface ForumDataModelIF extends Observable{
	void ferchData(NetRequestType type, int forumIndex, int tabIndex, int page, String url);
	void addNewForumData(HttpRequestBean httpBean, ThreadData bean);
	ThreadData getPageData(NetRequestType type, int fid);
	int getPageInfo(int fid);
}
