package com.github.kevinjava.ngaclient;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.github.kevinjava.ngaclient.model.ForumModel;
import com.github.kevinjava.ngaclient.model.GroupModel;
import com.github.kevinjava.ngaclient.util.NgaLog;

import cn.trinea.android.common.service.impl.ImageCache;

public class NgaApp extends Application {
	public static ArrayList<GroupModel> groups;
	public static Context mContext;
    private static int indexs;
    public static final ImageCache imageCache = new ImageCache();

    static {
        ImageCache.OnImageCallbackListener imageCallBack = new ImageCache.OnImageCallbackListener() {

            private static final long serialVersionUID = 1L;

            @Override
            public void onImageLoaded(String imageUrl, Drawable imageDrawable, View view, boolean isInCache) {
                NgaLog.i("TAG", "<------got image "+imageUrl+"------>");
                if (view != null && imageDrawable != null && view instanceof ImageView) {
                    if(view.getTag() != null){
                        String url = (String) view.getTag();
                        if(imageUrl.equals(url)){
                            ImageView imageView = (ImageView)view;
                            imageView.setImageDrawable(imageDrawable);
                        }
                    }
                }else{
                    ImageView imageView = (ImageView)view;
                    imageView.setImageResource(R.drawable.default_user_avatar);
                }

            }

        };
        imageCache.setOnImageCallbackListener(imageCallBack);
    }

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
    
    public static void setCurrentIndex(int index){
        indexs = index;
    }

    public static int getCurrentIndex(){
        return indexs;
    }
    
    public static GroupModel getCurrentForum(){
        return groups.get(indexs);
    }
}
