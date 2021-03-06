package com.suramire.miaowu.bean;

import java.sql.Timestamp;

/**
 * Notification entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class Notification implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer uid1;
	private Integer uid2;
	private String content;
	private Timestamp time;
	private Integer isread;
	private Integer type; //1=关注通知  2=帖子通知  3=审核通知

	// Constructors

	/** default constructor */
	public Notification() {
	}

	/** minimal constructor */
	public Notification(Integer uid1, Integer uid2, String content, Integer type) {
		this.uid1 = uid1;
		this.uid2 = uid2;
		this.content = content;
		this.type = type;
	}

	/** full constructor */
	public Notification(Integer uid1, Integer uid2, String content,
			Timestamp time, Integer isread, Integer type) {
		this.uid1 = uid1;
		this.uid2 = uid2;
		this.content = content;
		this.time = time;
		this.isread = isread;
		this.type = type;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUid1() {
		return this.uid1;
	}

	public void setUid1(Integer uid1) {
		this.uid1 = uid1;
	}

	public Integer getUid2() {
		return this.uid2;
	}

	public void setUid2(Integer uid2) {
		this.uid2 = uid2;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getTime() {
		return this.time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public Integer getIsread() {
		return this.isread;
	}

	public void setIsread(Integer isread) {
		this.isread = isread;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}