package com.kevinjava.ngaclient.states;

import com.kevinjava.ngaclient.ui.AnimationTextView;

public class RefreshDataSuccessStates extends ResultStates {
	int count;

	public RefreshDataSuccessStates(int count) {
		this.count = count;
	}

	@Override
	public void setBackgoundAndText(AnimationTextView textView) {
		textView.setBackgroundGreen();
		textView.setText("新加载" + count + "条主题!");
		
	}

}
