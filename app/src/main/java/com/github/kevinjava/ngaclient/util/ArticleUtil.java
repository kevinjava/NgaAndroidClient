package com.github.kevinjava.ngaclient.util;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.kevinjava.ngaclient.model.ArticlePage;
import com.github.kevinjava.ngaclient.model.AttachInfo;
import com.github.kevinjava.ngaclient.model.Comment;
import com.github.kevinjava.ngaclient.model.ForumDataBean;
import com.github.kevinjava.ngaclient.model.ReplyPageInfo;
import com.github.kevinjava.ngaclient.model.SubjectData;
import com.github.kevinjava.ngaclient.model.ThreadData;
import com.github.kevinjava.ngaclient.model.ThreadPageInfo;
import com.github.kevinjava.ngaclient.model.UserInfo;
import com.github.kevinjava.ngaclient.service.tasks.NotLoginTask;

import java.util.ArrayList;
import java.util.List;

public class ArticleUtil {
    private final static String TAG = ArticleUtil.class.getSimpleName();

    private final static String JS_HEADER = "window.script_muti_get_var_store=";
    private final static String NOT_LOGIN = "访客不能直接访问";
    private final static String CHECK_PROMISSION = "查看所需的权限/条件";

    public static ArticlePage parserArticleList(String html) {
        return null;
    }

    private static JSONObject globalParseData(String content) {
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        content = content.replaceAll(JS_HEADER, "");
        JSONObject o = null;
        try {
            o = (JSONObject) JSON.parseObject(content).get("data");
        } catch (Exception e) {
            NgaLog.e(TAG, "can not parse :\n" + content);
        }
        return o;
    }

    public static NotLoginTask parseNotLogin(String content) {
        JSONObject data = globalParseData(content);
        if (data == null) {
            return null;
        }
        JSONObject message = (JSONObject) data.get("__MESSAGE");
        if (message == null) {
            return null;
        }
        String messageLine2 = JSONObject.toJSONString(message.get("1"));
        String messageLine3 = JSONObject.toJSONString(message.get("2"));
        if (messageLine2 == null || messageLine3 == null) {
            return null;
        }

        if (messageLine2.contains(NOT_LOGIN) && messageLine3.contains(CHECK_PROMISSION)) {
            return new NotLoginTask();
        } else {
            return null;
        }
    }

    public static ThreadData parseJsonThreadPage(String js) {
        JSONObject o = globalParseData(js);
        if (o == null) {
            return null;
        }
        ThreadData data = new ThreadData();

        JSONObject o1;
        o1 = (JSONObject) o.get("__T");
        if (o1 == null)
            return null;

        int rows = (Integer) o.get("__T__ROWS");

        List<ForumDataBean> __R = convertJSobjToList(o1, rows);
        data.setRowList(__R);
        data.setRowNum(__R.size());

        return data;

    }

