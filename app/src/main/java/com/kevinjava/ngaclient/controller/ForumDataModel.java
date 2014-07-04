package com.kevinjava.ngaclient.controller;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;

import com.github.kevinjava.ngaclient.NgaApp;
import com.github.kevinjava.ngaclient.listener.ForumObserver;
import com.github.kevinjava.ngaclient.model.GroupModel;
import com.github.kevinjava.ngaclient.model.HttpRequestBean;
import com.github.kevinjava.ngaclient.model.ThreadData;
import com.kevinjava.ngaclient.network.AsyncForumHttpResponseHandler;
import com.github.kevinjava.ngaclient.manager.NetworkManager;
import com.kevinjava.ngaclient.states.NotLoginState;
import com.kevinjava.ngaclient.states.ResultStates;
import com.github.kevinjava.ngaclient.util.NgaLog;

@SuppressLint("UseSparseArrays")
public class ForumDataModel implements ForumDataModelIF {
	private static final String TAG = ForumDataModel.class.getSimpleName();

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
		HttpRequestBean bean = new HttpRequestBean(type,
				fid, url, page == 1);
		ResultStates status = NetworkManager.getInstance().asyncGetRequest(
				url,
				new AsyncForumHttpResponseHandler(bean, this));
		if(status != null){
			notifyToast(bean, status);
		}
	}

	@Override
	public void addNewForumData(HttpRequestBean httpBean, ThreadData bean) {
		if (bean == null) {
			// not login
			NgaLog.e(TAG, "object is null");
			notifyToast(httpBean, new NotLoginState());
			return;
		}
		synchronized (sync) {
			ThreadData data = forumDatas.get(httpBean.getFid());
			if (data != null) {
				if (httpBean.getType() == NetRequestType.RefrushForumData) {
					NgaLog.e(TAG, "refrush merge the list");
//					bean.mergeList(data);
					forumDatas.put(httpBean.getFid(), bean);
				} else {
					NgaLog.e(TAG, "merge the list");
					data.mergeList(bean);
				}
			} else {
				NgaLog.e(TAG, "put to the map");
				forumDatas.put(httpBean.getFid(), bean);
			}
			notifyChange(httpBean);
		}
	}


	public void add(NetRequestType type, ForumObserver observable) {
		observers.put(type, observable);
	}

	public void remove(NetRequestType type, ForumObserver observable) {
		if (observers.containsKey(type)) {
			observers.remove(type);
		}
	}

    @Override
    public void add(ForumObserver observable) {

    }

    @Override
    public void remove(ForumObserver observable) {

    }

    public void notifyChange(HttpRequestBean bean) {
//		ForumObserver ob = observers.get(bean.getType());
//		if (ob != null) {
//			ob.update(bean, this);
//		}
	}
	
	public void notifyToast(HttpRequestBean bean, ResultStates state){
//		ForumObserver ob = observers.get(bean.getType());
//		if (ob != null) {
//			ob.notifyToast(state);
//		}
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
