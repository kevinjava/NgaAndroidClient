package com.kevinjava.ngaclient.controller;

import com.kevinjava.ngaclient.listener.ForumObserver;

import android.os.Bundle;

public interface SlideViewControlIF extends ForumObserver {
	void createView(Bundle savedInstanceState);
}
