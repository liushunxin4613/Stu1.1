package com.fengyang.model;

import java.sql.Timestamp;
import java.util.Date;

import com.fengyang.entity.AppPartCollection;
import com.fengyang.entity.AppPartTime;

/**
 * AppPartCollection entity. @author MyEclipse Persistence Tools
 */

public class PartCollection implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = -3856771707655620388L;
	/**
	 * 收藏兼职的id
	 */
	private Integer pcId;
	/**
	 * 兼职的id
	 */
	private Integer partTimeId;
	/**
	 * 收藏者的id
	 */
	private Integer collecterId;
	private Timestamp publishTime;
	private Timestamp collectDate;
	private String publisherName;
	private String PartName;
	private Integer publisherType;
	private Double pay;
	private Integer payUnit;
	private Integer payWay;
	private Short timeType;

	// Constructors

	/** default constructor */
	public PartCollection() {
	}

	public PartCollection(Integer pcId, Integer partTimeId,
			Integer collecterId, Timestamp publishTime, Timestamp collectDate,
			String publisherName, String partName, Integer publisherType,
			Double pay, Integer payUnit, Integer payWay, Short timeType) {
		super();
		this.pcId = pcId;
		this.partTimeId = partTimeId;
		this.collecterId = collecterId;
		this.publishTime = publishTime;
		this.collectDate = collectDate;
		this.publisherName = publisherName;
		PartName = partName;
		this.publisherType = publisherType;
		this.pay = pay;
		this.payUnit = payUnit;
		this.payWay = payWay;
		this.timeType = timeType;
	}

	public static PartCollection Parse(AppPartTime partTime, int userId,
			Date collectTime) {
		PartCollection collection = new PartCollection(null,
				partTime.getPartTimeId(), userId, partTime.getPublishTime(),
				new Timestamp(collectTime.getTime()),
				partTime.getPartPublisher(), partTime.getPartTimeName(),
				partTime.getPublisherType(), partTime.getPay(),
				partTime.getPayUnit(), partTime.getPayWay(),
				partTime.getTimeType());

		return collection;
	}

	public static PartCollection Parse(AppPartCollection appCollection) {
		AppPartTime partTime = appCollection.getAppPartTime();
		PartCollection collection = new PartCollection(appCollection.getPcId(),
				partTime.getPartTimeId(),
				appCollection.getOubaMember().getId(),
				partTime.getPublishTime(), appCollection.getPcDate(),
				partTime.getPartPublisher(), partTime.getPartTimeName(),
				appCollection.getPcPublisherType(), partTime.getPay(),
				partTime.getPayUnit(), partTime.getPayWay(),
				partTime.getTimeType());

		return collection;
	}

	public Integer getPcId() {
		return pcId;
	}

	public void setPcId(Integer pcId) {
		this.pcId = pcId;
	}

	public Integer getPartTimeId() {
		return partTimeId;
	}

	public void setPartTimeId(Integer partTimeId) {
		this.partTimeId = partTimeId;
	}

	public Integer getCollecterId() {
		return collecterId;
	}

	public void setCollecterId(Integer collecterId) {
		this.collecterId = collecterId;
	}

	public Timestamp getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Timestamp publishTime) {
		this.publishTime = publishTime;
	}

	public Timestamp getCollectDate() {
		return collectDate;
	}

	public void setCollectDate(Timestamp collectDate) {
		this.collectDate = collectDate;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public String getPartName() {
		return PartName;
	}

	public void setPartName(String partName) {
		PartName = partName;
	}

	public Integer getPublisherType() {
		return publisherType;
	}

	public void setPublisherType(Integer publisherType) {
		this.publisherType = publisherType;
	}

	public Double getPay() {
		return pay;
	}

	public void setPay(Double pay) {
		this.pay = pay;
	}

	public Integer getPayUnit() {
		return payUnit;
	}

	public void setPayUnit(Integer payUnit) {
		this.payUnit = payUnit;
	}

	public Integer getPayWay() {
		return payWay;
	}

	public void setPayWay(Integer payWay) {
		this.payWay = payWay;
	}

	public Short getTimeType() {
		return timeType;
	}

	public void setTimeType(Short timeType) {
		this.timeType = timeType;
	}

}