package com.github.kevinjava.ngaclient.model;

public class ForumDataBean {
	/**
	 * "tid": 6901927, "fid": 7, "quote_from": 0, "quote_to": "", "icon": 0,
	 * "topic_misc": "AQAAAAI", "author": "o半缘修道o", "authorid": 6707202,
	 * "subject": "[原创]草蛇灰线，伏隐万方——寓言般的潘达利亚(遍历mop剧情后有感)(最末更新有关纳兹戈林和马尔考罗克的个人观点)",
	 * "type": 32, "postdate": 1393403588, "lastpost": 1393999658, "lastposter":
	 * "自来血", "replies": 197, "lastmodify": 1394000595, "recommend": 20,
	 * "tpcurl": "read.php?tid=6901927"
	 */

	String tid;
	String fid;
	String quote_from;
	String quote_to;
	String icon;
	String topic_misc;
	String author;
	String authorid;
	String subject;
	String type;
	String postdate;
	String lastpost;
	String lastposter;
	String replies;
	String lastmodify;
	String recommend;
	String tpcurl;

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getQuote_from() {
		return quote_from;
	}

	public void setQuote_from(String quote_from) {
		this.quote_from = quote_from;
	}

	public String getQuote_to() {
		return quote_to;
	}

	public void setQuote_to(String quote_to) {
		this.quote_to = quote_to;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getTopic_misc() {
		return topic_misc;
	}

	public void setTopic_misc(String topic_misc) {
		this.topic_misc = topic_misc;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAuthorid() {
		return authorid;
	}

	public void setAuthorid(String authorid) {
		this.authorid = authorid;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPostdate() {
		return postdate;
	}

	public void setPostdate(String postdate) {
		this.postdate = postdate;
	}

	public String getLastpost() {
		return lastpost;
	}

	public void setLastpost(String lastpost) {
		this.lastpost = lastpost;
	}

	public String getLastposter() {
		return lastposter;
	}

	public void setLastposter(String lastposter) {
		this.lastposter = lastposter;
	}

	public String getReplies() {
		return replies;
	}

	public void setReplies(String replies) {
		this.replies = replies;
	}

	public String getLastmodify() {
		return lastmodify;
	}

	public void setLastmodify(String lastmodify) {
		this.lastmodify = lastmodify;
	}

	public String getRecommend() {
		return recommend;
	}

	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}

	public String getTpcurl() {
		return tpcurl;
	}

	public void setTpcurl(String tpcurl) {
		this.tpcurl = tpcurl;
	}

	@Override
	public boolean equals(Object o) {
		if(o == this){
			return true;
		}
		if(o instanceof ForumDataBean){
			if(((ForumDataBean)o).fid.equals(this.fid) && ((ForumDataBean)o).tid.equals(this.tid)){
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tid == null) ? 0 : tid.hashCode());
		result = prime * result + ((fid == null) ? 0 : fid.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}
}
