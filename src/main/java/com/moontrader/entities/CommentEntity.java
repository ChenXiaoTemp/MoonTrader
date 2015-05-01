package com.moontrader.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="comment")
public class CommentEntity {
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
	public List<AttachmentEntity> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<AttachmentEntity> attachments) {
		this.attachments = attachments;
	}
	private Date time;
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	@Id
	@Column(name = "ID")
	@GeneratedValue
	private int id;
	@Column(length=2048)
	private String content;
	@OneToMany(cascade = CascadeType.ALL)
	private List<AttachmentEntity> attachments;
}
