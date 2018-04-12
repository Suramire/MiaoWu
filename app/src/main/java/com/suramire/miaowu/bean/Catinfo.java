package com.suramire.miaowu.bean;

import java.util.Date;

/**
 * Catinfo entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class Catinfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private String type;
	private Integer sex;
	private Integer age;
	private Integer neutering;
	private Integer insecticide;
	private Date adddate;
	private String conditions;
	private Integer isAdopted;
	private Date adoptdate;
	private Integer uid;

	// Constructors

	/** default constructor */
	public Catinfo() {
	}

	/** minimal constructor */
	public Catinfo(Date adddate, Integer isAdopted) {
		this.adddate = adddate;
		this.isAdopted = isAdopted;
	}

	/** full constructor */
	public Catinfo(String type, Integer sex, Integer age, Integer neutering,
			Integer insecticide, Date adddate, String conditions,
			Integer isAdopted, Date adoptdate, Integer uid) {
		this.type = type;
		this.sex = sex;
		this.age = age;
		this.neutering = neutering;
		this.insecticide = insecticide;
		this.adddate = adddate;
		this.conditions = conditions;
		this.isAdopted = isAdopted;
		this.adoptdate = adoptdate;
		this.uid = uid;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getSex() {
		return this.sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getNeutering() {
		return this.neutering;
	}

	public void setNeutering(Integer neutering) {
		this.neutering = neutering;
	}

	public Integer getInsecticide() {
		return this.insecticide;
	}

	public void setInsecticide(Integer insecticide) {
		this.insecticide = insecticide;
	}

	public Date getAdddate() {
		return this.adddate;
	}

	public void setAdddate(Date adddate) {
		this.adddate = adddate;
	}

	public String getConditions() {
		return this.conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	public Integer getIsAdopted() {
		return this.isAdopted;
	}

	public void setIsAdopted(Integer isAdopted) {
		this.isAdopted = isAdopted;
	}

	public Date getAdoptdate() {
		return this.adoptdate;
	}

	public void setAdoptdate(Date adoptdate) {
		this.adoptdate = adoptdate;
	}

	public Integer getUid() {
		return this.uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

}