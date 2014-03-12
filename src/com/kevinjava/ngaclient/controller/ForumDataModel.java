package com.kevinjava.ngaclient.controller;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.util.Log;

import com.kevinjava.ngaclient.NgaApp;
import com.kevinjava.ngaclient.listener.ForumObserver;
import com.kevinjava.ngaclient.model.GroupModel;
import com.kevinjava.ngaclient.model.HttpRequestBean;
import com.kevinjava.ngaclient.model.ThreadData;
import com.kevinjava.ngaclient.network.AsyncForumHttpResponseHandler;
import com.kevinjava.ngaclient.network.NetworkManager;

@SuppressLint("UseSparseArrays")
public class ForumDataModel implements ForumDataModelIF {

	private HashMap<Integer, ThreadData> forumDatas;
	private HashMap<NetRequestType, ForumObserver> observers;
	private Object sync = new Object();

	public ForumDataModel() {
		forumDatas = new HashMap<Integer, ThreadData>();
		observers = new HashMap<NetRequestType, ForumObserver>();
	}

	@Override
	public void ferchData(NetRequestType type, int forumIndex, int tabIndex, String url) {
		final ArrayList<GroupModel> forums = NgaApp.groups;
		GroupModel currentGroup = forums.get(forumIndex);
		int fid = currentGroup.forums.get(tabIndex).fid;
		NetworkManager.getInstance().asyncGetRequest(
				url,
				new AsyncForumHttpResponseHandler(new HttpRequestBean(type,
						fid, url), this));
	}

	@Override
	public void addNewForumData(HttpRequestBean httpBean, ThreadData bean) {
		if (bean == null) {
			// not login
			Log.e("test", "object is null");
			return;
		}
		synchronized (sync) {
			if (httpBean.getType() == NetRequestType.RefrushForumData) {
				forumDatas.put(httpBean.getFid(), bean);
			} else {
				ThreadData data = forumDatas.get(httpBean.getFid());
				if (data != null) {
					Log.e("test", "merge the list");
					data.mergeList(bean);
				} else {
					Log.e("test", "put to the map");
					forumDatas.put(httpBean.getFid(), bean);
				}
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

	@Override
	public ThreadData getPageData(NetRequestType type, int fid) {
		return forumDatas.get(fid);
	}
}