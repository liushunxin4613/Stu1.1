package com.fengyang.entity;

import java.sql.Timestamp;

/**
 * AppSecondHandReport entity. @author MyEclipse Persistence Tools
 */

public class AppSecondHandReport implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 8571848246368435364L;
	private Integer secondReportId;
	private User oubaMember;
	private AppSecondHand appSecondHand;
	private Boolean secondFalseInformation;
	private Boolean secondFalseContactWay;
	private Boolean secondOtherReason;
	private String secondDetailReportReason;
	private String memberMobile;
	private Timestamp secondReportTime;
	private Boolean isSolved;

	// Constructors

	/** default constructor */
	public AppSecondHandReport() {
	}

	/** minimal constructor */
	public AppSecondHandReport(User oubaMember,
			AppSecondHand appSecondHand, Boolean secondFalseInformation,
			Boolean secondFalseContactWay, Boolean secondOtherReason,
			String memberMobile, Timestamp secondReportTime, Boolean isSolved) {
		this.oubaMember = oubaMember;
		this.appSecondHand = appSecondHand;
		this.secondFalseInformation = secondFalseInformation;
		this.secondFalseContactWay = secondFalseContactWay;
		this.secondOtherReason = secondOtherReason;
		this.memberMobile = memberMobile;
		this.secondReportTime = secondReportTime;
		this.isSolved = isSolved;
	}

	/** full constructor */
	public AppSecondHandReport(User oubaMember,
			AppSecondHand appSecondHand, Boolean secondFalseInformation,
			Boolean secondFalseContactWay, Boolean secondOtherReason,
			String secondDetailReportReason, String memberMobile,
			Timestamp secondReportTime, Boolean isSolved) {
		this.oubaMember = oubaMember;
		this.appSecondHand = appSecondHand;
		this.secondFalseInformation = secondFalseInformation;
		this.secondFalseContactWay = secondFalseContactWay;
		this.secondOtherReason = secondOtherReason;
		this.secondDetailReportReason = secondDetailReportReason;
		this.memberMobile = memberMobile;
		this.secondReportTime = secondReportTime;
		this.isSolved = isSolved;
	}

	// Property accessors

	public Integer getSecondReportId() {
		return this.secondReportId;
	}

	public void setSecondReportId(Integer secondReportId) {
		this.secondReportId = secondReportId;
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

	public Boolean getSecondFalseInformation() {
		return this.secondFalseInformation;
	}

	public void setSecondFalseInformation(Boolean secondFalseInformation) {
		this.secondFalseInformation = secondFalseInformation;
	}

	public Boolean getSecondFalseContactWay() {
		return this.secondFalseContactWay;
	}

	public void setSecondFalseContactWay(Boolean secondFalseContactWay) {
		this.secondFalseContactWay = secondFalseContactWay;
	}

	public Boolean getSecondOtherReason() {
		return this.secondOtherReason;
	}

	public void setSecondOtherReason(Boolean secondOtherReason) {
		this.secondOtherReason = secondOtherReason;
	}

	public String getSecondDetailReportReason() {
		return this.secondDetailReportReason;
	}

	public void setSecondDetailReportReason(String secondDetailReportReason) {
		this.secondDetailReportReason = secondDetailReportReason;
	}

	public String getMemberMobile() {
		return this.memberMobile;
	}

	public void setMemberMobile(String memberMobile) {
		this.memberMobile = memberMobile;
	}

	public Timestamp getSecondReportTime() {
		return this.secondReportTime;
	}

	public void setSecondReportTime(Timestamp secondReportTime) {
		this.secondReportTime = secondReportTime;
	}

	public Boolean getIsSolved() {
		return this.isSolved;
	}

	public void setIsSolved(Boolean isSolved) {
		this.isSolved = isSolved;
	}

}