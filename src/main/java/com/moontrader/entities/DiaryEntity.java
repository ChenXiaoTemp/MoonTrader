package com.moontrader.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Diary")
public class DiaryEntity {
	public List<AttachmentEntity> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<AttachmentEntity> attachments) {
		this.attachments = attachments;
	}
	public UserEntity getUser() {
		return user;
	}
	public void setUser(UserEntity user) {
		this.user = user;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public CurrencyEntity getCurrency() {
		return currency;
	}
	public void setCurrency(CurrencyEntity currency) {
		this.currency = currency;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Id
	@Column(name = "ID")
	@GeneratedValue
	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	private Date time;
	@ManyToOne
	private CurrencyEntity currency;
	@Column(length=512)
	private String title;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Column(length=2048)
	private String content;
	@OneToMany(cascade = CascadeType.ALL)
	private List<AttachmentEntity> attachments;
	@OneToMany
	private List<NewsEntity> newsItems;
	public List<NewsEntity> getNewsItems() {
		return newsItems;
	}
	public void setNewsItems(List<NewsEntity> newsItems) {
		this.newsItems = newsItems;
	}
	@ManyToOne
	private UserEntity user;
}
