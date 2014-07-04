package com.github.kevinjava.ngaclient.model;

import java.util.ArrayList;

import android.text.Html;

import com.github.kevinjava.ngaclient.util.NgaLog;

public class SubjectData {
	int __ROWS;
	int __R__ROWS;
	int __R__ROWS_PAGE;
	ThreadPageInfo __T;
	ArrayList<ReplyPageInfo> __R;
	ArrayList<UserInfo> __U;

	public int get__ROWS() {
		return __ROWS;
	}

	public void set__ROWS(int __ROWS) {
		this.__ROWS = __ROWS;
	}

	public int get__R__ROWS() {
		return __R__ROWS;
	}

	public void set__R__ROWS(int __R__ROWS) {
		this.__R__ROWS = __R__ROWS;
	}

	public int get__R__ROWS_PAGE() {
		return __R__ROWS_PAGE;
	}

	public void set__R__ROWS_PAGE(int __R__ROWS_PAGE) {
		this.__R__ROWS_PAGE = __R__ROWS_PAGE;
	}

	public ThreadPageInfo get__T() {
		return __T;
	}

	public void set__T(ThreadPageInfo __T) {
		this.__T = __T;
	}

	public ArrayList<ReplyPageInfo> get__R() {
		return __R;
	}

	public void set__R(ArrayList<ReplyPageInfo> __R) {
		this.__R = __R;
	}

	public ArrayList<UserInfo> get__U() {
		return __U;
	}

	public void set__U(ArrayList<UserInfo> __U) {
		this.__U = __U;
	}
	
	public void printReply(){
		for (ReplyPageInfo info : __R) {
			if(info.content != null){
				NgaLog.e("test", info.content);
				NgaLog.i("test", Html.fromHtml(info.content).toString());
			}
			
			if(info.attachs != null){
//				NgaLog.i("test", info.attachs.get(0).attachurl);
			}
			
			if(info.comment != null){
//				NgaLog.i("test", info.comment.get(0).content);
			}
		}
	}
}
