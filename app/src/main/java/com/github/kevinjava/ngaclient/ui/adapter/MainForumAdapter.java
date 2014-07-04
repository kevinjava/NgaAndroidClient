package com.github.kevinjava.ngaclient.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.github.kevinjava.ngaclient.NgaApp;
import com.github.kevinjava.ngaclient.ui.fragment.MainPageFragment;

import java.util.ArrayList;

/**
 * Created by kevliu on 6/25/14.
 */

public class MainForumAdapter extends FragmentPagerAdapter {
    ArrayList<String> listTAg = new ArrayList<String>();
    FragmentManager fm;

    public MainForumAdapter(FragmentManager fm) {
        super(fm);
        this.fm = fm;
    }

    @Override
    public Fragment getItem(int position) {

        return MainPageFragment.newInstance(NgaApp.getCurrentIndex(), position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return NgaApp.getCurrentForum().forums.get(position).name;
    }

    @Override
    public int getCount() {
        return NgaApp.getCurrentForum().forums.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        String tagName = makeFragmentName(container.getId(), getItemId(position));
        if (!listTAg.contains(tagName)) {
            listTAg.add(tagName);
            Log.i("tag", tagName);
        }
        return super.instantiateItem(container, position);
    }

    private String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }

    public void update(int item) {
        Fragment fragment = fm.findFragmentByTag(listTAg.get(item));
        if (fragment != null) {
            ((MainPageFragment) fragment).setContent(NgaApp.getCurrentIndex(), item);
        } else {
            Log.i("TAG", "fragment is null");
        }

    }

    public void initAllPageInfo() {
        for (String tag : listTAg) {
            Fragment fragment = fm.findFragmentByTag(tag);
            if (fragment != null) {
                ((MainPageFragment) fragment).initFragment();
            }
        }
    }

}