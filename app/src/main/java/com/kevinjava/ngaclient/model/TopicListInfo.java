package com.kevinjava.ngaclient.model;

import java.util.List;

public class TopicListInfo {
	int __ROWS;
	int __T__ROWS;
	int __SELECTED_FORUM;

	List<ThreadPageInfo> articleEntryList;

	public int get__ROWS() {
		return __ROWS;
	}

	public void set__ROWS(int __ROWS) {
		this.__ROWS = __ROWS;
	}

	public int get__T__ROWS() {
		return __T__ROWS;
	}

	public void set__T__ROWS(int __T__ROWS) {
		this.__T__ROWS = __T__ROWS;
	}

	public int get__SELECTED_FORUM() {
		return __SELECTED_FORUM;
	}

	public void set__SELECTED_FORUM(int __SELECTED_FORUM) {
		this.__SELECTED_FORUM = __SELECTED_FORUM;
	}

	public List<ThreadPageInfo> getArticleEntryList() {
		return articleEntryList;
	}

	public void setArticleEntryList(List<ThreadPageInfo> articleEntryList) {
		this.articleEntryList = articleEntryList;
	}

}
