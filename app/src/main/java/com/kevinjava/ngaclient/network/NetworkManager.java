package com.kevinjava.ngaclient.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.kevinjava.ngaclient.NgaApp;
import com.kevinjava.ngaclient.states.NetworkErrorStatus;
import com.kevinjava.ngaclient.states.ProcessRequestStatues;
import com.kevinjava.ngaclient.states.ResultStates;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class NetworkManager {
	private AsyncHttpClient asyncHttpClient;
	private Context mContext;
	private static class NetworkManagerHandler{
		static NetworkManager networkManager = new NetworkManager();
	}
	private NetworkManager() {
		asyncHttpClient = new AsyncHttpClient();
		asyncHttpClient.setUserAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/536.5 (KHTML, like Gecko) Chrome/19.0.1084.30 Safari/536.5");
		asyncHttpClient.addHeader("Accept-Charset", "GBK");
		asyncHttpClient.addHeader("Accept-Encoding", "gzip,deflate");
		mContext = NgaApp.getContext();
	}

	public static NetworkManager getInstance() {
		return NetworkManagerHandler.networkManager;
	}

	public ResultStates asyncGetRequest(String url,
			AsyncHttpResponseHandler responseHandler) {
		if(!isNetworkConnected()){
			return new NetworkErrorStatus();
		}
		asyncHttpClient.get(url, responseHandler);
		return new ProcessRequestStatues();
	}
	
	private boolean isNetworkConnected(){
		NetworkInfo activeNetwork = getSystemNetworkInfo();
		boolean isConnected = activeNetwork != null &&
		                      activeNetwork.isConnectedOrConnecting();
		return isConnected;
	}
	
	private boolean isWifi(){
		boolean isWiFi = getSystemNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
		return isWiFi;
	}
	
	private NetworkInfo getSystemNetworkInfo(){
		ConnectivityManager cm =
		        (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		 
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		return activeNetwork;
	}
}
