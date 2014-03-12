package com.kevinjava.ngaclient.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.costum.android.widget.PullAndLoadListView;
import com.costum.android.widget.PullAndLoadListView.OnLoadMoreListener;
import com.costum.android.widget.PullToRefreshListView.OnRefreshListener;
import com.kevinjava.ngaclient.R;
import com.kevinjava.ngaclient.model.ThreadData;

public class MainForumFragement extends NgaBaseFragment {

	PullAndLoadListView listView;
	ThreadData data;
	String[] mListItems;

	public MainForumFragement setThreadData(ThreadData data){
		this.data = data;
//		mListItems = new String[data.getRowNum()];
//		int i = 0;
//		for (ForumDataBean bean : data.getRowList()) {
//			mListItems[i] = bean.getSubject();
//			i++;
//		}
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
		listView = (PullAndLoadListView) view.findViewById(android.R.id.list);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		BaseAdapter adapter = new MainForumAdapter(data, getActivity());
		listView.setAdapter(adapter);
		// Set a listener to be invoked when the list should be refreshed.
		listView.setOnRefreshListener(new OnRefreshListener() {

			public void onRefresh() {
				// Do work to refresh the list here.
				// new PullToRefreshDataTask().execute();
			}
		});

		// set a listener to be invoked when the list reaches the end
		listView.setOnLoadMoreListener(new OnLoadMoreListener() {

			public void onLoadMore() {
				// Do the work to load more items at the end of list
				// here
				// new LoadMoreDataTask().execute();
			}
		});
		super.onViewCreated(view, savedInstanceState);
	}
}
