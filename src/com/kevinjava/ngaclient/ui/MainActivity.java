package com.kevinjava.ngaclient.ui;

import android.os.Bundle;
import android.view.Menu;

import com.kevinjava.ngaclient.R;
import com.kevinjava.ngaclient.controller.Controller;

public class MainActivity extends BaseActivity {

	public MainActivity() {
		super(R.string.forum_total);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new Controller(this).createView(savedInstanceState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

}
