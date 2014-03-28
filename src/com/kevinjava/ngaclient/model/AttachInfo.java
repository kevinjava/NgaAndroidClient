package com.kevinjava.ngaclient.model;

/**
 *  "1": {
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
 * @author kevliu
 *
 */
public class AttachInfo {
	String attachurl;
	String size;
	String type;
	String url_utf8_org_name;
	String ext;
	String name;
	String path;
	
	public String getAttachurl() {
		return attachurl;
	}
	public void setAttachurl(String attachurl) {
		this.attachurl = attachurl;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl_utf8_org_name() {
		return url_utf8_org_name;
	}
	public void setUrl_utf8_org_name(String url_utf8_org_name) {
		this.url_utf8_org_name = url_utf8_org_name;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
}
