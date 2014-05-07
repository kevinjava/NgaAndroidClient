package com.kevinjava.ngaclient.model;

public class Comment {
	long pid;
	long authorid;
	String postdate;
	String alterinfo;
	String content;
	int lou;
	public long getPid() {
		return pid;
	}
	public void setPid(long pid) {
		this.pid = pid;
	}
	public long getAuthorid() {
		return authorid;
	}
	public void setAuthorid(long authorid) {
		this.authorid = authorid;
	}
	public String getPostdate() {
		return postdate;
	}
	public void setPostdate(String postdate) {
		this.postdate = postdate;
	}
	public String getAlterinfo() {
		return alterinfo;
	}
	public void setAlterinfo(String alterinfo) {
		this.alterinfo = alterinfo;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getLou() {
		return lou;
	}
	public void setLou(int lou) {
		this.lou = lou;
	}
}
