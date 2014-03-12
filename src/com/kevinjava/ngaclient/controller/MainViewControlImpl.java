package com.kevinjava.ngaclient.controller;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.kevinjava.ngaclient.NgaApp;
import com.kevinjava.ngaclient.R;
import com.kevinjava.ngaclient.factory.FragmentFactoryIF;
import com.kevinjava.ngaclient.model.GroupModel;
import com.kevinjava.ngaclient.model.HttpRequestBean;
import com.kevinjava.ngaclient.model.ThreadData;
import com.kevinjava.ngaclient.ui.BaseActivity;

public class MainViewControlImpl implements MainViewControlIF,
		OnNavigationListener {

	BaseActivity baseActivity;
	FragmentFactoryIF fragmentFactory;
	int index;
	String[] tabStrs;

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
				.getDrawable(R.drawable.action_bar_bg));
	}

	@Override
	public void switchForum(int index, int tabIndex) {
		final ArrayList<GroupModel> forums = NgaApp.groups;
		this.index = index;
		GroupModel currentGroup = forums.get(index);
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
		Log.e("test", data);
	}

	@Override
	public void update(HttpRequestBean bean, ForumDataModelIF dataModel) {
		final ThreadData data = dataModel.getPageData(bean.getType(),
				bean.getFid());
		new Handler(Looper.getMainLooper()).post(new Runnable() {
			public void run() {
				if (!baseActivity.isFinishing()) {
					FragmentTransaction t = baseActivity.getFragmentManager()
							.beginTransaction();
					t.replace(R.id.main_frame,
							fragmentFactory.getMainForumFragment(data));
					t.commit();
				}
			}
		});
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		fragmentFactory.getController().onTabChange(index, itemPosition);
		return false;
	}

}
