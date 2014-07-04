package com.kevinjava.ngaclient.factory;

import com.kevinjava.ngaclient.listener.ViewControllIF;
import com.github.kevinjava.ngaclient.model.ThreadData;
import com.kevinjava.ngaclient.ui.BaseActivity;
import com.kevinjava.ngaclient.ui.NgaBaseFragment;

public abstract class FragmentFactoryIF {
	BaseActivity baseActivity;
	ViewControllIF controller;
	public abstract NgaBaseFragment getSlideLeftFragment();
	public abstract NgaBaseFragment getSlideRightNotLoginFragment();
	public abstract NgaBaseFragment getMainForumFragment(ThreadData data);
	public FragmentFactoryIF(BaseActivity act, ViewControllIF controller) {
		this.baseActivity = act;
		this.controller = controller;
	}
	
	public ViewControllIF getController(){
		return controller;
	}
}
