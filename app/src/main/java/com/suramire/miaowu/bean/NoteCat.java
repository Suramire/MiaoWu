package com.suramire.miaowu.bean;
// default package

/**
 * NoteCat entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class NoteCat implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer nid;
	private Integer cid;

	// Constructors

	/** default constructor */
	public NoteCat() {
	}

	/** full constructor */
	public NoteCat(Integer nid, Integer cid) {
		this.nid = nid;
		this.cid = cid;
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

	public Integer getCid() {
		return this.cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

}