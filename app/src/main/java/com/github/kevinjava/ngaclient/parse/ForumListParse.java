package com.github.kevinjava.ngaclient.parse;

import com.github.kevinjava.ngaclient.util.ArticleUtil;
import com.github.kevinjava.ngaclient.util.NgaLog;
import com.kevinjava.ngaclient.controller.NetRequestType;

/**
 * Created by kevliu on 6/26/14.
 */
public class ForumListParse extends Parse {
    @Override
    public Object paser(NetRequestType type, String content) {
        Object obj = ArticleUtil.parseJsonThreadPage(content);
        if(obj == null && nextParse != null){
            obj = nextParse.paser(type, content);
            NgaLog.i("TAG", "use notlogin parse");
        }
        return obj;
    }
}
