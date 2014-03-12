package com.kevinjava.ngaclient.network;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class NetworkManager {
	private AsyncHttpClient asyncHttpClient;
	private static class NetworkManagerHandler{
		static NetworkManager networkManager = new NetworkManager();
	}
	private NetworkManager() {
		asyncHttpClient = new AsyncHttpClient();
		asyncHttpClient.setUserAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/536.5 (KHTML, like Gecko) Chrome/19.0.1084.30 Safari/536.5");
		asyncHttpClient.addHeader("Accept-Charset", "GBK");
		asyncHttpClient.addHeader("Accept-Encoding", "gzip,deflate");
	}

	public static NetworkManager getInstance() {
		return NetworkManagerHandler.networkManager;
	}

	public void asyncGetRequest(String url,
			AsyncHttpResponseHandler responseHandler) {
		asyncHttpClient.get(url, responseHandler);
	}
}
