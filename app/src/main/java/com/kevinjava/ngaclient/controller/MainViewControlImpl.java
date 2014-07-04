package com.kevinjava.ngaclient.controller;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ArrayAdapter;

import com.github.kevinjava.ngaclient.NgaApp;
import com.github.kevinjava.ngaclient.R;
import com.kevinjava.ngaclient.factory.FragmentFactoryIF;
import com.github.kevinjava.ngaclient.model.GroupModel;
import com.github.kevinjava.ngaclient.model.HttpRequestBean;
import com.github.kevinjava.ngaclient.model.ThreadData;
import com.kevinjava.ngaclient.states.ResultStates;
import com.kevinjava.ngaclient.ui.BaseActivity;
import com.kevinjava.ngaclient.ui.MainForumFragement;
import com.kevinjava.ngaclient.ui.NgaBaseFragment;
import com.github.kevinjava.ngaclient.util.NgaLog;

public class MainViewControlImpl implements MainViewControlIF,
		OnNavigationListener {
	private static final String TAG = MainViewControlImpl.class.getSimpleName();
	BaseActivity baseActivity;
	FragmentFactoryIF fragmentFactory;
	int index = -1;
	int page = 1;
	int tabIndex = -1;
	String[] tabStrs;
	NgaBaseFragment mainForumFragement;

	public MainViewControlImpl(BaseActivity act,
			FragmentFactoryIF fragmentFactory) {
		this.baseActivity = act;
		this.fragmentFactory = fragmentFactory;
	}

	@Override
	public void goToDetailPage() {

	}

	@Override
	public void createView(Bundle savedInstanceState) {
		baseActivity.setContentView(R.layout.activity_main);
		final ActionBar actionBar = baseActivity.getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setBackgroundDrawable(baseActivity.getResources()
				.getDrawable(R.color.abs__holo_blue_light));
		FragmentTransaction t = baseActivity.getFragmentManager()
				.beginTransaction();
		mainForumFragement = fragmentFactory
				.getMainForumFragment(new ThreadData());
		t.replace(R.id.main_frame, mainForumFragement);
		t.commit();
	}

	@Override
	public void switchForum(int index, int tabIndex,
			ForumDataModelIF forumDataModelIF) {
		if (this.index == index) {
			return;
		}
		final ArrayList<GroupModel> forums = NgaApp.groups;
		this.index = index;
		this.tabIndex = -1;
		GroupModel currentGroup = forums.get(index);
		baseActivity.setTitle(currentGroup.name);
		tabStrs = new String[currentGroup.forums.size()];
		for (int i = 0; i < currentGroup.forums.size(); i++) {
			tabStrs[i] = currentGroup.forums.get(i).name;
		}
		baseActivity.getActionBar().setListNavigationCallbacks(
				new ArrayAdapter<String>(baseActivity,
						R.layout.action_list_item, tabStrs), this);
		baseActivity.showContent();
	}

	@Override
	public void handlerForumData(String data) {
		NgaLog.e("test", data);
	}

	@Override
	public void update(final HttpRequestBean bean) {
//		final ThreadData data = dataModel.getPageData(bean.getType(),
//				bean.getFid());
//		new Handler(Looper.getMainLooper()).post(new Runnable() {
//			public void run() {
//				if (!baseActivity.isFinishing()) {
//					if (mainForumFragement == null) {
//						FragmentTransaction t = baseActivity
//								.getFragmentManager().beginTransaction();
//						mainForumFragement = fragmentFactory
//								.getMainForumFragment(data);
//						((MainForumFragement) mainForumFragement).setIndex(
//								index, tabIndex, page);
//						t.replace(R.id.main_frame, mainForumFragement);
//						t.commit();
//					} else {
//						((MainForumFragement) mainForumFragement)
//								.enableFootView();
//						((MainForumFragement) mainForumFragement).setIndex(
//								index, tabIndex, page);
//						((MainForumFragement) mainForumFragement)
//								.setDataChange(data, bean);
//					}
//				}
//			}
//		});
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		fragmentFactory.getController().onTabChange(index, itemPosition);
		return false;
	}

	@Override
	public void onRefreshPage(int index, int tabIndex, int page) {
		this.index = index;
		this.page = page;
		this.tabIndex = tabIndex;
	}

	@Override
	public void onloadMore(int index, int tabIndex, int page) {
		this.index = index;
		this.page = page;
		this.tabIndex = tabIndex;
	}

	@Override
	public boolean onTabChange(int index, int tabIndex,
			ForumDataModelIF forumDataModelIF) {
		boolean isChange = !(this.tabIndex == tabIndex && this.index == index);
		this.tabIndex = tabIndex;
		this.index = index;
		if (isChange) {
			((MainForumFragement) mainForumFragement).setRefrush();
		}
		return isChange;
	}


}
