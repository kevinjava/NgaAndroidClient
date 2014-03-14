package com.kevinjava.ngaclient.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.util.Log;

public class ThreadData {
	private List<ForumDataBean> rowList;
	private int rowNum;

	public List<ForumDataBean> getRowList() {
		return rowList;
	}

	public void setRowList(List<ForumDataBean> rowList) {
		this.rowList = rowList;
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public void mergeList(ThreadData data) {

		List<ForumDataBean> aList = new ArrayList<ForumDataBean>();
		aList.addAll(data.rowList);
		aList.retainAll(rowList);
		Log.i("test", "the same list item");
		for (ForumDataBean bean : aList) {
			Log.i("test", "before" + bean.subject);
		}
		Log.i("test", "remove the same");
		for (Iterator iterator = aList.iterator(); iterator.hasNext();) {
			ForumDataBean forumDataBean = (ForumDataBean) iterator.next();
			int index = rowList.indexOf(forumDataBean);
			rowList.remove(index);
			rowList.add(index, forumDataBean);
			data.rowList.remove(forumDataBean);
		}
		Log.i("test", "after removed");
		for (ForumDataBean bean : data.rowList) {
			Log.i("test", "after" + bean.subject);
		}
		rowList.addAll(data.rowList);
		rowNum = rowList.size();
		Log.i("test", "final list size" + rowNum);
	}

}
