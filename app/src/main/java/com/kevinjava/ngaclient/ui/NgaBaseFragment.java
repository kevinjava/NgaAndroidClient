package com.kevinjava.ngaclient.ui;

import android.app.Fragment;

import com.kevinjava.ngaclient.listener.ViewControllIF;

public abstract class NgaBaseFragment extends Fragment {
	ViewControllIF controller;

	public NgaBaseFragment setController(ViewControllIF controller) {
		this.controller = controller;
		return this;
	}
	
}
