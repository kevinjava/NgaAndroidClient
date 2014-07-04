package com.github.kevinjava.ngaclient.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.kevinjava.ngaclient.manager.NetworkManager;
import com.github.kevinjava.ngaclient.ui.adapter.MainForumAdapter;
import com.github.kevinjava.ngaclient.R;
import com.viewpagerindicator.TabPageIndicator;

import net.simonvt.menudrawer.Position;

/**
 * Created by kevliu on 6/25/14.
 */
/**
 * A placeholder fragment containing a simple view.
 */
public class MainContentFragment extends Fragment implements ViewPager.OnPageChangeListener, TabPageIndicator.OnTabReselectedListener {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private MainForumAdapter adapter;
    private ViewPager pager;
    private TabPageIndicator indicator;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static MainContentFragment newInstance() {
        MainContentFragment fragment = new MainContentFragment();
        return fragment;
    }

    public MainContentFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        adapter = new MainForumAdapter(getActivity().getSupportFragmentManager());

        pager = (ViewPager) rootView.findViewById(R.id.pager);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(100);
        indicator = (TabPageIndicator) rootView.findViewById(R.id.indicator);
        indicator.setViewPager(pager);
        indicator.setOnPageChangeListener(this);
        indicator.setOnTabReselectedListener(this);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public void update(int positon){
        adapter.update(positon);
    }

    public void notifyChange(){
        NetworkManager.getInstance().getDatastore().forumDatas.clear();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
                indicator.notifyDataSetChanged();
                indicator.setCurrentItem(0);
                adapter.initAllPageInfo();
                adapter.update(0);
            }
        },300);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        update(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}