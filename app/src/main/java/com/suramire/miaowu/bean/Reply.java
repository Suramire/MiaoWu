package com.suramire.miaowu.bean;
// default package

import java.sql.Timestamp;

/**
 * Reply entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class Reply implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer nid;
	private Integer uid;
	private String replycontent;
	private Timestamp replytime;
	private Integer replyuid;

	// Constructors

	/** default constructor */
	public Reply() {
	}

	/** minimal constructor */
	public Reply(Integer nid, Integer uid, String replycontent) {
		this.nid = nid;
		this.uid = uid;
		this.replycontent = replycontent;
	}

	/** full constructor */
	public Reply(Integer nid, Integer uid, String replycontent,
			Timestamp replytime, Integer replyuid) {
		this.nid = nid;
		this.uid = uid;
		this.replycontent = replycontent;
		this.replytime = replytime;
		this.replyuid = replyuid;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNid() {
		return this.nid;
	}

	public void setNid(Integer nid) {
		this.nid = nid;
	}

	public Integer getUid() {
		return this.uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getReplycontent() {
		return this.replycontent;
	}

	public void setReplycontent(String replycontent) {
		this.replycontent = replycontent;
	}

	public Timestamp getReplytime() {
		return this.replytime;
	}

	public void setReplytime(Timestamp replytime) {
		this.replytime = replytime;
	}

	public Integer getReplyuid() {
		return this.replyuid;
	}

	public void setReplyuid(Integer replyuid) {
		this.replyuid = replyuid;
	}

}