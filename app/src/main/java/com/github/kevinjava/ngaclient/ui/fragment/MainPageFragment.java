package com.github.kevinjava.ngaclient.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.kevinjava.ngaclient.R;
import com.github.kevinjava.ngaclient.customview.LoadMoreListView;
import com.github.kevinjava.ngaclient.manager.NetworkManager;
import com.github.kevinjava.ngaclient.manager.TaskManager;
import com.github.kevinjava.ngaclient.model.HttpRequestBean;
import com.github.kevinjava.ngaclient.model.ThreadData;
import com.github.kevinjava.ngaclient.service.Datastore;
import com.github.kevinjava.ngaclient.service.tasks.NetworkTask;
import com.github.kevinjava.ngaclient.service.tasks.NotLoginTask;
import com.github.kevinjava.ngaclient.ui.adapter.MainForumListAdapter;
import com.github.kevinjava.ngaclient.util.NgaLog;
import com.github.kevinjava.ngaclient.util.URLCreator;
import com.kevinjava.ngaclient.controller.NetRequestType;

import java.util.Observable;
import java.util.Observer;

public final class MainPageFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, Observer, LoadMoreListView.OnLoadMoreListener {
    private static final String KEY_CONTENT = "TestFragment:Content";
    private static final int DATA_UPDATE = 0;
    private static final int NOT_LOGIN = 1;
    private static final int NETWORK_ERROR = 2;
    Handler operatorHandler;

    public static MainPageFragment newInstance(int fatherIndex, int index) {
        Log.i("TestFragment", "newInstance");
        MainPageFragment fragment = new MainPageFragment();
        fragment.index = index;
        fragment.fatherIndex = fatherIndex;
        return fragment;
    }

    public int index;
    public int fatherIndex;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        NetworkManager.getInstance().getDatastore().addObserver(this);
    }

    public void setContent(int fatherIndex, int index) {
        this.index = index;
        this.fatherIndex = fatherIndex;
        ThreadData currentData = NetworkManager.getInstance().getDatastore().forumDatas.get(String.valueOf(URLCreator.getFid(fatherIndex, index)));
        if (currentData == null) {
            if (!refreshLayout.isRefreshing()) {
                TaskManager.getInstance().addTask(new NetworkTask(NetRequestType.ForumData, fatherIndex, index, page));
            }
            refreshLayout.setRefreshing(true);
            mainAdapter.setThreadData(null);
            mainAdapter.notifyDataSetChanged();
            NgaLog.i("TAG", "Current Data is null, index :" + index);
        } else {

            if (mainAdapter.getThreadData() != currentData) {
                NgaLog.i("TAG", "update with Current Data , index :" + index);
                mainAdapter.setThreadData(currentData);
            } else {
                NgaLog.i("TAG", "Current Data  == old data, index :" + index);
            }
            mainAdapter.notifyDataSetChanged();
        }
    }

    LoadMoreListView listView;
    MainForumListAdapter mainAdapter;
    SwipeRefreshLayout refreshLayout;
    int page = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        refreshLayout = new SwipeRefreshLayout(getActivity());
        refreshLayout.setOnRefreshListener(this);
        listView = new LoadMoreListView(getActivity());
        mainAdapter = new MainForumListAdapter(new ThreadData(), getActivity());
        listView.setAdapter(mainAdapter);
        listView.setOnItemClickListener(mainAdapter);
        listView.setOnLoadMoreListener(this);
        listView.setSelector(R.drawable.listview_selector);
        listView.setDrawSelectorOnTop(true);
        refreshLayout.addView(listView);
        refreshLayout.setColorScheme(android.R.color.holo_red_light, android.R.color.holo_green_light,
                android.R.color.holo_blue_bright, android.R.color.holo_orange_light);
        ThreadData currentData = NetworkManager.getInstance().getDatastore().forumDatas.get(String.valueOf(index));
        if (currentData == null) {
            if (index == 0 && !refreshLayout.isRefreshing()) {
                TaskManager.getInstance().addTask(new NetworkTask(NetRequestType.ForumData, fatherIndex, index, page));
                refreshLayout.setRefreshing(true);
            }
        }
        operatorHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                int fid = URLCreator.getFid(fatherIndex, index);
                switch (msg.what) {
                    case DATA_UPDATE:
                        HttpRequestBean bean = (HttpRequestBean) msg.obj;
                        if (bean.getFid() == fid) {
                            NgaLog.i("TAG", "got notification" + fid);
                            if (bean.getType() == NetRequestType.ForumDataRefresh) {
                                refreshLayout.setRefreshing(false);
                            } else if (bean.getType() == NetRequestType.ForumDataLoadMore) {
                                listView.onLoadMoreComplete();
                                page++;
                            } else {
                                refreshLayout.setRefreshing(false);
                            }
                            setContent(fatherIndex, index);
                        } else {
                            NgaLog.i("TAG", "got notification" + fid + ":" + bean.getFid());
                        }
                        break;
                    case NOT_LOGIN:
                        NotLoginTask task = (NotLoginTask) msg.obj;
                        if (task.getBean().getFid() == fid) {
                            NgaLog.i("TAG", "receive not login notification, pop up the dialog");
                            refreshLayout.setRefreshing(false);
                            task.setFragment(MainPageFragment.this);
                            TaskManager.getInstance().addTask(task);
                        }
                        break;
                    case NETWORK_ERROR:
                        Datastore.ErrorResponse response = (Datastore.ErrorResponse) msg.obj;
                        if (response.bean.getFid() == fid) {
                            if (response.bean.getType() == NetRequestType.ForumDataRefresh) {
                                refreshLayout.setRefreshing(false);
                            } else if (response.bean.getType() == NetRequestType.ForumDataLoadMore) {
                                listView.onLoadMoreComplete();
                            } else {
                                refreshLayout.setRefreshing(false);
                            }
                        }
                        break;
                }
            }
        };
        return refreshLayout;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRefresh() {
        NgaLog.i("TAG", "on Refresh");
        TaskManager.getInstance().addTask(new NetworkTask(NetRequestType.ForumDataRefresh, fatherIndex, index, 1));
        refreshLayout.setRefreshing(true);

    }

    @Override
    public void update(Observable observable, final Object data) {
        if (data instanceof HttpRequestBean) {
            operatorHandler.obtainMessage(DATA_UPDATE, data).sendToTarget();
        } else if (data instanceof NotLoginTask) {
            operatorHandler.obtainMessage(NOT_LOGIN, data).sendToTarget();
        } else if (data instanceof Datastore.ErrorResponse) {
            operatorHandler.obtainMessage(NETWORK_ERROR, data).sendToTarget();
        }

    }

    @Override
    public void onLoadMore() {
        NgaLog.i("TAG", "on load more");
        TaskManager.getInstance().addTask(new NetworkTask(NetRequestType.ForumDataLoadMore, fatherIndex, index, page + 1));
    }

    public void initFragment() {
        page = 1;
        refreshLayout.setRefreshing(false);
        listView.onLoadMoreComplete();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        NgaLog.i("TAG", "request code :" + requestCode);
        NgaLog.i("TAG", "resultCode code :" + resultCode);
        if (resultCode == 100) {
            if (data.getExtras().getBoolean("isLogIn")) {
                setContent(fatherIndex, index);
            }
        }
    }
}
