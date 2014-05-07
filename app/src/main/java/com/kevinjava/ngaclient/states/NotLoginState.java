package com.kevinjava.ngaclient.states;

import com.kevinjava.ngaclient.ui.AnimationTextView;

public class NotLoginState extends ResultStates {

	@Override
	public void setBackgoundAndText(AnimationTextView textView) {
		textView.setBackgroundRed();
		textView.setText("没有登录");
	}

}
