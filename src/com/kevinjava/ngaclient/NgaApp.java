package com.kevinjava.ngaclient;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.kevinjava.ngaclient.model.GroupModel;

public class NgaApp extends Application {
	public static ArrayList<GroupModel> groups;
	public static Context mContext;
	public void onCreate() {
		init();
		mContext = this.getApplicationContext();
	};
	
	public static Context getContext(){
		return mContext;
	}
	
	public void init(){
		InputStream forumsIS;
		try {
			forumsIS = getAssets().open("forums.json");
			Scanner s = new Scanner(forumsIS).useDelimiter("\\A");
			String jsonStr = s.hasNext() ? s.next() : "";
			groups = (ArrayList<GroupModel>) JSON.parseArray(jsonStr, GroupModel.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
