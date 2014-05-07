package com.kevinjava.ngaclient.listener;



public interface ViewControllIF{
	void onForumChange(int index, int tabIndex);
	void onTabChange(int index, int tabIndex);

	void onRefreshPage(int index, int tabIndex, int page);
	void onloadMore(int index, int tabIndex, int page);
}
