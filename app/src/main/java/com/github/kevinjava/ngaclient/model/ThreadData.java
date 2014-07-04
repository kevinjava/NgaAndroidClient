package com.github.kevinjava.ngaclient.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
		for (Iterator<ForumDataBean> iterator = aList.iterator(); iterator
				.hasNext();) {
			ForumDataBean forumDataBean = iterator.next();
			int index = rowList.indexOf(forumDataBean);
			rowList.remove(index);
			rowList.add(index, forumDataBean);
			data.rowList.remove(forumDataBean);
		}
		rowList.addAll(data.rowList);
		rowNum = rowList.size();
	}

}
