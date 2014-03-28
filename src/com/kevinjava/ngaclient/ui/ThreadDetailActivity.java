package com.kevinjava.ngaclient.ui;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.ViewDragHelper;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import android.graphics.Color;
import android.hardware.input.InputManager;
import android.inputmethodservice.InputMethodService;
import android.os.Bundle;
import android.text.Html;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Layout.Alignment;
import android.text.style.AlignmentSpan;
import android.view.WindowManager;
import android.widget.TextView;

import com.kevinjava.ngaclient.R;
import com.kevinjava.ngaclient.model.ReplyPageInfo;
import com.kevinjava.ngaclient.model.SubjectData;
import com.kevinjava.ngaclient.network.NetworkManager;
import com.kevinjava.ngaclient.util.ArticleUtil;
import com.kevinjava.ngaclient.util.NgaLog;
import com.kevinjava.ngaclient.util.StringUtil;
import com.kevinjava.ngaclient.util.URLCreator;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class ThreadDetailActivity extends SwipeBackActivity {
	private static final String TAG = ThreadDetailActivity.class.getSimpleName();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.thread_detail_layout);
		String tid = getIntent().getExtras().getString("tid");
		NgaLog.i(TAG, "tid is : " + tid);
		NetworkManager.getInstance().asyncGetRequest(URLCreator.getSubjectUrl(Integer.parseInt(tid), 1), new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(String content) {
				super.onSuccess(content);
//				
//				SubjectData data = ArticleUtil.parseSubjectPage(content);
//				for (ReplyPageInfo info : data.get__R()) {
//					if(info.getContent() != null){
//						((TextView)findViewById(R.id.content)).setText(Html.fromHtml(StringUtil.decodeForumTag(info.getContent(), true)));
//						break;
//					}
//				}
				String string = "测试居中";
				SpannableStringBuilder style = new SpannableStringBuilder(string);
				style.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, 3, SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
				((TextView)findViewById(R.id.content)).setBackgroundColor(Color.GREEN);
				((TextView)findViewById(R.id.content)).setText(style);
			}
		});
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
		
	}
}
