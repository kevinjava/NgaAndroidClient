package com.github.kevinjava.ngaclient.parse;

/**
 * Created by kevliu on 6/26/14.
 */
public class ParseHelper {
    static Parse parse;
    static {
        parse = new ForumListParse();
        parse.setParse(new NotLoginParse());
    }

    public static Object parse(String content){
        Object response = parse.paser(null, content);
        return response;
    }
}
