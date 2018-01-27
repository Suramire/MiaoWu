package com.suramire.miaowu.bean;

/**
 * Follow entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class Follow implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer uid1;
	private Integer uid2;

	// Constructors

	/** default constructor */
	public Follow() {
	}

	/** full constructor */
	public Follow(Integer uid1, Integer uid2) {
		this.uid1 = uid1;
		this.uid2 = uid2;
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

}