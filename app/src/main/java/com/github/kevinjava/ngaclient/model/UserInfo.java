package com.github.kevinjava.ngaclient.model;

public class UserInfo {
	String uid;// ":1234567,//uid
	String username;// ":"xxx",
	// "credit":20,//
	// "medal":54,//
	// "reputation":"46_100",//
	// "groupid":-1,//
	// "memberid":39,//
	String avatar;// ":"",//ͷ
	String yz;// ":1,//
	// "site":"",//
	// "honor":"",//
	// "regdate":1199856844,//
	String mute_time;// ":0,//
	// "postnum":2409,//
	String rvrc;// ":0,//
	// "money":23741,//
	// "thisvisit":1363859920,//
	String signature;// ":"",//
	// "nickname":"",//
	// "bit_data":20//
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getYz() {
		return yz;
	}
	public void setYz(String yz) {
		this.yz = yz;
	}
	public String getMute_time() {
		return mute_time;
	}
	public void setMute_time(String mute_time) {
		this.mute_time = mute_time;
	}
	public String getRvrc() {
		return rvrc;
	}
	public void setRvrc(String rvrc) {
		this.rvrc = rvrc;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}

}
