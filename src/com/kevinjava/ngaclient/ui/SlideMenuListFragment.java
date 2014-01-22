package com.kevinjava.ngaclient.ui;

import java.util.ArrayList;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kevinjava.ngaclient.NgaApp;
import com.kevinjava.ngaclient.R;
import com.kevinjava.ngaclient.model.GroupModel;

public class SlideMenuListFragment extends Fragment {
	private ArrayList<GroupModel> groups;
	
	public SlideMenuListFragment() {
		this.groups = NgaApp.groups;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.slide_left, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	public class SampleAdapter extends ArrayAdapter<String> {

		public SampleAdapter() {
			super(getActivity(), 0);
		}

		@Override
		public int getCount() {
			return groups.size();
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			TextView tv;
			if (convertView == null) {
				tv = new TextView(getActivity());
			} else {
				tv = (TextView) convertView;
			}
			tv.setText(groups.get(position).name);
			return tv;
		}

	}
}
