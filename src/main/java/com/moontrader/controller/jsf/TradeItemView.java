package com.moontrader.controller.jsf;

import java.io.Serializable;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.moontrader.dto.Operation;
import com.moontrader.dto.TradeItem;

@ManagedBean
@ViewScoped
public class TradeItemView implements Serializable{
	private static final long serialVersionUID = -7686852158227934928L;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Operation getOperation() {
		return operation;
	}
	public void setOperation(Operation operation) {
		this.operation = operation;
	}
	private String title;
	private Double price;
	private Operation operation;
	private String analyse;
	private Date beginTime;
	private String operationTips;
	public String getOperationTips() {
		return operationTips;
	}
	public void setOperationTips(String operationTips) {
		this.operationTips = operationTips;
	}
	private int state;
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	private Date endTime;
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	private TradeItem item=null;
	public TradeItem getItem() {
		return item;
	}
	public void setItem(TradeItem item) {
		this.item = item;
	}
	public String getAnalyse() {
		return analyse;
	}
	public void setAnalyse(String analyse) {
		this.analyse = analyse;
	}
	public void prepare(TradeItem item){
		this.title=item.getTitle();
		this.item=item;
		this.setPrice(item.getPrice());
		this.setOperation(item.getOperation());
	}
	public void clear(){
		this.title=null;
		this.price=null;
		this.operation=null;
		this.item=null;
	}
}
