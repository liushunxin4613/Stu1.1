package com.fengyang.entity;

import java.sql.Timestamp;

/**
 * AppCheckFoot entity. @author MyEclipse Persistence Tools
 */

public class AppCheckFoot implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 281277933734782277L;
	private Long cfId;
	private User oubaMember;
	private AppCheck appCheck;
	private Short cfThisDate;
	private Timestamp cfDate;
	private Integer cfPoints;
	private Integer cfToday;

	// Constructors

	/** default constructor */
	public AppCheckFoot() {
	}

	/** full constructor */
	public AppCheckFoot(User oubaMember, AppCheck appCheck,
			Short cfThisDate, Timestamp cfDate, Integer cfPoints,
			Integer cfToday) {
		this.oubaMember = oubaMember;
		this.appCheck = appCheck;
		this.cfThisDate = cfThisDate;
		this.cfDate = cfDate;
		this.cfPoints = cfPoints;
		this.cfToday = cfToday;
	}

	// Property accessors

	public Long getCfId() {
		return this.cfId;
	}

	public void setCfId(Long cfId) {
		this.cfId = cfId;
	}

	public User getOubaMember() {
		return this.oubaMember;
	}

	public void setOubaMember(User oubaMember) {
		this.oubaMember = oubaMember;
	}

	public AppCheck getAppCheck() {
		return this.appCheck;
	}

	public void setAppCheck(AppCheck appCheck) {
		this.appCheck = appCheck;
	}

	public Short getCfThisDate() {
		return this.cfThisDate;
	}

	public void setCfThisDate(Short cfThisDate) {
		this.cfThisDate = cfThisDate;
	}

	public Timestamp getCfDate() {
		return this.cfDate;
	}

	public void setCfDate(Timestamp cfDate) {
		this.cfDate = cfDate;
	}

	public Integer getCfPoints() {
		return this.cfPoints;
	}

	public void setCfPoints(Integer cfPoints) {
		this.cfPoints = cfPoints;
	}

	public Integer getCfToday() {
		return this.cfToday;
	}

	public void setCfToday(Integer cfToday) {
		this.cfToday = cfToday;
	}

}