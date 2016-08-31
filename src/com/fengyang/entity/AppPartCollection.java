package com.fengyang.entity;

import java.sql.Timestamp;

/**
 * AppPartCollection entity. @author MyEclipse Persistence Tools
 */

public class AppPartCollection implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -407430339241446543L;
	private Integer pcId;
	private User oubaMember;
	private AppPartTime appPartTime;
	private Timestamp pcDate;
	private Integer pcPublisherType;

	// Constructors

	/** default constructor */
	public AppPartCollection() {
	}

	/** full constructor */
	public AppPartCollection(User oubaMember, AppPartTime appPartTime,
			Timestamp pcDate, Integer pcPublisherType) {
		this.oubaMember = oubaMember;
		this.appPartTime = appPartTime;
		this.pcDate = pcDate;
		this.pcPublisherType = pcPublisherType;
	}

	// Property accessors

	public Integer getPcId() {
		return this.pcId;
	}

	public void setPcId(Integer pcId) {
		this.pcId = pcId;
	}

	public User getOubaMember() {
		return this.oubaMember;
	}

	public void setOubaMember(User oubaMember) {
		this.oubaMember = oubaMember;
	}

	public AppPartTime getAppPartTime() {
		return this.appPartTime;
	}

	public void setAppPartTime(AppPartTime appPartTime) {
		this.appPartTime = appPartTime;
	}

	public Timestamp getPcDate() {
		return this.pcDate;
	}

	public void setPcDate(Timestamp pcDate) {
		this.pcDate = pcDate;
	}

	public Integer getPcPublisherType() {
		return this.pcPublisherType;
	}

	public void setPcPublisherType(Integer pcPublisherType) {
		this.pcPublisherType = pcPublisherType;
	}

}