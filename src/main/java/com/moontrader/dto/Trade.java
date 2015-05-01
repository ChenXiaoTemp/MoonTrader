package com.moontrader.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Trade implements Serializable{
	private static final long serialVersionUID = -1581914358817261347L;
	private int id;
	private String title;
	private String analyse;
	private int state;
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getAnalyse() {
		return analyse;
	}
	public void setAnalyse(String analyse) {
		this.analyse = analyse;
	}
	private Date createTime=new Date();
	private Date beginTime=new Date();
	private Date endTime;
	private Currency currency=new Currency();
	private List<TradeItem> items=new ArrayList<TradeItem>();
	private List<Comment> comments=new ArrayList<Comment>();
	private List<Attachment> attachments=new ArrayList<Attachment>();
	private List<Attachment> images=new ArrayList<Attachment>();
	private List<News> newsItems=new ArrayList<News>();
	private List<Label> labels=new ArrayList<Label>();
	public List<Label> getLabels() {
		return labels;
	}
	public void setLabels(List<Label> labels) {
		this.labels = labels;
	}
	public List<News> getNewsItems() {
		return newsItems;
	}
	public void setNewsItems(List<News> newsItems) {
		this.newsItems = newsItems;
	}
	public List<Attachment> getImages() {
		return images;
	}
	public void setImages(List<Attachment> images) {
		this.images = images;
	}
	public List<Attachment> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
		for(Attachment attachment:attachments){
			if(attachment.getContentType().matches("image/.*")){
				this.images.add(new Image(attachment));
			}
		}
	}
	private User owner;
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public Currency getCurrency() {
		System.out.println("ddddasdfasdfasdfas:"+currency.getName());
		return currency;
	}
	public Date getEndTime() {
		return endTime;
	}
	public int getId() {
		return id;
	}
	public List<TradeItem> getItems() {
		return items;
	}
	public String getTitle() {
		return title;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setItems(List<TradeItem> items) {
		this.items = items;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void addItem(TradeItem item){
		this.items.add(item);
	}
	public void addAttachment(Attachment attachment){
		this.attachments.add(attachment);
		if(attachment.getContentType().matches("image/.*")){
			this.images.add(attachment);
		}
	}
}
