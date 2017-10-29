package com.suramire.miaowu.pojo;

/**
 * NotePhoto entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class NotePhoto implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer nid;
	private String name;

	// Constructors

	/** default constructor */
	public NotePhoto() {
	}

	/** full constructor */
	public NotePhoto(Integer nid, String name) {
		this.nid = nid;
		this.name = name;
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}