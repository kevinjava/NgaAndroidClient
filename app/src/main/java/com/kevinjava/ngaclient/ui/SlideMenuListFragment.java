package com.kevinjava.ngaclient.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.kevinjava.ngaclient.R;
import com.kevinjava.ngaclient.constant.ForumConstant;

public class SlideMenuListFragment extends NgaBaseFragment {
	ForumLayout layout;

	public SlideMenuListFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.slide_left, null);
		layout = new ForumLayout();
		layout.main = (Button) view.findViewById(R.id.btn1);
		layout.history = (Button) view.findViewById(R.id.btn2);
		layout.job = (Button) view.findViewById(R.id.btn3);
		layout.dialobe = (Button) view.findViewById(R.id.btn4);
		layout.adventue = (Button) view.findViewById(R.id.btn5);
		layout.game = (Button) view.findViewById(R.id.btn6);
		layout.other = (Button) view.findViewById(R.id.btn7);
		layout.net = (Button) view.findViewById(R.id.btn8);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		layout.main.setOnClickListener(btnCallback);
		layout.history.setOnClickListener(btnCallback);
		layout.job.setOnClickListener(btnCallback);
		layout.dialobe.setOnClickListener(btnCallback);
		layout.adventue.setOnClickListener(btnCallback);
		layout.game.setOnClickListener(btnCallback);
		layout.other.setOnClickListener(btnCallback);
		layout.net.setOnClickListener(btnCallback);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		drawPressBackgroud(0);
		handlerClick(0);
	}

	class ForumLayout {
		Button main;
		Button history;
		Button job;
		Button dialobe;
		Button adventue;
		Button net;
		Button game;
		Button other;

	}

	OnClickListener btnCallback = new OnClickListener() {

		@Override
		public void onClick(View v) {
			int index = 0;
			switch (v.getId()) {
			case R.id.btn1:
				index = ForumConstant.MAIN;
				break;
			case R.id.btn2:
				index = ForumConstant.HISTORY;
				break;
			case R.id.btn3:
				index = ForumConstant.JOB;
				break;
			case R.id.btn4:
				index = ForumConstant.DIALOBE;
				break;
			case R.id.btn5:
				index = ForumConstant.ADVENTURE;
				break;
			case R.id.btn6:
				index = ForumConstant.GAME;
				break;
			case R.id.btn7:
				index = ForumConstant.OTHER;
				break;
			case R.id.btn8:
				index = ForumConstant.NET;
				break;
			default:
				break;
			}
			drawPressBackgroud(index);
			handlerClick(index);
		}
	};

	private void drawPressBackgroud(int index) {
		layout.main.setBackgroundResource(R.drawable.btn_drawer_menu);
		layout.history.setBackgroundResource(R.drawable.btn_drawer_menu);
		layout.job.setBackgroundResource(R.drawable.btn_drawer_menu);
		layout.dialobe.setBackgroundResource(R.drawable.btn_drawer_menu);
		layout.adventue.setBackgroundResource(R.drawable.btn_drawer_menu);
		layout.game.setBackgroundResource(R.drawable.btn_drawer_menu);
		layout.other.setBackgroundResource(R.drawable.btn_drawer_menu);
		layout.net.setBackgroundResource(R.drawable.btn_drawer_menu);
		switch (index) {
		case ForumConstant.MAIN:
			layout.main.setBackgroundResource(R.color.ics_blue_semi);
			break;
		case ForumConstant.HISTORY:
			layout.history.setBackgroundResource(R.color.ics_blue_semi);
			break;
		case ForumConstant.JOB:
			layout.job.setBackgroundResource(R.color.ics_blue_semi);
			break;
		case ForumConstant.DIALOBE:
			layout.dialobe.setBackgroundResource(R.color.ics_blue_semi);
			break;
		case ForumConstant.ADVENTURE:
			layout.adventue.setBackgroundResource(R.color.ics_blue_semi);
			break;
		case ForumConstant.GAME:
			layout.game.setBackgroundResource(R.color.ics_blue_semi);
			break;
		case ForumConstant.OTHER:
			layout.other.setBackgroundResource(R.color.ics_blue_semi);
			break;
		case ForumConstant.NET:
			layout.net.setBackgroundResource(R.color.ics_blue_semi);
			break;

		default:
			break;
		}
	}

	private void handlerClick(int index) {
		controller.onForumChange(index, 0);
	}

	
}
