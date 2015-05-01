package com.moontrader.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Comment implements Serializable{
	private static final long serialVersionUID = 4319699475790539043L;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public List<Attachment> getImages() {
		return images;
	}
	public void setImages(List<Attachment> images) {
		this.images = images;
	}
	private int id;
	private String content;
	private Date time=new Date();
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	private List<Attachment> attachments=new ArrayList<Attachment>();
	private List<Attachment> images=new ArrayList<Attachment>();
	public void addAttachment(Attachment attachment){
		this.attachments.add(attachment);
		if(attachment.getFilename().endsWith("(\\.jpe?g)|(\\.gif)|(\\.png)|(\\.tiff?)|(\\.bmp)")){
			this.images.add(attachment);
		}
	}
}
