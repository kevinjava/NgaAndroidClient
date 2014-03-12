package com.kevinjava.ngaclient.network;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class FrechForumAsynctask {
	public static void main(String[] args) {
		new AsyncHttpClient().get("", new AsyncHttpResponseHandler());
	}
}
