package com.kevinjava.ngaclient.util;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kevinjava.ngaclient.model.ArticlePage;
import com.kevinjava.ngaclient.model.ForumDataBean;
import com.kevinjava.ngaclient.model.ThreadData;
import com.kevinjava.ngaclient.model.ThreadRowInfo;

public class ArticleUtil {
	private final static String TAG = ArticleUtil.class.getSimpleName();
	
	private final static String JS_HEADER = "window.script_muti_get_var_store=";

	public static ArticlePage parserArticleList(String html) {
		return null;
	}

	private Context context;

	public ArticleUtil(Context context) {
		super();
		this.context = context;
	}

	private boolean isInWifi() {
		ConnectivityManager conMan = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.getState();
		return wifi == State.CONNECTED;
	}

	public static ThreadData parseJsonThreadPage(String js) {
		js = js.replaceAll("window.script_muti_get_var_store=", "");
		JSONObject o = null;
		try {
			o = (JSONObject) JSON.parseObject(js).get("data");
		} catch (Exception e) {
			Log.e(TAG, "can not parse :\n" + js);
		}
		if (o == null)
			return null;

		ThreadData data = new ThreadData();

		JSONObject o1;
		o1 = (JSONObject) o.get("__T");
		if (o1 == null)
			return null;

		int rows = (Integer) o.get("__T__ROWS");

		List<ForumDataBean> __R = convertJSobjToList(o1, rows, null);
		data.setRowList(__R);
		data.setRowNum(__R.size());

		return data;

	}

	private static List<ForumDataBean> convertJSobjToList(JSONObject rowMap,
			int count, JSONObject userInfoMap) {
		List<ForumDataBean> __R = new ArrayList<ForumDataBean>();

		if (rowMap == null)
			return null;

		for (int i = 0; i < count; i++) {
			JSONObject rowObj = (JSONObject) rowMap.get(String.valueOf(i));
			ForumDataBean row = null;
			if (rowObj != null) {
				row = JSONObject.toJavaObject(rowObj, ForumDataBean.class);
				__R.add(row);
			}

		}
		return __R;
	}

	private static void fillUserInfo(ThreadRowInfo row, JSONObject userInfoMap) {
		if (row.getAuthorid() == 0) {
			return;
		}
		JSONObject userInfo = (JSONObject) userInfoMap.get(String.valueOf(row
				.getAuthorid()));
		if (userInfo == null) {
			return;
		}
		row.setAuthor(userInfo.getString("username"));
		row.setJs_escap_avatar(userInfo.getString("avatar"));
		row.setYz(userInfo.getString("yz"));
		row.setMute_time(userInfo.getString("mute_time"));
		try {
			row.setAurvrc(Integer.valueOf(userInfo.getString("rvrc")));
		} catch (Exception e) {
			row.setAurvrc(0);
		}
		row.setSignature(userInfo.getString("signature"));
	}

	private static List<ForumDataBean> convertJSobjToList(JSONObject rowMap,
			JSONObject userInfoMap) {

		return convertJSobjToList(rowMap, rowMap.size(), userInfoMap);
	}

	private static void fillFormated_html_data(ThreadRowInfo row, int i) {

//		ThemeManager theme = ThemeManager.getInstance();
//		if (row.getContent() == null) {
//			row.setContent(row.getSubject());
//			row.setSubject(null);
//		}
//		int bgColor = context.getResources().getColor(
//				theme.getBackgroundColor(i));
//		int fgColor = context.getResources().getColor(
//				theme.getForegroundColor());
//		bgColor = bgColor & 0xffffff;
//		final String bgcolorStr = String.format("%06x", bgColor);
//
//		int htmlfgColor = fgColor & 0xffffff;
//		final String fgColorStr = String.format("%06x", htmlfgColor);
//
//		String formated_html_data = ArticleListAdapter.convertToHtmlText(row,
//				isShowImage(), fgColorStr, bgcolorStr);
//
//		row.setFormated_html_data(formated_html_data);
	}

}
