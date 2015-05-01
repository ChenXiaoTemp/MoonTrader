package com.moontrader.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class News implements Serializable{
	private static final long serialVersionUID = 7813455382215384457L;

	private List<Attachment> attachments = new ArrayList<Attachment>();

	private List<Comment> comments = new ArrayList<Comment>();

	private String content;

	private int id;
	
	private Date beginTime=new Date();
	
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

	private List<Attachment> images = new ArrayList<Attachment>();
	
	private List<Label> labels=new ArrayList<Label>();

	public List<Label> getLabels() {
		return labels;
	}

	public void setLabels(List<Label> labels) {
		this.labels = labels;
	}

	private int importance;

	private Date time=new Date();

	private String title;

	private String url;

	public void addAttachment(Attachment attachment) {
		this.attachments.add(attachment);
		if (attachment.getContentType().matches("image/.*")) {
			this.images.add(attachment);
		}
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public String getContent() {
		return content;
	}

	public int getId() {
		return id;
	}

	public List<Attachment> getImages() {
		return images;
	}

	public int getImportance() {
		return importance;
	}

	public Date getTime() {
		return time;
	}

	public String getTitle() {
		return title;
	}

	public String getUrl() {
		return url;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
		for (Attachment attachment : attachments) {
			if (attachment.getContentType().matches("image/.*")) {
				this.images.add(new Image(attachment));
			}
		}
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setImages(List<Attachment> images) {
		this.images = images;
	}

	public void setImportance(int importance) {
		this.importance = importance;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
