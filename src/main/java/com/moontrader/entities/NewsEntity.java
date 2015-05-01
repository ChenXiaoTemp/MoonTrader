package com.moontrader.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="news")
public class NewsEntity implements Serializable{
	private static final long serialVersionUID = -2468162417918872010L;

	@OneToMany(cascade = CascadeType.ALL)
	private List<AttachmentEntity> attachments;

	private Date beginTime;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<CommentEntity> comments;

	@Column(length = 4096)
	private String content;
	private Date endTime;

	@Id
	@Column(name = "ID")
	@GeneratedValue
	private int id;

	private int importance;

	@ManyToMany(cascade = CascadeType.MERGE)
	private List<LabelEntity> labels;

	private Date time;
	
	@Column(length=2048)
	private String title;
	private String url;
	public List<AttachmentEntity> getAttachments() {
		return attachments;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public List<CommentEntity> getComments() {
		return comments;
	}

	public String getContent() {
		return content;
	}

	public Date getEndTime() {
		return endTime;
	}

	public int getId() {
		return id;
	}

	public int getImportance() {
		return importance;
	}
	public List<LabelEntity> getLabels() {
		return labels;
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

	public void setAttachments(List<AttachmentEntity> attachments) {
		this.attachments = attachments;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public void setComments(List<CommentEntity> comments) {
		this.comments = comments;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void setImportance(int importance) {
		this.importance = importance;
	}
	

	public void setLabels(List<LabelEntity> labels) {
		this.labels = labels;
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
