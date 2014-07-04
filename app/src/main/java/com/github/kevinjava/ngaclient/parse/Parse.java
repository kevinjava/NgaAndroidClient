package com.github.kevinjava.ngaclient.parse;

import com.kevinjava.ngaclient.controller.NetRequestType;

/**
 * Created by kevliu on 6/26/14.
 */
public abstract  class Parse {
    Parse nextParse;
    public void setParse(Parse parse) {
        this.nextParse = parse;
    }

    public abstract Object paser(NetRequestType type, String content);
}
