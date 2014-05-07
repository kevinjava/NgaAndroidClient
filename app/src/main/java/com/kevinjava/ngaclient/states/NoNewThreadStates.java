package com.kevinjava.ngaclient.states;

import com.kevinjava.ngaclient.ui.AnimationTextView;

public class NoNewThreadStates extends ResultStates {

	@Override
	public void setBackgoundAndText(AnimationTextView textView) {
		textView.setBackgroundOrange();
		textView.setText("没有新主题!");
	}

}
