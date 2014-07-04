package com.kevinjava.ngaclient.ui;

import java.util.ArrayList;
import java.util.List;

import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.github.kevinjava.ngaclient.R;
import com.github.kevinjava.ngaclient.ui.ThreadDetailActivity;
import com.github.kevinjava.ngaclient.ui.adapter.MainForumListAdapter;
import com.kevinjava.ngaclient.controller.NetRequestType;
import com.github.kevinjava.ngaclient.model.ForumDataBean;
import com.github.kevinjava.ngaclient.model.HttpRequestBean;
import com.github.kevinjava.ngaclient.model.ThreadData;
import com.kevinjava.ngaclient.states.LoadMoreSuccessStates;
import com.kevinjava.ngaclient.states.NetworkErrorStatus;
import com.kevinjava.ngaclient.states.NoNewThreadStates;
import com.kevinjava.ngaclient.states.NotLoginState;
import com.kevinjava.ngaclient.states.RefreshDataSuccessStates;
import com.kevinjava.ngaclient.states.ResultStates;
import com.github.kevinjava.ngaclient.util.NgaLog;

public class MainForumFragement extends NgaBaseFragment implements
		IXListViewListener,OnItemClickListener {
	private static final String TAG = MainForumFragement.class.getSimpleName();
	XListView listView;
	ThreadData data;
	int index;
	int tabIndex;
	int page;
	MainForumListAdapter adapter;
	AnimationTextView toastView;
	int currentDataSize = 0;

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
		listView.setOnItemClickListener(this);
		listView.setSelector(R.drawable.listview_selector);
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
		adapter = new MainForumListAdapter(data, getActivity());
		listView.setAdapter(adapter);
	}

	public void setIndex(int index, int tabIndex, int page) {
		this.index = index;
		this.tabIndex = tabIndex;
		this.page = page;
	}

	public synchronized void setDataChange(ThreadData data, HttpRequestBean bean) {
		this.data = data;
		if (bean.getType() == NetRequestType.RefrushForumData) {
			List<ForumDataBean> aList = new ArrayList<ForumDataBean>();
			aList.addAll(adapter.getThreadData().getRowList());
			aList.retainAll(data.getRowList());
			int count = data.getRowNum() - aList.size();
			aList.clear();
			adapter = new MainForumListAdapter(data, getActivity());
			listView.setAdapter(adapter);
			notifyToast(count == 0 ? new NoNewThreadStates()
					: new RefreshDataSuccessStates(count));
			listView.stopRefresh();
			// listView.setRefreshTime("刚刚");
		} else {
			adapter.setThreadData(data);
			adapter.notifyDataSetChanged();
			if ((bean.getType() == NetRequestType.OnLoadMore)) {
				listView.stopLoadMore();
				notifyToast(new LoadMoreSuccessStates(adapter.getCount() - currentDataSize));
				currentDataSize = adapter.getCount();
			}else {
				// tab change request
				notifyToast(new RefreshDataSuccessStates(data.getRowNum()));
				listView.stopRefresh();
			}
		}
		currentDataSize = data.getRowNum();
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

	public void notifyToast(ResultStates states) {
		if(states instanceof NetworkErrorStatus || states instanceof NotLoginState){
			listView.stopRefresh();
		}
		states.showToast(toastView);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		NgaLog.i(TAG, "current data:" + data.getRowList().get(arg2 - 1).getSubject());
		Intent intent = new Intent(getActivity(),ThreadDetailActivity.class);
		if(!TextUtils.isEmpty(data.getRowList().get(arg2 -1).getQuote_from()) && !data.getRowList().get(arg2 -1).getQuote_from().equals("0")){
			intent.putExtra("tid", data.getRowList().get(arg2 -1).getQuote_from());
		}else {
			intent.putExtra("tid", data.getRowList().get(arg2 -1).getTid());
		}
		startActivity(intent);
		getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}
}
