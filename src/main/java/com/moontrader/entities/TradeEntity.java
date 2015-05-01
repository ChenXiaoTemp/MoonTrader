package com.moontrader.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "trade")
public class TradeEntity {
	@Id
	@Column(name = "ID")
	@GeneratedValue
	private int id;
	@Column(length=512)
	private String title;
	@Column(length=2048)
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
	private Date createTime;
	@ManyToOne
	@JoinColumn(name = "CURRENCY_ID")
	private CurrencyEntity currency;
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private UserEntity user;
	@OneToMany(cascade = CascadeType.ALL)
	private List<AttachmentEntity> attachments;
	
	@ManyToMany(cascade=CascadeType.MERGE)
	private List<LabelEntity> labels;
	public List<LabelEntity> getLabels() {
		return labels;
	}

	public void setLabels(List<LabelEntity> labels) {
		this.labels = labels;
	}

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
	public CurrencyEntity getCurrency() {
		return currency;
	}

	public void setCurrency(CurrencyEntity currency) {
		this.currency = currency;
	}

	@OneToMany(cascade = CascadeType.ALL)
	private List<TradeItemEntity> items;
	
	@OneToMany
	private List<CommentEntity> comments;
	
	@ManyToMany(cascade=CascadeType.MERGE)
	private List<NewsEntity> newsItems;
	
	public List<NewsEntity> getNewsItems() {
		return newsItems;
	}

	public void setNewsItems(List<NewsEntity> newsItems) {
		this.newsItems = newsItems;
	}

	public List<CommentEntity> getComments() {
		return comments;
	}

	public void setComments(List<CommentEntity> comments) {
		this.comments = comments;
	}

	public Date getCreateTime() {
		return createTime;
	}

    public int getId() {
		return id;
	}

	public List<TradeItemEntity> getItems() {
		return items;
	}

	public String getTitle() {
		return title;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setItems(List<TradeItemEntity> items) {
		this.items = items;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
