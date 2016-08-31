package com.fengyang.entity;

import java.sql.Timestamp;

/**
 * AppSecondCollection entity. @author MyEclipse Persistence Tools
 */

public class AppSecondCollection implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -4505426972007928968L;
	private Integer scId;
	private User oubaMember;
	private AppSecondHand appSecondHand;
	private Integer scPublisherType;
	private Timestamp scDate;

	// Constructors

	/** default constructor */
	public AppSecondCollection() {
	}

	/** full constructor */
	public AppSecondCollection(User oubaMember,
			AppSecondHand appSecondHand, Integer scPublisherType,
			Timestamp scDate) {
		this.oubaMember = oubaMember;
		this.appSecondHand = appSecondHand;
		this.scPublisherType = scPublisherType;
		this.scDate = scDate;
	}

	// Property accessors

	public Integer getScId() {
		return this.scId;
	}

	public void setScId(Integer scId) {
		this.scId = scId;
	}

	public User getOubaMember() {
		return this.oubaMember;
	}

	public void setOubaMember(User oubaMember) {
		this.oubaMember = oubaMember;
	}

	public AppSecondHand getAppSecondHand() {
		return this.appSecondHand;
	}

	public void setAppSecondHand(AppSecondHand appSecondHand) {
		this.appSecondHand = appSecondHand;
	}

	public Integer getScPublisherType() {
		return this.scPublisherType;
	}

	public void setScPublisherType(Integer scPublisherType) {
		this.scPublisherType = scPublisherType;
	}

	public Timestamp getScDate() {
		return this.scDate;
	}

	public void setScDate(Timestamp scDate) {
		this.scDate = scDate;
	}

}