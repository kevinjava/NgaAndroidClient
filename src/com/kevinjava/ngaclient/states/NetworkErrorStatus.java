package com.kevinjava.ngaclient.states;

import com.kevinjava.ngaclient.ui.AnimationTextView;

public class NetworkErrorStatus extends ResultStates {

	@Override
	public void setBackgoundAndText(AnimationTextView textView) {
		textView.setBackgroundRed();
		textView.setText("网络错误!");
	}

}
