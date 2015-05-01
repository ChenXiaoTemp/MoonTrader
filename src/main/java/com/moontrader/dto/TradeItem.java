package com.moontrader.dto;

import java.io.Serializable;
import java.util.Date;

public class TradeItem implements Serializable{
	private static final long serialVersionUID = -8197979235283788984L;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	public String getAnalyse() {
		return analyse;
	}
	public void setAnalyse(String analyse) {
		this.analyse = analyse;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Operation getOperation() {
		return operation;
	}
	public void setOperation(Operation operation) {
		this.operation = operation;
	}
	public String getOperationTips() {
		return operationTips;
	}
	public void setOperationTips(String operationTips) {
		this.operationTips = operationTips;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	private int id;
	private double price;
	private int direction;
	private String analyse;
	private Date beginTime;
	private int tempId=0;
	public int getTempId() {
		return tempId;
	}
	public void setTempId(int tempId) {
		this.tempId = tempId;
	}
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
	private String title;
	private Operation operation=new Operation();
	private String operationTips;
	private int state;
}
