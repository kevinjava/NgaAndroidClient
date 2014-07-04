package com.github.kevinjava.ngaclient.model;

import java.util.ArrayList;

/**
 * "11": {
                "pid": 128314713,
                "authorid": 5712389,
                "postdate": "2014-03-22 09:06",
                "subject": "",
                "alterinfo": "Edit by pengkezs at 2014-03-22 09:18	",
                "content": "原来我也达到老玩家了 .............哈哈[img]./mon_201403/22/7_532ce9eaebfd7.png[/img][img]./mon_201403/22/7_532ce9dcd09c2.png[/img]",
                "lou": 31,
                "postdatetimestamp": 1395450361,
                "attachs": {
                    "0": {
                        "attachurl": "mon_201403/22/7_532ce9dcd09c2.png",
                        "size": 648,
                        "type": "img",
                        "subid": 0,
                        "url_utf8_org_name": "Q%E6%88%AA%E5%9B%BE20140322091427.png",
                        "dscp": "",
                        "path": "mon_201403/22",
                        "name": "7_532ce9dcd09c2.png",
                        "ext": "png",
                        "thumb": 1
                    },
                    "1": {
                        "attachurl": "mon_201403/22/7_532ce9eaebfd7.png",
                        "size": 681,
                        "type": "img",
                        "subid": 1,
                        "url_utf8_org_name": "Q%E6%88%AA%E5%9B%BE20140322091418.png",
                        "dscp": "",
                        "path": "mon_201403/22",
                        "name": "7_532ce9eaebfd7.png",
                        "ext": "png",
                        "thumb": 1
                    }
                },
                "content_length": 99
                 "comment": {
                    "0": {
                        "pid": 128219980,
                        "fid": 422,
                        "tid": 6947992,
                        "authorid": 18940114,
                        "type": 1,
                        "recommend": 0,
                        "postdate": "2014-03-20 11:03",
                        "subject": "",
                        "alterinfo": "",
                        "content": "上图！",
                        "lou": 0,
                        "postdatetimestamp": 1395284591,
                        "content_length": 6,
                        "comment_to_id": 128218454
                    }
                }
            }
 * @author kevliu
 *
 */
public class ReplyPageInfo extends Comment{
	ArrayList<AttachInfo> attachs;
	ArrayList<Comment> comment;
	public ArrayList<Comment> getComment() {
		return comment;
	}
	public void setComment(ArrayList<Comment> comment) {
		this.comment = comment;
	}
	public ArrayList<AttachInfo> getAttachs() {
		return attachs;
	}
	public void setAttachs(ArrayList<AttachInfo> attachs) {
		this.attachs = attachs;
	}
}
