package com.kevinjava.ngaclient.model;

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
	
	public void mergeList(ThreadData data){
		rowList.removeAll(data.rowList);
		rowList.addAll(data.rowList);
		rowNum = rowList.size();
	}

}
