package com.kevinjava.ngaclient.controller;


public interface MainViewControlIF extends SlideViewControlIF {
	void goToDetailPage();
	void switchForum(int index, int tabIndex);
	void handlerForumData(String data);
}
