package com.suramire.miaowu.bean;

import java.sql.Timestamp;

/**
 * Apply entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class Apply implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer nid;
	private Integer uid;
	private Integer cid;
	private Timestamp time;
	private Integer verify;

	// Constructors

	/** default constructor */
	public Apply() {
	}

	/** full constructor */
	public Apply(Integer nid, Integer uid, Integer cid, Timestamp time,
			Integer verify) {
		this.nid = nid;
		this.uid = uid;
		this.cid = cid;
		this.time = time;
		this.verify = verify;
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

	public Integer getCid() {
		return this.cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public Timestamp getTime() {
		return this.time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public Integer getVerify() {
		return this.verify;
	}

	public void setVerify(Integer verify) {
		this.verify = verify;
	}

}