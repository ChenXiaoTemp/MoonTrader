package com.moontrader.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Diary implements Serializable{
	private static final long serialVersionUID = -7976655669558123304L;
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public Currency getCurrency() {
		return currency;
	}
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	private Date time=new Date();
	private Currency currency=new Currency();
	private String content;
	private String title;
	private User user;
	private List<News> newsItems=new ArrayList<News>();
	public List<News> getNewsItems() {
		return newsItems;
	}
	public void setNewsItems(List<News> newsItems) {
		this.newsItems = newsItems;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	private List<Attachment> images=new ArrayList<Attachment>();
	public List<Attachment> getImages() {
		return images;
	}
	public void setImages(List<Attachment> images) {
		this.images = images;
	}
	private List<Attachment> attachments=new ArrayList<Attachment>();
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
	public void addAttachment(Attachment attachment){
		this.attachments.add(attachment);
		if(attachment.getContentType().matches("image/.*")){
			this.images.add(attachment);
		}
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
