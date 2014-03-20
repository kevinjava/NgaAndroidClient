package com.kevinjava.ngaclient.controller;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.util.Log;

import com.kevinjava.ngaclient.NgaApp;
import com.kevinjava.ngaclient.constant.ToastType;
import com.kevinjava.ngaclient.listener.ForumObserver;
import com.kevinjava.ngaclient.model.GroupModel;
import com.kevinjava.ngaclient.model.HttpRequestBean;
import com.kevinjava.ngaclient.model.ThreadData;
import com.kevinjava.ngaclient.network.AsyncForumHttpResponseHandler;
import com.kevinjava.ngaclient.network.NetworkManager;

@SuppressLint("UseSparseArrays")
public class ForumDataModel implements ForumDataModelIF {

	private HashMap<Integer, ThreadData> forumDatas;
	private HashMap<Integer, Integer> pageDatas;
	private HashMap<NetRequestType, ForumObserver> observers;
	private Object sync = new Object();

	public ForumDataModel() {
		forumDatas = new HashMap<Integer, ThreadData>();
		observers = new HashMap<NetRequestType, ForumObserver>();
		pageDatas = new HashMap<Integer, Integer>();
	}

	@Override
	public void ferchData(NetRequestType type, int forumIndex, int tabIndex,
			int page, String url) {
		final ArrayList<GroupModel> forums = NgaApp.groups;
		GroupModel currentGroup = forums.get(forumIndex);
		int fid = currentGroup.forums.get(tabIndex).fid;
		Integer lastPage = pageDatas.get(fid);
		if(lastPage == null || page != 1){
			pageDatas.put(fid, page);
		}
		NetworkManager.getInstance().asyncGetRequest(
				url,
				new AsyncForumHttpResponseHandler(new HttpRequestBean(type,
						fid, url, page == 1), this));
	}

	@Override
	public void addNewForumData(HttpRequestBean httpBean, ThreadData bean) {
		if (bean == null) {
			// not login
			Log.e("test", "object is null");
			notifyToast(httpBean, ToastType.NotLogin);
			return;
		}
		synchronized (sync) {
			ThreadData data = forumDatas.get(httpBean.getFid());
			if (data != null) {
				if (httpBean.getType() == NetRequestType.RefrushForumData) {
					Log.e("test", "refrush merge the list");
//					bean.mergeList(data);
					forumDatas.put(httpBean.getFid(), bean);
				} else {
					Log.e("test", "merge the list");
					data.mergeList(bean);
				}
			} else {
				Log.e("test", "put to the map");
				forumDatas.put(httpBean.getFid(), bean);
			}
			notifyChange(httpBean);
		}
	}

	@Override
	public void add(NetRequestType type, ForumObserver observable) {
		observers.put(type, observable);
	}

	@Override
	public void remove(NetRequestType type, ForumObserver observable) {
		if (observers.containsKey(type)) {
			observers.remove(type);
		}
	}

	@Override
	public void notifyChange(HttpRequestBean bean) {
		ForumObserver ob = observers.get(bean.getType());
		if (ob != null) {
			ob.update(bean, this);
		}
	}
	
	public void notifyToast(HttpRequestBean bean, ToastType type){
		ForumObserver ob = observers.get(bean.getType());
		if (ob != null) {
			ob.notifyToast(type);
		}
	}

	@Override
	public ThreadData getPageData(NetRequestType type, int fid) {
		return forumDatas.get(fid);
	}

	@Override
	public int getPageInfo(int fid) {
		Integer page = pageDatas.get(fid);
		if(page == null){
			page = 1;
		}
		return page;
	}
	
}
