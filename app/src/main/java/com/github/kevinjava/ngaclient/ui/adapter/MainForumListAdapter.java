package com.github.kevinjava.ngaclient.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.kevinjava.ngaclient.R;
import com.github.kevinjava.ngaclient.model.ForumDataBean;
import com.github.kevinjava.ngaclient.model.ThreadData;
import com.github.kevinjava.ngaclient.ui.ThreadDetailActivity;
import com.github.kevinjava.ngaclient.util.TextViewColorUtil;

import java.util.HashMap;

public class MainForumListAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {

    ThreadData data;
    Context mContext;

    HashMap<String, SpannableStringBuilder> subjectSpannables = new HashMap<String, SpannableStringBuilder>();

    public MainForumListAdapter(ThreadData data, Context context) {
        this.data = data;
        this.mContext = context;
    }

    public void setThreadData(ThreadData data) {
        this.data = data;
    }

    public ThreadData getThreadData() {
        return this.data;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.getRowNum();
    }

    @Override
    public ForumDataBean getItem(int arg0) {
        return data.getRowList().get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(int index, View currentView, ViewGroup arg2) {
        MainItem item;
        if (currentView == null) {
            currentView = View.inflate(mContext, R.layout.main_list_item, null);
            item = new MainItem(currentView);
            currentView.setTag(item);
        } else {
            item = (MainItem) currentView.getTag();
        }

        item.setData(getItem(index));

        return currentView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(mContext, ThreadDetailActivity.class);
        String realId = "0".equals(data.getRowList().get(position).getQuote_from()) ? data.getRowList().get(position).getTid() : data.getRowList().get(position).getQuote_from();
        intent.putExtra("tid", realId);
        mContext.startActivity(intent);
    }

    class MainItem {
        ImageView userIcon;
        TextView userName;
        TextView subject;
        TextView publishTime;
        TextView replyNum;
        TextView replyName;

        public MainItem(View view) {
            userIcon = (ImageView) view.findViewById(R.id.user_icon);
            userName = (TextView) view.findViewById(R.id.user_name);
            publishTime = (TextView) view.findViewById(R.id.pushlish_time);
            replyNum = (TextView) view.findViewById(R.id.reply_num);
            replyName = (TextView) view.findViewById(R.id.last_reply_name);
            subject = (TextView) view.findViewById(R.id.forum_subject);
        }

        public void setData(ForumDataBean bean) {
            userName.setText(bean.getAuthor());
            publishTime.setText(TextViewColorUtil.formatDate(bean.getLastpost()));
            replyNum.setText(bean.getReplies());
            replyName.setText(bean.getLastposter());
            SpannableStringBuilder builder = subjectSpannables.get(bean
                    .getTid());
            if (builder == null) {
                builder = TextViewColorUtil
                        .getForumBodyStyle(bean.getSubject());
                subjectSpannables.put(bean.getTid(), builder);
            }
            subject.setText(builder);
        }
    }

}
