package com.github.kevinjava.ngaclient.manager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;

import com.github.kevinjava.ngaclient.NgaApp;
import com.github.kevinjava.ngaclient.util.NgaLog;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.kevinjava.ngaclient.states.NetworkErrorStatus;
import com.kevinjava.ngaclient.states.ProcessRequestStatues;
import com.kevinjava.ngaclient.states.ResultStates;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import com.github.kevinjava.ngaclient.service.Datastore;

import org.apache.http.entity.StringEntity;

import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkManager {
    private AsyncHttpClient asyncHttpClient;
    private Context mContext;
    private Datastore datastore;
    private String uid;
    private String cid;

    public void setDatastore(Datastore datastore) {
        this.datastore = datastore;
    }

    public Datastore getDatastore() {
        return datastore;
    }

    private static class NetworkManagerHandler {
        static NetworkManager networkManager = new NetworkManager();
    }

    private NetworkManager() {
    }

    public void init(){
        asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.setUserAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/536.5 (KHTML, like Gecko) Chrome/19.0.1084.30 Safari/536.5");
        asyncHttpClient.addHeader("Accept-Charset", "GBK");
        asyncHttpClient.addHeader("Accept-Encoding", "gzip,deflate");
        mContext = NgaApp.getContext();
        datastore = new Datastore();
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCookie() {
        if (!TextUtils.isEmpty(uid) && !TextUtils.isEmpty(cid)) {
            return "ngaPassportUid=" + uid +
                    "; ngaPassportCid=" + cid;
        }
        return "";
    }

    public static NetworkManager getInstance() {
        return NetworkManagerHandler.networkManager;
    }

    public ResultStates asyncGetRequest(String url,
                                        AsyncHttpResponseHandler responseHandler) {
        if (!isNetworkConnected()) {
            responseHandler.onFailure(new Throwable(),"no network ");
            return new NetworkErrorStatus();
        }
        if (!TextUtils.isEmpty(getCookie())) {
            asyncHttpClient.addHeader("Cookie", getCookie());
        }
        asyncHttpClient.get(url, responseHandler);
        return new ProcessRequestStatues();
    }

    public HttpURLConnection syncPost(String requestUrl, String body) {
        HttpURLConnection conn;
        try {
            URL url = new URL(requestUrl);
            conn = (HttpURLConnection) url.openConnection();
            if (!TextUtils.isEmpty(getCookie()))
                conn.setRequestProperty("Cookie", getCookie());
            conn.setInstanceFollowRedirects(false);

            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/536.5 (KHTML, like Gecko) Chrome/19.0.1084.30 Safari/536.5");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(body.length()));
            conn.setRequestProperty("Accept-Charset", "GBK");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            conn.connect();

            OutputStreamWriter out = new OutputStreamWriter(conn
                    .getOutputStream());
            out.write(body);
            out.flush();
            out.close();

        } catch (Exception e) {
            //sb.append(e.toString());
            conn = null;
        }
        return conn;
    }

    private boolean isNetworkConnected() {
        NetworkInfo activeNetwork = getSystemNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    private boolean isWifi() {
        boolean isWiFi = getSystemNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
        return isWiFi;
    }

    private NetworkInfo getSystemNetworkInfo() {
        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork;
    }
}
