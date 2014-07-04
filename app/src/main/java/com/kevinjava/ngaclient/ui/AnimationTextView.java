package com.kevinjava.ngaclient.ui;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.github.kevinjava.ngaclient.R;

public class AnimationTextView extends LinearLayout {
	Scroller mScroller;
	TextView toAstTextView;
	boolean isScrolling;

	public AnimationTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		addTextView(context);
	}

	public void addTextView(Context context) {
		mScroller = new Scroller(getContext(), new DecelerateInterpolator());
		toAstTextView = new TextView(context);
		toAstTextView.setTextColor(Color.WHITE);
		toAstTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		LinearLayout.LayoutParams params = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		toAstTextView.setGravity(Gravity.CENTER);
		toAstTextView.setBackgroundResource(R.color.ToastGreen);
		int padding = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 8, getResources()
						.getDisplayMetrics());
		toAstTextView.setPadding(padding, padding, padding, padding);
		addView(toAstTextView, params);
	}

	public AnimationTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		addTextView(context);
	}

	public AnimationTextView(Context context) {
		super(context);
		addTextView(context);
	}

	public void startScroll() {
		isScrolling = true;
		mScroller.startScroll(0, 0, 0, getHeight(), 1000);
		invalidate();
	}
	
	public void resetTextView(){
		toAstTextView.setVisibility(View.VISIBLE);
	}
	
	public void setBackgroundRed(){
		toAstTextView.setBackgroundResource(R.color.ToastRed);
	}
	
	public void setBackgroundGreen(){
		toAstTextView.setBackgroundResource(R.color.ToastGreen);
	}
	
	public void setBackgroundOrange(){
		toAstTextView.setBackgroundResource(R.color.ToastOrange);
	}
	
	public void resetView() {
		mScroller.startScroll(0, 0, 0, -getHeight(), 1000);
		invalidate();
	}

	public void setText(String str) {
		toAstTextView.setText(str);
	}

	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			scrollTo(0, mScroller.getCurrY());
			postInvalidate();
		}
		if (mScroller.isFinished() && isScrolling) {
			isScrolling = false;
//			resetView();
			toAstTextView.setVisibility(View.INVISIBLE);
//			postInvalidate();
			scrollBy(0, -getHeight());
		}
		super.computeScroll();
	}

}
