package com.kevinjava.ngaclient.model;

import com.kevinjava.ngaclient.controller.NetRequestType;

public class HttpRequestBean implements Comparable<HttpRequestBean> {
	private NetRequestType type;
	private int fid;
	private String url;

	public HttpRequestBean(NetRequestType type, int fid, String url) {
		this.type = type;
		this.fid = fid;
		this.url = url;
	}

	public NetRequestType getType() {
		return type;
	}

	public void setType(NetRequestType type) {
		this.type = type;
	}

	public int getFid() {
		return fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public int compareTo(HttpRequestBean another) {
		if (another.fid == fid && another.type.equals(type)
				&& another.url.equals(url)) {
			return 0;
		} else {
			return 1;
		}
	}
}