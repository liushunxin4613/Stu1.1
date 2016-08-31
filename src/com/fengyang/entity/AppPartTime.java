package com.fengyang.entity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * AppPartTime entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("rawtypes")
public class AppPartTime implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4060500878187041522L;
	// Fields

	private Integer partTimeId;
	private AppPartClass appPartClass;
	private User oubaMember;
	private OubaArea oubaAreaByProId;
	private OubaArea oubaAreaByCityId;
	private OubaArea oubaAreaByDisId;
	private String partTimeName;
	private String partKeyword;
	private String partPublisher;
	private Integer publisherType;
	private Integer partNum;
	private String phone;
	private Double pay;
	private Integer payUnit;
	private Integer payWay;
	private Short timeType;
	private String timeDescription;
	private Date endTime;
	private String jobDescription;
	private Timestamp publishTime;
	private Boolean isPrccessed;
	private Integer partVisited;
	private Integer partCollected;
	private String address;
	
	private Set appPartCollections = new HashSet(0);
	private Set appPartTimeReports = new HashSet(0);

	// Constructors

	/** default constructor */
	public AppPartTime() {
	}

	/** minimal constructor */
	public AppPartTime(AppPartClass appPartClass, User oubaMember,
			String partTimeName, Integer publisherType, String phone,
			Double pay, Integer payUnit, Integer payWay, Short timeType,
			String jobDescription, Boolean isPrccessed, Integer partVisited,
			Integer partCollected) {
		this.appPartClass = appPartClass;
		this.oubaMember = oubaMember;
		this.partTimeName = partTimeName;
		this.publisherType = publisherType;
		this.phone = phone;
		this.pay = pay;
		this.payUnit = payUnit;
		this.payWay = payWay;
		this.timeType = timeType;
		this.jobDescription = jobDescription;
		this.isPrccessed = isPrccessed;
		this.partVisited = partVisited;
		this.partCollected = partCollected;
	}

	/** full constructor */
	public AppPartTime(AppPartClass appPartClass, User oubaMember,
			OubaArea oubaAreaByProId, OubaArea oubaAreaByCityId,
			OubaArea oubaAreaByDisId, String partTimeName, String partKeyword,
			String partPublisher, Integer publisherType, Integer partNum,
			String phone, Double pay, Integer payUnit, Integer payWay,
			Short timeType, String timeDescription, Date endTime,
			String jobDescription, Timestamp publishTime, Boolean isPrccessed,
			Integer partVisited, Integer partCollected, String address,
			Set appPartCollections, Set appPartTimeReports) {
		this.appPartClass = appPartClass;
		this.oubaMember = oubaMember;
		this.oubaAreaByProId = oubaAreaByProId;
		this.oubaAreaByCityId = oubaAreaByCityId;
		this.oubaAreaByDisId = oubaAreaByDisId;
		this.partTimeName = partTimeName;
		this.partKeyword = partKeyword;
		this.partPublisher = partPublisher;
		this.publisherType = publisherType;
		this.partNum = partNum;
		this.phone = phone;
		this.pay = pay;
		this.payUnit = payUnit;
		this.payWay = payWay;
		this.timeType = timeType;
		this.timeDescription = timeDescription;
		this.endTime = endTime;
		this.jobDescription = jobDescription;
		this.publishTime = publishTime;
		this.isPrccessed = isPrccessed;
		this.partVisited = partVisited;
		this.partCollected = partCollected;
		this.address = address;
		this.appPartCollections = appPartCollections;
		this.appPartTimeReports = appPartTimeReports;
	}

	// Property accessors

	public Integer getPartTimeId() {
		return this.partTimeId;
	}

	public void setPartTimeId(Integer partTimeId) {
		this.partTimeId = partTimeId;
	}

	public AppPartClass getAppPartClass() {
		return this.appPartClass;
	}

	public void setAppPartClass(AppPartClass appPartClass) {
		this.appPartClass = appPartClass;
	}

	public User getOubaMember() {
		return this.oubaMember;
	}

	public void setOubaMember(User oubaMember) {
		this.oubaMember = oubaMember;
	}

	public OubaArea getOubaAreaByProId() {
		return this.oubaAreaByProId;
	}

	public void setOubaAreaByProId(OubaArea oubaAreaByProId) {
		this.oubaAreaByProId = oubaAreaByProId;
	}

	public OubaArea getOubaAreaByCityId() {
		return this.oubaAreaByCityId;
	}

	public void setOubaAreaByCityId(OubaArea oubaAreaByCityId) {
		this.oubaAreaByCityId = oubaAreaByCityId;
	}

	public OubaArea getOubaAreaByDisId() {
		return this.oubaAreaByDisId;
	}

	public void setOubaAreaByDisId(OubaArea oubaAreaByDisId) {
		this.oubaAreaByDisId = oubaAreaByDisId;
	}

	public String getPartTimeName() {
		return this.partTimeName;
	}

	public void setPartTimeName(String partTimeName) {
		this.partTimeName = partTimeName;
	}

	public String getPartKeyword() {
		return this.partKeyword;
	}

	public void setPartKeyword(String partKeyword) {
		this.partKeyword = partKeyword;
	}

	public String getPartPublisher() {
		return this.partPublisher;
	}

	public void setPartPublisher(String partPublisher) {
		this.partPublisher = partPublisher;
	}

	public Integer getPublisherType() {
		return this.publisherType;
	}

	public void setPublisherType(Integer publisherType) {
		this.publisherType = publisherType;
	}

	public Integer getPartNum() {
		return this.partNum;
	}

	public void setPartNum(Integer partNum) {
		this.partNum = partNum;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Double getPay() {
		return this.pay;
	}

	public void setPay(Double pay) {
		this.pay = pay;
	}

	public Integer getPayUnit() {
		return this.payUnit;
	}

	public void setPayUnit(Integer payUnit) {
		this.payUnit = payUnit;
	}

	public Integer getPayWay() {
		return this.payWay;
	}

	public void setPayWay(Integer payWay) {
		this.payWay = payWay;
	}

	public Short getTimeType() {
		return this.timeType;
	}

	public void setTimeType(Short timeType) {
		this.timeType = timeType;
	}

	public String getTimeDescription() {
		return this.timeDescription;
	}

	public void setTimeDescription(String timeDescription) {
		this.timeDescription = timeDescription;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getJobDescription() {
		return this.jobDescription;
	}

	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}

	public Timestamp getPublishTime() {
		return this.publishTime;
	}

	public void setPublishTime(Timestamp publishTime) {
		this.publishTime = publishTime;
	}

	public Boolean getIsPrccessed() {
		return this.isPrccessed;
	}

	public void setIsPrccessed(Boolean isPrccessed) {
		this.isPrccessed = isPrccessed;
	}

	public Integer getPartVisited() {
		return this.partVisited;
	}

	public void setPartVisited(Integer partVisited) {
		this.partVisited = partVisited;
	}

	public Integer getPartCollected() {
		return this.partCollected;
	}

	public void setPartCollected(Integer partCollected) {
		this.partCollected = partCollected;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Set getAppPartCollections() {
		return this.appPartCollections;
	}

	public void setAppPartCollections(Set appPartCollections) {
		this.appPartCollections = appPartCollections;
	}

	public Set getAppPartTimeReports() {
		return this.appPartTimeReports;
	}

	public void setAppPartTimeReports(Set appPartTimeReports) {
		this.appPartTimeReports = appPartTimeReports;
	}

}