package com.github.kevinjava.ngaclient.service.tasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.github.kevinjava.ngaclient.NgaApp;
import com.github.kevinjava.ngaclient.listener.IRequestFinished;
import com.github.kevinjava.ngaclient.listener.LoginStateCallback;
import com.github.kevinjava.ngaclient.manager.NetworkManager;
import com.github.kevinjava.ngaclient.service.Task;
import com.github.kevinjava.ngaclient.util.NgaLog;

import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

/**
 * Created by kevliu on 6/27/14.
 */
public class LoginTask implements Task{
    private LoginStateCallback callback;
    private final static String postUrl = "http://account.178.com/q_account.php?_act=login";
    private String email;
    private String password;

    public LoginTask(){

    }

    public LoginTask(String email, String password, LoginStateCallback callback){
        this.callback = callback;
        this.email = email;
        this.password = password;
    }

    @Override
    public void execue() {
        if(email == null && password == null){
            SharedPreferences ngaSP = NgaApp.mContext.getSharedPreferences("Nga", Context.MODE_PRIVATE);
            email = ngaSP.getString("account", "");
            password = ngaSP.getString("password","");
            if(TextUtils.isEmpty(email) && TextUtils.isEmpty(password)){
                return;
            }
        }
        HttpURLConnection conn = NetworkManager.getInstance().syncPost(postUrl, createLoginUrl(email, password));
        if (conn == null){
            if(callback != null){
                callback.isSuccess(false);
            }
            return;
        }

        String cookieVal = null;
        String key = null;

        String uid = "";
        String cid = "";
        String location = "";


        for (int i = 1; (key = conn.getHeaderFieldKey(i)) != null; i++) {
            NgaLog.d("TAG", conn.getHeaderFieldKey(i) + ":"
                    + conn.getHeaderField(i));
            if (key.equalsIgnoreCase("set-cookie")) {
                cookieVal = conn.getHeaderField(i);
                cookieVal = cookieVal.substring(0, cookieVal.indexOf(';'));
                if (cookieVal.indexOf("_sid=") == 0)
                    cid = cookieVal.substring(5);
                if (cookieVal.indexOf("_178c=") == 0)
                    uid = cookieVal.substring(6, cookieVal.indexOf('%'));
            }
            if (key.equalsIgnoreCase("Location")) {
                location = conn.getHeaderField(i);
            }
        }
        if (cid != "" && uid != ""
                && location.indexOf("login_success&error=0") != -1) {
            NetworkManager.getInstance().setUid(uid);
            NetworkManager.getInstance().setCid(cid);
            NgaLog.i("TAG", "uid =" + uid + ",csid=" + cid);
            NetworkManager.getInstance().setUid(uid);
            NetworkManager.getInstance().setCid(cid);
            SharedPreferences ngaSP = NgaApp.mContext.getSharedPreferences("Nga", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = ngaSP.edit();
            editor.putString("account", email);
            editor.putString("password", password);
            editor.commit();
            if(callback != null){
                callback.isSuccess(true);
                return;
            }
        }

        if(callback != null){
            callback.isSuccess(false);
        }
    }

    private String createLoginUrl(String userName, String password) {

        StringBuffer bodyBuffer = new StringBuffer();
        bodyBuffer.append("type=username&email=");

        try {
            bodyBuffer.append(URLEncoder.encode(userName, "utf-8"));
            bodyBuffer.append("&password=");
            bodyBuffer.append(URLEncoder.encode(password, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return bodyBuffer.toString();
    }
}
