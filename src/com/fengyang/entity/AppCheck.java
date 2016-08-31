package com.fengyang.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * AppCheck entity. @author MyEclipse Persistence Tools
 */

public class AppCheck implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -7541540281816022592L;
	private Long checkId;
	private User oubaMember;
	private Long checkSum;
	private Timestamp checkDatePre;
	private Integer checkSumRe;
	private Integer checkContinueSum;
	@SuppressWarnings("rawtypes")
	private Set appCheckFoots = new HashSet(0);

	// Constructors

	/** default constructor */
	public AppCheck() {
	}

	/** minimal constructor */
	public AppCheck(User oubaMember, Timestamp checkDatePre) {
		this.oubaMember = oubaMember;
		this.checkDatePre = checkDatePre;
	}

	/** full constructor */
	@SuppressWarnings("rawtypes")
	public AppCheck(User oubaMember, Long checkSum,
			Timestamp checkDatePre, Integer checkSumRe,
			Integer checkContinueSum, Set appCheckFoots) {
		this.oubaMember = oubaMember;
		this.checkSum = checkSum;
		this.checkDatePre = checkDatePre;
		this.checkSumRe = checkSumRe;
		this.checkContinueSum = checkContinueSum;
		this.appCheckFoots = appCheckFoots;
	}

	// Property accessors

	public Long getCheckId() {
		return this.checkId;
	}

	public void setCheckId(Long checkId) {
		this.checkId = checkId;
	}

	public User getOubaMember() {
		return this.oubaMember;
	}

	public void setOubaMember(User oubaMember) {
		this.oubaMember = oubaMember;
	}

	public Long getCheckSum() {
		return this.checkSum;
	}

	public void setCheckSum(Long checkSum) {
		this.checkSum = checkSum;
	}

	public Timestamp getCheckDatePre() {
		return this.checkDatePre;
	}

	public void setCheckDatePre(Timestamp checkDatePre) {
		this.checkDatePre = checkDatePre;
	}

	public Integer getCheckSumRe() {
		return this.checkSumRe;
	}

	public void setCheckSumRe(Integer checkSumRe) {
		this.checkSumRe = checkSumRe;
	}

	public Integer getCheckContinueSum() {
		return this.checkContinueSum;
	}

	public void setCheckContinueSum(Integer checkContinueSum) {
		this.checkContinueSum = checkContinueSum;
	}

	@SuppressWarnings("rawtypes")
	public Set getAppCheckFoots() {
		return this.appCheckFoots;
	}

	@SuppressWarnings("rawtypes")
	public void setAppCheckFoots(Set appCheckFoots) {
		this.appCheckFoots = appCheckFoots;
	}

}