package com.kevinjava.ngaclient.factory;

import com.kevinjava.ngaclient.listener.ViewControllIF;
import com.kevinjava.ngaclient.model.ThreadData;
import com.kevinjava.ngaclient.ui.BaseActivity;
import com.kevinjava.ngaclient.ui.MainForumFragement;
import com.kevinjava.ngaclient.ui.NgaBaseFragment;
import com.kevinjava.ngaclient.ui.SlideMenuListFragment;
import com.kevinjava.ngaclient.ui.SlideMenuRightNotLoginFragment;

public class FragementFactoryImpl extends FragmentFactoryIF {
	
	public FragementFactoryImpl(BaseActivity act, ViewControllIF controller) {
		super(act, controller);
	}
	
	@Override
	public NgaBaseFragment getSlideLeftFragment() {
		return new SlideMenuListFragment().setController(controller);
	}

	@Override
	public NgaBaseFragment getSlideRightNotLoginFragment() {
		return new SlideMenuRightNotLoginFragment().setController(controller);
	}

	@Override
	public NgaBaseFragment getMainForumFragment(ThreadData data) {
		return new MainForumFragement().setThreadData(data).setController(controller);
	}

	
}