    private static List<ForumDataBean> convertJSobjToList(JSONObject rowMap,
                                                          int count) {
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

    public static SubjectData parseSubjectPage(String js) {
        if (js == null) {
            return null;
        }
        js = js.replaceAll(JS_HEADER, "");
        JSONObject o = null;
        try {
            o = (JSONObject) JSON.parseObject(js).get("data");
        } catch (Exception e) {
            NgaLog.e(TAG, "can not parse :\n" + js);
        }
        if (o == null)
            return null;
        SubjectData data = new SubjectData();
        // parse threadinfo
        JSONObject threadinfo;
        threadinfo = (JSONObject) o.get("__T");
        if (threadinfo == null)
            return null;
        ThreadPageInfo info = JSONObject.toJavaObject(threadinfo,
                ThreadPageInfo.class);
        data.set__T(info);

        // parse ROWS
        data.set__ROWS(o.getIntValue("__ROWS"));
        // parse __R__ROWS
        data.set__R__ROWS(o.getIntValue("__R__ROWS"));
        // parse __R__ROWS_PAGE
        data.set__R__ROWS_PAGE(o.getIntValue("__R__ROWS_PAGE"));

        // parse __R
        JSONObject replys;
        replys = (JSONObject) o.get("__R");
        if (replys != null) {
            JSONObject users = (JSONObject) o.get("__U");
            convertJSToList(replys, users, data);
        }
        return data;
    }

    private static void convertJSToList(JSONObject rowMap, JSONObject users,
                                        SubjectData data) {
        ArrayList<ReplyPageInfo> __R = new ArrayList<ReplyPageInfo>();
        ArrayList<UserInfo> __U = new ArrayList<UserInfo>();
        int count = data.get__R__ROWS();

        for (int i = 0; i < count; i++) {
            JSONObject rowObj = (JSONObject) rowMap.get(String.valueOf(i));
            ReplyPageInfo row = null;
            if (rowObj != null) {
                row = new ReplyPageInfo();
                row.setPid(rowObj.getLongValue("pid"));
                row.setAlterinfo(rowObj.getString("alterinfo"));
                row.setAuthorid(rowObj.getLongValue("authorid"));
                row.setContent(rowObj.getString("content"));
                row.setLou(rowObj.getIntValue("lou"));
                row.setPostdate(rowObj.getString("postdate"));
                try {
                    JSONObject attachs = (JSONObject) rowObj.get("attachs");
                    if (attachs != null) {
                        row.setAttachs(convertJSobjToList(attachs));
                    }
                } catch (Exception e) {

                }

                try {
                    JSONObject comments = (JSONObject) rowObj.get("comment");
                    if (comments != null) {
                        row.setComment(convertCommentsList(comments, __U, users));
                    }
                } catch (Exception e) {

                }
                __U.add(fillUserInfo(row.getAuthorid(), users));
                __R.add(row);
            }

        }

        data.set__U(__U);
        data.set__R(__R);
    }

    private static ArrayList<AttachInfo> convertJSobjToList(JSONObject rowMap) {
        ArrayList<AttachInfo> __R = new ArrayList<AttachInfo>();

        if (rowMap == null)
            return null;

        for (int i = 0; ; i++) {
            JSONObject rowObj = (JSONObject) rowMap.get(String.valueOf(i));
            AttachInfo row = null;
            if (rowObj != null) {
                row = new AttachInfo();
                row.setAttachurl(rowObj.getString("attachurl"));
                row.setExt(rowObj.getString("ext"));
                row.setName(rowObj.getString("name"));
                row.setPath(rowObj.getString("path"));
                row.setSize(rowObj.getString("size"));
                row.setType(rowObj.getString("type"));
                row.setUrl_utf8_org_name(rowObj.getString("url_utf8_org_name"));
                __R.add(row);
            } else {
                break;
            }
        }
        return __R;
    }

    private static ArrayList<Comment> convertCommentsList(JSONObject rowMap,
                                                          ArrayList<UserInfo> users, JSONObject userObj) {
        ArrayList<Comment> __R = new ArrayList<Comment>();

        if (rowMap == null)
            return null;

        for (int i = 0; ; i++) {
            JSONObject rowObj = (JSONObject) rowMap.get(String.valueOf(i));
            Comment row = null;
            if (rowObj != null) {
                row = new Comment();
                row = new ReplyPageInfo();
                row.setPid(rowObj.getLongValue("pid"));
                row.setAlterinfo(rowObj.getString("alterinfo"));
                row.setAuthorid(rowObj.getLongValue("authorid"));
                row.setContent(rowObj.getString("content"));
                row.setLou(rowObj.getIntValue("lou"));
                row.setPostdate(rowObj.getString("postdate"));
                users.add(fillUserInfo(row.getAuthorid(), userObj));
                __R.add(row);
            } else {
                break;
            }
        }
        return __R;
    }

    private static UserInfo fillUserInfo(long authorid, JSONObject userInfoMap) {
        if (authorid == 0) {
            return null;
        }
        JSONObject userInfoJS = (JSONObject) userInfoMap.get(String
                .valueOf(authorid));
        if (userInfoJS == null) {
            return null;
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setUid(userInfoJS.getString("uid"));
        userInfo.setAvatar(userInfoJS.getString("avatar"));
        userInfo.setMute_time(userInfoJS.getString("mute_time"));
        userInfo.setRvrc(userInfoJS.getString("rvrc"));
        userInfo.setUsername(userInfoJS.getString("username"));
        userInfo.setYz(userInfoJS.getString("yz"));
        return userInfo;
    }

}
