package com.github.kevinjava.ngaclient.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.webkit.WebView;

import com.github.kevinjava.ngaclient.R;
import com.github.kevinjava.ngaclient.model.ReplyPageInfo;
import com.github.kevinjava.ngaclient.ui.fragment.ThreadDetailFragment;
import com.github.kevinjava.ngaclient.util.NgaLog;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.ViewDragHelper;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class ThreadDetailActivity extends SwipeBackActivity {
    private static final String TAG = ThreadDetailActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);


        final android.app.ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setBackgroundDrawable(getResources()
                .getDrawable(R.color.abs__holo_blue_light));


        SwipeBackLayout mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
        mSwipeBackLayout.addSwipeListener(new SwipeBackLayout.SwipeListener() {
            @Override
            public void onScrollStateChange(int state, float scrollPercent) {

            }

            @Override
            public void onEdgeTouch(int edgeFlag) {
                NgaLog.i(TAG, "onEdgeTouch");
            }

            @Override
            public void onScrollOverThreshold() {
                NgaLog.i(TAG, "onScrollOverThreshold");
            }
        });


        ThreadDetailFragment detailFragment = new ThreadDetailFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.detail, detailFragment)
                .commit();
    }


    public void update(final WebView contentTV, final ReplyPageInfo row) {
        NgaLog.i("TAG", "update " + row.getLou() + " Lou");


    }


}
