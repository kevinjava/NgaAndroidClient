package com.kevinjava.ngaclient.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.costum.android.widget.PullAndLoadListView;
import com.costum.android.widget.PullAndLoadListView.OnLoadMoreListener;
import com.costum.android.widget.PullToRefreshListView.OnRefreshListener;
import com.kevinjava.ngaclient.R;
import com.kevinjava.ngaclient.controller.NetRequestType;
import com.kevinjava.ngaclient.model.HttpRequestBean;
import com.kevinjava.ngaclient.model.ThreadData;

public class MainForumFragement extends NgaBaseFragment {

	PullAndLoadListView listView;
	ThreadData data;
	int index;
	int tabIndex;
	int page;
	MainForumAdapter adapter;

	public MainForumFragement setThreadData(ThreadData data){
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
		listView = (PullAndLoadListView) view.findViewById(android.R.id.list);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		adapter = new MainForumAdapter(data, getActivity());
		listView.setAdapter(adapter);
		// Set a listener to be invoked when the list should be refreshed.
		listView.setOnRefreshListener(new OnRefreshListener() {

			public void onRefresh() {
				page = 1;
				controller.onRefreshPage(index, tabIndex, page);
			}
		});

		// set a listener to be invoked when the list reaches the end
		listView.setOnLoadMoreListener(new OnLoadMoreListener() {

			public void onLoadMore() {
				controller.onloadMore(index, tabIndex, ++page);
			}
		});
		
		// Set a listener to be invoked when the list should be refreshed.
		/*listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
					@Override
					public void onRefresh(PullToRefreshBase<ListView> refreshView) {
						String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

						// Update the LastUpdatedLabel
						refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

						// Do work to refresh the list here.
						new GetDataTask().execute();
					}
				});

				// Add an end-of-list listener
		listView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

					@Override
					public void onLastItemVisible() {
						Toast.makeText(getActivity(), "End of List!", Toast.LENGTH_SHORT).show();
					}
				});

				ListView actualListView = listView.getRefreshableView();

				// Need to use the Actual ListView when registering for Context Menu
				registerForContextMenu(actualListView);


				*//**
				 * Add Sound Event Listener
				 *//*
				SoundPullEventListener<ListView> soundListener = new SoundPullEventListener<ListView>(getActivity());
				soundListener.addSoundEvent(State.PULL_TO_REFRESH, R.raw.psst2);
				soundListener.addSoundEvent(State.RESET, R.raw.pop);
				soundListener.addSoundEvent(State.REFRESHING, R.raw.psst1);
				listView.setOnPullEventListener(soundListener);

				// You can also just use setListAdapter(mAdapter) or
				// mPullRefreshListView.setAdapter(mAdapter)
				adapter = new MainForumAdapter(data, getActivity());
				listView.setAdapter(adapter);
				
			}
	
	

			private class GetDataTask extends AsyncTask<Void, Void, String[]> {

				@Override
				protected String[] doInBackground(Void... params) {
					// Simulates a background job.
					try {
						Thread.sleep(4000);
					} catch (InterruptedException e) {
					}
					return new String[]{"1","2"};
				}

				@Override
				protected void onPostExecute(String[] result) {

					// Call onRefreshComplete when the list has been refreshed.
					listView.onRefreshComplete();

					super.onPostExecute(result);
				} */
			}
			
			
	public void setIndex(int index, int tabIndex, int page){
		this.index = index;
		this.tabIndex = tabIndex;
		this.page = page;
	}
	
	public void setDataChange(ThreadData data, HttpRequestBean bean){
		adapter.setThreadData(data);
		adapter.notifyDataSetChanged();
		if(bean.isSelectFirst()){
			listView.setSelection(1);
		}
		if(bean.getType() == NetRequestType.RefrushForumData){
			listView.onRefreshComplete();
		}else if(bean.getType() == NetRequestType.OnLoadMore){
			listView.onLoadMoreComplete();
		}
			
	}
}
