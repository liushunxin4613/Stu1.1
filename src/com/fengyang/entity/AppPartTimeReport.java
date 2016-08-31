package com.fengyang.entity;

import java.sql.Timestamp;

/**
 * AppPartTimeReport entity. @author MyEclipse Persistence Tools
 */

public class AppPartTimeReport implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -4059588233073764524L;
	private Integer partReportId;
	private User oubaMember;
	private AppPartTime appPartTime;
	private Boolean partFalseInformation;
	private Boolean partFalseContactWay;
	private Boolean partFalseCompany;
	private Boolean partConduitCompany;
	private Boolean partOtherReason;
	private String partDetailReportReason;
	private String memberMobile;
	private Timestamp partReportTime;
	private Boolean isSolved;

	// Constructors

	/** default constructor */
	public AppPartTimeReport() {
	}

	/** minimal constructor */
	public AppPartTimeReport(User oubaMember, AppPartTime appPartTime,
			Boolean partFalseInformation, Boolean partFalseContactWay,
			Boolean partFalseCompany, Boolean partConduitCompany,
			Boolean partOtherReason, String memberMobile,
			Timestamp partReportTime, Boolean isSolved) {
		this.oubaMember = oubaMember;
		this.appPartTime = appPartTime;
		this.partFalseInformation = partFalseInformation;
		this.partFalseContactWay = partFalseContactWay;
		this.partFalseCompany = partFalseCompany;
		this.partConduitCompany = partConduitCompany;
		this.partOtherReason = partOtherReason;
		this.memberMobile = memberMobile;
		this.partReportTime = partReportTime;
		this.isSolved = isSolved;
	}

	/** full constructor */
	public AppPartTimeReport(User oubaMember, AppPartTime appPartTime,
			Boolean partFalseInformation, Boolean partFalseContactWay,
			Boolean partFalseCompany, Boolean partConduitCompany,
			Boolean partOtherReason, String partDetailReportReason,
			String memberMobile, Timestamp partReportTime, Boolean isSolved) {
		this.oubaMember = oubaMember;
		this.appPartTime = appPartTime;
		this.partFalseInformation = partFalseInformation;
		this.partFalseContactWay = partFalseContactWay;
		this.partFalseCompany = partFalseCompany;
		this.partConduitCompany = partConduitCompany;
		this.partOtherReason = partOtherReason;
		this.partDetailReportReason = partDetailReportReason;
		this.memberMobile = memberMobile;
		this.partReportTime = partReportTime;
		this.isSolved = isSolved;
	}

	// Property accessors

	public Integer getPartReportId() {
		return this.partReportId;
	}

	public void setPartReportId(Integer partReportId) {
		this.partReportId = partReportId;
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

	public Boolean getPartFalseInformation() {
		return this.partFalseInformation;
	}

	public void setPartFalseInformation(Boolean partFalseInformation) {
		this.partFalseInformation = partFalseInformation;
	}

	public Boolean getPartFalseContactWay() {
		return this.partFalseContactWay;
	}

	public void setPartFalseContactWay(Boolean partFalseContactWay) {
		this.partFalseContactWay = partFalseContactWay;
	}

	public Boolean getPartFalseCompany() {
		return this.partFalseCompany;
	}

	public void setPartFalseCompany(Boolean partFalseCompany) {
		this.partFalseCompany = partFalseCompany;
	}

	public Boolean getPartConduitCompany() {
		return this.partConduitCompany;
	}

	public void setPartConduitCompany(Boolean partConduitCompany) {
		this.partConduitCompany = partConduitCompany;
	}

	public Boolean getPartOtherReason() {
		return this.partOtherReason;
	}

	public void setPartOtherReason(Boolean partOtherReason) {
		this.partOtherReason = partOtherReason;
	}

	public String getPartDetailReportReason() {
		return this.partDetailReportReason;
	}

	public void setPartDetailReportReason(String partDetailReportReason) {
		this.partDetailReportReason = partDetailReportReason;
	}

	public String getMemberMobile() {
		return this.memberMobile;
	}

	public void setMemberMobile(String memberMobile) {
		this.memberMobile = memberMobile;
	}

	public Timestamp getPartReportTime() {
		return this.partReportTime;
	}

	public void setPartReportTime(Timestamp partReportTime) {
		this.partReportTime = partReportTime;
	}

	public Boolean getIsSolved() {
		return this.isSolved;
	}

	public void setIsSolved(Boolean isSolved) {
		this.isSolved = isSolved;
	}

}