package com.kevinjava.ngaclient.controller;

import android.app.FragmentTransaction;
import android.os.Bundle;

import com.kevinjava.ngaclient.R;
import com.kevinjava.ngaclient.factory.FragmentFactoryIF;
import com.kevinjava.ngaclient.model.HttpRequestBean;
import com.kevinjava.ngaclient.ui.BaseActivity;

public class SlideLeftViewImpl implements SlideLeftViewControlIF {
	BaseActivity baseActivity;
	FragmentFactoryIF fragmentFactory;

	public SlideLeftViewImpl(BaseActivity act, FragmentFactoryIF fragmentFactory) {
		this.baseActivity = act;
		this.fragmentFactory = fragmentFactory;
	}

	@Override
	public void clickForumItem(int index) {

	}

	@Override
	public void createView(Bundle savedInstanceState) {
		if (savedInstanceState == null) {
			FragmentTransaction t = baseActivity.getFragmentManager().beginTransaction();
			t.replace(R.id.menu_frame, fragmentFactory.getSlideLeftFragment());
			t.commit();
		}
	}

	@Override
	public void update(HttpRequestBean bean, ForumDataModelIF dataModel) {
		
	}

}
