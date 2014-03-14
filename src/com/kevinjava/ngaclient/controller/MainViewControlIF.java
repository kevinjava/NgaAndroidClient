package com.kevinjava.ngaclient.controller;


public interface MainViewControlIF extends SlideViewControlIF {
	void goToDetailPage();
	void switchForum(int index, int tabIndex);
	void handlerForumData(String data);
	void onRefreshPage(int index, int tabIndex, int page);
	void onloadMore(int index, int tabIndex, int page);
	boolean onTabChange(int index, int tabIndex, ForumDataModelIF forumDataModelIF);
}
