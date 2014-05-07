package com.kevinjava.ngaclient.states;

import android.view.View;
import android.widget.Toast;

import com.kevinjava.ngaclient.NgaApp;
import com.kevinjava.ngaclient.ui.AnimationTextView;

public class LoadMoreSuccessStates extends ResultStates {
	int count ;
	public LoadMoreSuccessStates(int count) {
		this.count = count;
	}
	
	@Override
	public void showToast(View view) {
		Toast.makeText(NgaApp.getContext(), "新加载了 " + count +" 条主题", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void setBackgoundAndText(AnimationTextView textView) {
		
	}
}
