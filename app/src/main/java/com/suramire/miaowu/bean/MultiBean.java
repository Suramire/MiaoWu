package com.suramire.miaowu.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 存放首页帖子item信息
 * 多表联合 User Note Note_Photo
 * @author Suramire
 * @since 2017-10-30 15:26:30
 */

@SuppressWarnings("serial")
public class MultiBean implements Serializable {
	
	private String nickname;
	private String name;
	private int id;
	private int uid;
	private String icon;
	private String title;
	private String content;
	private Timestamp publish;
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getPublish() {
		return publish;
	}
	public void setPublish(Timestamp publish) {
		this.publish = publish;
	}
	

}
