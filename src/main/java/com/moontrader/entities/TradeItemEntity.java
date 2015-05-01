package com.moontrader.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Trade_Item")
public class TradeItemEntity {
	@Id
	@Column(name = "ID")
	@GeneratedValue
	private int id;
	private double price;
	@Column(length=2048)
	private String analyse;
	private String title;
	@ManyToOne(targetEntity = OperationEntity.class)
	@JoinColumn(name = "OPERATION_ID")
	private OperationEntity operation;
	private Date beginTime;
	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	private Date endTime;
	public OperationEntity getOperation() {
		return operation;
	}

	public void setOperation(OperationEntity operation) {
		this.operation = operation;
	}

	private String operationTips;
	private int state;

	public String getAnalyse() {
		return analyse;
	}


	public int getId() {
		return id;
	}


	public String getOperationTips() {
		return operationTips;
	}

	public double getPrice() {
		return price;
	}

	public int getState() {
		return state;
	}

	public String getTitle() {
		return title;
	}

	public void setAnalyse(String analyse) {
		this.analyse = analyse;
	}

	public void setId(int id) {
		this.id = id;
	}


	public void setOperationTips(String operationTips) {
		this.operationTips = operationTips;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setState(int state) {
		this.state = state;
	}


	public void setTitle(String title) {
		this.title = title;
	}
}
