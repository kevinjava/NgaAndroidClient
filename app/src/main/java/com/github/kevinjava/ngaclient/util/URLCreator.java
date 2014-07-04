package com.github.kevinjava.ngaclient.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.kevinjava.ngaclient.NgaApp;
import com.github.kevinjava.ngaclient.model.GroupModel;

public class URLCreator {
	
	private static final String TAG = URLCreator.class.getSimpleName();
	private static final String BASE_URL = "http://bbs.ngacn.cc";
	private static final String QUESTION_MARK = "?";
	private static final String AND = "&";
	/**
	 * fid 版面ID 整数 或 逗号分隔的整数 page 页 整数 authorid 主题作者用户id 整数 key 搜索关键字
	 * 字符串urlencode fidgroup 搜索的版面组 取值为user时表示全部用户版 无此参数为全部非用户版 favor 收藏的主题
	 * 为1时显示收藏的主题 recommend 推荐精华加分的主题 为1时显示推荐 精华 加分的主题 lite 输出格式 取值为js
	 */
	private static final String THREAD = "/thread.php";
	private static final String FID = "fid=";
	private static final String PAGE = "page=";
	private static final String AUTHOR_ID = "authorid=";
	private static final String KEY = "key=";
	private static final String FID_GROUP = "fidgroup=";
	private static final String FAVOR = "favor=";
	private static final String RECOMMEND = "recommend=";
	private static final String LITE = "lite=js";

	/**
	 * tid //主题id 整数 pid //回复id 整数 page //页 整数 authorid //作者用户id 整数 lite //输出格式
	 * 见1.1 v2 //固定为1
	 */

	private static final String READ = "/read.php";
	private static final String V2 = "v2";
	private static final String TID = "tid=";
	private static final String PID = "pid=";

	/**
	 * action //动作 new为新主题 reply为回复 quote为引用 modify为编辑 pid //回复id 整数 tid //主题id
	 * 整数 fid //版面id 整数 lite //输出格式 见1.1
	 */
	private static final String POST = "/post.php";
	private static final String ACTION = "action=";

	/**
	 * __lib //固定取值 ucp __act //固定取值 get uid //用户id 整数 username //用户名 字符串 lite
	 * //输出格式 见1.1
	 */
	private static final String NUKE = "/nuke.php";

	/**
	 * /thread.php?fid=7&page=1&lite=js (版面id7的主题第一页
	 * 
	 * /thread.php?authorid=58&page=1&lite=js (发布人id是58的主题第一页
	 * 
	 * /thread.php?authorid=58&key=keyword&fid=7&page=1&lite=js (发布人id是58
	 * 在版面id7中 发布的主题
	 * 
	 * /thread.php?key=keyword&fid=7&page=1&lite=js (在版面id7中搜索标题包含keyword的主题
	 * 
	 * /thread.php?key=keyword&fid=7,10&page=1&lite=js (在版面id7和10中搜索
	 * 
	 * /thread.php?key=keyword&fidgroup=user&page=1&lite=js (在所有用户版面(fid<0)中搜索
	 * 
	 * /thread.php?key=keyword&page=1&lite=js (在所有非用户版面(fid>0)中搜索
	 * 
	 * /thread.php?favor=1 (自己收藏的主题
	 * 
	 * /thread.php?recommend=1 (所有精华 推荐 加分的主题
	 * 
	 * /thread.php?fid=7&recommend=1 (版面id7中所有精华 推荐 加分的主题
	 * 
	 * /thread.php?fid=7&recommend=1&authorid=58 (版面id7中发布人用户id是58的所有精华 推荐 加分的主题
	 */

	public static final String getForumUrl(int fid, int page, int tab) {
		String url = new StringBuffer(BASE_URL).append(THREAD)
				.append(QUESTION_MARK).append(FID).append(getFid(fid, tab))
				.append(AND).append(PAGE).append(page).append(AND).append(LITE)
				.append(AND).append(V2).toString();
		NgaLog.i(TAG, url);
		return url;
	}

	public static final String getSubjectUrl(int tid, int page) {
		String url = new StringBuffer(BASE_URL).append(READ).append(QUESTION_MARK)
				.append(TID).append(tid).append(AND).append(PAGE).append(page)
				.append(AND).append(LITE).append(AND).append(V2).toString();
		NgaLog.i(TAG, "subject url: " + url);
		return url;
	}

	public static final int getFid(int index, int tabIndex) {
		final ArrayList<GroupModel> forums = NgaApp.groups;
		GroupModel currentGroup = forums.get(index);
		return currentGroup.forums.get(tabIndex).fid;
	}

	public static final void parseString() {
		String test = "[123][abc][456def]测试subject";
		Pattern pattern = Pattern.compile("\\[[^\\]]*\\]");
		Matcher matcher = pattern.matcher(test);
		while (matcher.find()) {
			System.out.println(matcher.start());
			System.out.println(matcher.end());
			System.out.println(matcher.group());
		}
        getSubjectUrl(99,1);
	}

	public static String formatDate(String time) {
		SimpleDateFormat format = new SimpleDateFormat();
		System.out.println(format.format(new Date(Long.parseLong(time))));
		return null;
	}

	public static void main(String[] args) {
		// System.out.println(getForumUrl(7, 1, 0));
		parseString();
		formatDate("1394680613");
	}
}
