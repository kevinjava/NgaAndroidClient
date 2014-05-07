package com.kevinjava.ngaclient.states;

import com.kevinjava.ngaclient.ui.AnimationTextView;

import android.view.View;

public abstract class ResultStates {
	public void showToast(View view){
		final AnimationTextView toastView = (AnimationTextView) view;
		toastView.setVisibility(View.VISIBLE);
		toastView.resetTextView();
		setBackgoundAndText(toastView);
		toastView.postDelayed(new Runnable() {

			@Override
			public void run() {
				toastView.startScroll();
			}
		}, 800);

	};
	
	public abstract void setBackgoundAndText(AnimationTextView textView);
	
}
