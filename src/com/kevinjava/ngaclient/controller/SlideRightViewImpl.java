package com.kevinjava.ngaclient.controller;

import android.app.FragmentTransaction;
import android.os.Bundle;

import com.kevinjava.ngaclient.R;
import com.kevinjava.ngaclient.constant.ToastType;
import com.kevinjava.ngaclient.factory.FragmentFactoryIF;
import com.kevinjava.ngaclient.model.HttpRequestBean;
import com.kevinjava.ngaclient.ui.BaseActivity;

public class SlideRightViewImpl implements SlideRightViewControlIF{
	BaseActivity baseActivity;

	FragmentFactoryIF fragmentFactory;

	public SlideRightViewImpl(BaseActivity act, FragmentFactoryIF fragmentFactory) {
		this.baseActivity = act;
		this.fragmentFactory = fragmentFactory;
	}
	@Override
	public void createView(Bundle savedInstanceState) {
		if (savedInstanceState == null) {
			FragmentTransaction t = baseActivity.getFragmentManager().beginTransaction();
			t.replace(R.id.menu_frame_two, fragmentFactory.getSlideRightNotLoginFragment());
			t.commit();
		}
	}

	@Override
	public void login(String accound, String psw) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void frechUserInfo() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void update(HttpRequestBean bean, ForumDataModelIF dataModel) {
		
	}
	@Override
	public void notifyToast(ToastType type) {
		// TODO Auto-generated method stub
		
	}

}
