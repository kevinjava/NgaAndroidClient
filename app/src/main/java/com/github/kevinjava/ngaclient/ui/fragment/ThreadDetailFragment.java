package com.github.kevinjava.ngaclient.ui.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.kevinjava.ngaclient.NgaApp;
import com.github.kevinjava.ngaclient.R;
import com.github.kevinjava.ngaclient.customview.LoadMoreListView;
import com.github.kevinjava.ngaclient.manager.NetworkManager;
import com.github.kevinjava.ngaclient.model.ReplyPageInfo;
import com.github.kevinjava.ngaclient.model.SubjectData;
import com.github.kevinjava.ngaclient.model.UserInfo;
import com.github.kevinjava.ngaclient.util.ArticleListWebClient;
import com.github.kevinjava.ngaclient.util.ArticleUtil;
import com.github.kevinjava.ngaclient.util.NgaLog;
import com.github.kevinjava.ngaclient.util.StringUtil;
import com.github.kevinjava.ngaclient.util.URLCreator;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.HashMap;

/**
 * Created by kevliu on 7/1/14.
 */
public class ThreadDetailFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, LoadMoreListView.OnLoadMoreListener {

    LoadMoreListView listView;
    SimpleAdapter adapter;
    SwipeRefreshLayout refreshLayout;
    HashMap<Integer, String> htmls = new HashMap<Integer, String>();
    SubjectData data;
    int page = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        refreshLayout = new SwipeRefreshLayout(getActivity());
        refreshLayout.setOnRefreshListener(this);
        listView = new LoadMoreListView(getActivity());
        adapter = new SimpleAdapter();
        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(mainAdapter);
        listView.setOnLoadMoreListener(this);
//        listView.setSelector(R.drawable.listview_selector);
        listView.setDrawSelectorOnTop(true);
        refreshLayout.addView(listView);
        refreshLayout.setColorScheme(android.R.color.holo_red_light, android.R.color.holo_green_light,
                android.R.color.holo_blue_bright, android.R.color.holo_orange_light);

