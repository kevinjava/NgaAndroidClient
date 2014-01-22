package com.kevinjava.ngaclient.ui;

import com.kevinjava.ngaclient.R;
import com.kevinjava.ngaclient.R.layout;
import com.kevinjava.ngaclient.R.menu;
import com.kevinjava.ngaclient.R.string;

import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends BaseActivity {

	public MainActivity() {
		super(R.string.app_name);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
