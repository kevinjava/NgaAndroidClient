package com.github.kevinjava.ngaclient.parse;

import com.github.kevinjava.ngaclient.util.ArticleUtil;
import com.kevinjava.ngaclient.controller.NetRequestType;

/**
 * Created by kevliu on 6/26/14.
 */
public class NotLoginParse extends  Parse{
    @Override
    public Object paser(NetRequestType type, String content) {
        return ArticleUtil.parseNotLogin(content);
    }
}