        return refreshLayout;
    }

    String tid;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tid = getActivity().getIntent().getExtras().getString("tid");
        NgaLog.i("TAG", "tid is : " + tid);
        refreshLayout.setRefreshing(true);
        NetworkManager.getInstance().asyncGetRequest(URLCreator.getSubjectUrl(Integer.parseInt(tid), 1), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
//
                data = ArticleUtil.parseSubjectPage(content);
                if (data == null) {
                    return;
                }
                refreshLayout.setRefreshing(false);
                NgaLog.i("TAG", data.get__R__ROWS() + "====" + data.get__R__ROWS_PAGE() + "====" + data.get__ROWS() + "====" + data.get__R__ROWS());
                if ((data.get__R__ROWS() < data.get__R__ROWS_PAGE()) || data.get__ROWS() == data.get__R__ROWS()) {
                    listView.disableFootView();
                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Throwable error, String content) {
                super.onFailure(error, content);
                refreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onLoadMore() {
        NetworkManager.getInstance().asyncGetRequest(URLCreator.getSubjectUrl(Integer.parseInt(tid), page + 1), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
//
                SubjectData newData = ArticleUtil.parseSubjectPage(content);
                if (newData == null) {
                    return;
                }
                ++page;

                // merge the list
                data.set__T(newData.get__T());
                data.get__R().addAll(newData.get__R());
                data.get__U().addAll(newData.get__U());
                data.set__ROWS(newData.get__ROWS());
                data.set__R__ROWS(data.get__R().size());
                data.set__R__ROWS_PAGE(data.get__R__ROWS_PAGE());
                NgaLog.i("TAG", newData.get__R__ROWS() + "====" + newData.get__R__ROWS_PAGE() + "====" + newData.get__ROWS() + "====" + data.get__R__ROWS());
                if ((newData.get__R__ROWS() < newData.get__R__ROWS_PAGE()) || data.get__ROWS() == data.get__R__ROWS()) {
                    listView.disableFootView();
                }
                listView.onLoadMoreComplete();
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Throwable error, String content) {
                super.onFailure(error, content);
                listView.onLoadMoreComplete();
            }
        });
    }

    @Override
    public void onRefresh() {
        NetworkManager.getInstance().asyncGetRequest(URLCreator.getSubjectUrl(Integer.parseInt(tid), 1), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                data = ArticleUtil.parseSubjectPage(content);
                if (data == null) {
                    return;
                }

                page = 1;

                if ((data.get__R__ROWS() < data.get__R__ROWS_PAGE()) || data.get__ROWS() == data.get__R__ROWS()) {
                    listView.disableFootView();
                }

                refreshLayout.setRefreshing(false);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Throwable error, String content) {
                super.onFailure(error, content);
                refreshLayout.setRefreshing(false);
            }
        });
    }

    private void handleContentTV(final WebView contentTV, final ReplyPageInfo row, int bgColor, int fgColor) {

        contentTV.setBackgroundColor(0);
        contentTV.setFocusableInTouchMode(false);
        contentTV.setFocusable(false);
//        if(Build.VERSION > Version.)
//        {
//
//            contentTV.setLongClickable(false);
//        }

		/*bgColor = bgColor & 0xffffff;
        final String bgcolorStr = String.format("%06x",bgColor);

		int htmlfgColor = fgColor & 0xffffff;
		final String fgColorStr = String.format("%06x",htmlfgColor);
		if(row.getContent()== null){
			row.setContent(row.getSubject());
			row.setSubject(null);
		}*/

        boolean showImage = true;

        WebSettings setting = contentTV.getSettings();
        //setting.setBlockNetworkImage(!showImage);
        // the network image url already replaced by local icon. this should not be called and
        // webview will not work properly in android 4.4.
        setting.setDefaultFontSize(16);
        setting.setJavaScriptEnabled(false);
        setting.setUseWideViewPort(true);
        setting.setLoadWithOverviewMode(true);
        contentTV.setWebViewClient(new ArticleListWebClient(getActivity()));


//        contentTV.setTag(row.get__R().);


		/*
        ForumTagDecodTask task= new ForumTagDecodTask(row, showImage, fgColorStr, bgcolorStr);
		if(ActivityUtil.isGreaterThan_2_3_3()){
			excuteOnExcutor(task,contentTV);
		}else{
			task.execute(contentTV);
		}*/

    }


    class SimpleAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return data == null ? 0 : data.get__R__ROWS();
        }

        @Override
        public Object getItem(int position) {
            return data.get__R().get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            if (convertView != null) {
                view = convertView;
            } else {
                view = View.inflate(getActivity(), R.layout.thread_detail_layout, null);
            }
            ReplyPageInfo info = data.get__R().get(position);
            Object obj = view.getTag();
            ListItemHolder holder;
            if (obj != null) {
                holder = (ListItemHolder) obj;
                WebViewAsyncTask task = holder.getTask();
                if (task.info.getLou() != info.getLou()) {
                    task.cancel(true);
                    holder.getWebView().loadUrl("about:blank");
                    WebViewAsyncTask newTask = new WebViewAsyncTask(holder.getWebView(), holder.getIcon(), info);
                    holder.setTask(newTask);
                    newTask.execute();
                    view.setTag(holder);
                } else {
                    NgaLog.i("TAG", "no need to refresh webview");
                }
            } else {
                holder = new ListItemHolder(view);

                WebViewAsyncTask newTask = new WebViewAsyncTask(holder.getWebView(), holder.getIcon(), info);
                holder.setTask(newTask);
                newTask.execute();
                view.setTag(holder);
            }
            holder.update();

            return view;
        }


    }

    public UserInfo getUserInfo(String uid) {
        for (UserInfo info : data.get__U()) {
            if (info.getUid().equals(uid)) {
                return info;
            }
        }
        return null;
    }

    class ListItemHolder {
        private WebView webView;
        private ImageView icon;
        private TextView nickName;
        private TextView lou;
        private TextView postDate;
        private WebViewAsyncTask task;

        public ListItemHolder(View view) {
            webView = (WebView) view.findViewById(R.id.webview_content);
            icon = (ImageView) view.findViewById(R.id.user_icon);
            nickName = (TextView) view.findViewById(R.id.user_name);
            lou = (TextView) view.findViewById(R.id.lou);
            postDate = (TextView) view.findViewById(R.id.post_date);
            handleContentTV(webView, null, 0, 0);
        }

        public void update() {
            ReplyPageInfo info = task.info;

            lou.setText(info.getLou() + "楼");
            postDate.setText(info.getPostdate());
            UserInfo userInfo = task.userInfo;
            if (userInfo != null) {
                nickName.setText(userInfo.getUsername());
            }
            if (userInfo != null && !TextUtils.isEmpty(userInfo.getAvatar()) && userInfo.getAvatar().startsWith("http")) {
                Object tag = icon.getTag();
                Object louTag = lou.getTag();
                if (tag == null || !((String) tag).equals(userInfo.getAvatar()) || louTag == null || ((Integer) louTag).intValue() != info.getLou()) {
                    NgaLog.i("TAG", userInfo.getUsername() + "------>" + userInfo.getAvatar() + "---->" + info.getLou());
                    icon.setImageResource(R.drawable.default_user_avatar);
                    icon.setTag(userInfo.getAvatar());
                    lou.setTag(info.getLou());
                    NgaApp.imageCache.get(userInfo.getAvatar(), icon);
                }

            } else {
                icon.setImageResource(R.drawable.default_user_avatar);
            }

        }

        public WebViewAsyncTask getTask() {
            return task;
        }

        public void setTask(WebViewAsyncTask task) {
            this.task = task;
        }

        public ImageView getIcon() {
            return icon;
        }

        public void setIcon(ImageView icon) {
            this.icon = icon;
        }

        public TextView getNickName() {
            return nickName;
        }

        public void setNickName(TextView nickName) {
            this.nickName = nickName;
        }

        public TextView getLou() {
            return lou;
        }

        public void setLou(TextView lou) {
            this.lou = lou;
        }

        public TextView getPostDate() {
            return postDate;
        }

        public void setPostDate(TextView postDate) {
            this.postDate = postDate;
        }

        public WebView getWebView() {
            return webView;
        }
    }

    class WebViewAsyncTask extends AsyncTask {

        public WebView webView;
        public ReplyPageInfo info;
        public UserInfo userInfo;
        public ImageView icon;

        public WebViewAsyncTask(WebView webView, ImageView icon, ReplyPageInfo info) {
            this.webView = webView;
            this.info = info;
            this.userInfo = getUserInfo(info.getAuthorid() + "");
            this.icon = icon;
        }

        @Override
        protected String doInBackground(Object[] params) {
            String html = null;
            if (htmls.size() > info.getLou()) {
                html = htmls.get(info.getLou());
            }
            if (html == null) {
                html = StringUtil.convertToHtmlText(data, info, true, "000000", "ffffff");
                htmls.put(info.getLou(), html);
            }

            NgaLog.i("TAG", html);
            return html;
        }

        @Override
        protected void onPostExecute(Object o) {

            webView.loadDataWithBaseURL(null, (String) o, "text/html", "utf-8", null);
        }
    }
}
