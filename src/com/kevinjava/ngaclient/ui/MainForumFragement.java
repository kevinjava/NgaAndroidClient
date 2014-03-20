package com.kevinjava.ngaclient.ui;

import java.util.ArrayList;
import java.util.List;

import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kevinjava.ngaclient.R;
import com.kevinjava.ngaclient.constant.ToastType;
import com.kevinjava.ngaclient.controller.NetRequestType;
import com.kevinjava.ngaclient.model.ForumDataBean;
import com.kevinjava.ngaclient.model.HttpRequestBean;
import com.kevinjava.ngaclient.model.ThreadData;

public class MainForumFragement extends NgaBaseFragment implements
		IXListViewListener {

	XListView listView;
	ThreadData data;
	int index;
	int tabIndex;
	int page;
	MainForumAdapter adapter;
	AnimationTextView toastView;

	public MainForumFragement setThreadData(ThreadData data) {
		this.data = data;
		return this;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.pull_and_load, null);
		toastView = (AnimationTextView) view.findViewById(R.id.toastView);
		listView = (XListView) view.findViewById(R.id.xListView);
		listView.setPullLoadEnable(true);
		listView.setXListViewListener(this);
		return view;
	}

	@Override
	public void onRefresh() {
		page = 1;
		controller.onRefreshPage(index, tabIndex, page);
	}

	@Override
	public void onLoadMore() {
		controller.onloadMore(index, tabIndex, ++page);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		adapter = new MainForumAdapter(data, getActivity());
		listView.setAdapter(adapter);
	}

	public void setIndex(int index, int tabIndex, int page) {
		this.index = index;
		this.tabIndex = tabIndex;
		this.page = page;
	}

	public synchronized void setDataChange(ThreadData data, HttpRequestBean bean) {
		listView.stopRefresh();
		if (bean.getType() == NetRequestType.RefrushForumData) {
			List<ForumDataBean> aList = new ArrayList<ForumDataBean>();
			aList.addAll(adapter.getThreadData().getRowList());
			aList.retainAll(data.getRowList());
			int count = data.getRowNum() - aList.size();
			aList.clear();
			adapter = new MainForumAdapter(data, getActivity());
			listView.setAdapter(adapter);
			notifyToast(ToastType.Success, count);
			// listView.setRefreshTime("刚刚");
		} else {
			adapter.setThreadData(data);
			adapter.notifyDataSetChanged();
			if ((bean.getType() == NetRequestType.OnLoadMore)) {
				listView.stopLoadMore();
			}
			// mListView.setRefreshTime("刚刚");
		}
		if (bean.isSelectFirst()
				&& bean.getType() != NetRequestType.RefrushForumData) {
			listView.setSelection(1);
		}

	}

	public void setRefrush() {
		listView.setRefrush();
	}

	public void enableFootView() {
		listView.setPullLoadEnable(true);
	}

	public void notifyToast(ToastType type) {
		if (type == ToastType.Success) {

		} else if (type == ToastType.NotLogin) {

		} else if (type == ToastType.NetworkError) {

		}
	}

	public void notifyToast(final ToastType type, final int count) {
		toastView.setVisibility(View.VISIBLE);
		toastView.resetTextView();
		String showTxt = "";
		if(count > 0 ){
			showTxt = "新加载" + count + "条主题";
		}else {
			showTxt = "没有新主题";
		}
		toastView.setText(showTxt);
		toastView.postDelayed(new Runnable() {

			@Override
			public void run() {
				if (type == ToastType.Success) {
					toastView.startScroll();
				}
			}
		}, 800);

	}

}
