package com.kevinjava.ngaclient.controller;

import android.os.Bundle;

import com.kevinjava.ngaclient.factory.FragementFactoryImpl;
import com.kevinjava.ngaclient.factory.FragmentFactoryIF;
import com.kevinjava.ngaclient.listener.ViewControllIF;
import com.kevinjava.ngaclient.ui.BaseActivity;
import com.kevinjava.ngaclient.util.URLCreator;

public class Controller implements ViewControllIF {
	BaseActivity baseActivity;
	SlideLeftViewControlIF leftViewControlIF;
	SlideRightViewControlIF rightViewControlIF;
	MainViewControlIF mainViewControlIF;
	FragmentFactoryIF fragmentFactory;
	ForumDataModelIF forumDataModel;

	public Controller(BaseActivity act) {
		this.baseActivity = act;
		fragmentFactory = new FragementFactoryImpl(act, this);
		leftViewControlIF = new SlideLeftViewImpl(act, fragmentFactory);
		rightViewControlIF = new SlideRightViewImpl(act, fragmentFactory);
		mainViewControlIF = new MainViewControlImpl(act, fragmentFactory);
		forumDataModel = new ForumDataModel();
		forumDataModel.add(NetRequestType.ForumData, mainViewControlIF);
		forumDataModel.add(NetRequestType.RefrushForumData, mainViewControlIF);
		forumDataModel.add(NetRequestType.OnLoadMore, mainViewControlIF);
	}

	public void createView(Bundle savedInstanceState) {
		leftViewControlIF.createView(savedInstanceState);
		rightViewControlIF.createView(savedInstanceState);
		mainViewControlIF.createView(savedInstanceState);
	}

	@Override
	public void onForumChange(int index, int tabIndex) {
		mainViewControlIF.switchForum(index, tabIndex, forumDataModel);
	}

	@Override
	public void onTabChange(int index, int tabIndex) {
		boolean isChange = mainViewControlIF.onTabChange(index, tabIndex, forumDataModel);
		if(isChange){
			forumDataModel.ferchData(NetRequestType.ForumData, index, tabIndex, 1,
					URLCreator.getForumUrl(index, 1, tabIndex));
		}
	}

	@Override
	public void onRefreshPage(int index, int tabIndex, int page) {
		mainViewControlIF.onRefreshPage(index, tabIndex, page);
		forumDataModel.ferchData(NetRequestType.RefrushForumData, index, tabIndex, page, URLCreator.getForumUrl(index, page, tabIndex));
	}

	@Override
	public void onloadMore(int index, int tabIndex, int page) {
		mainViewControlIF.onloadMore(index, tabIndex, page);
		forumDataModel.ferchData(NetRequestType.OnLoadMore, index, tabIndex, page, URLCreator.getForumUrl(index, page, tabIndex));
	}

}
