package com.moontrader.controller.jsf;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.FileUploadEvent;

import com.moontrader.dto.Attachment;
import com.moontrader.dto.News;
import com.moontrader.service.NewsService;
import com.moontrader.service.ServiceException;
import com.moontrader.util.Helper;

@ManagedBean
@ViewScoped
public class NewsView implements Serializable,EventListener {
	private static final long serialVersionUID = -4137139350353460413L;
	private String viewId=UUID.randomUUID().toString();
	public String getViewId() {
		return viewId;
	}
	public void setViewId(String viewId) {
		this.viewId = viewId;
	}
	private List<News> news = new ArrayList<News>();

	public List<News> getNews() {
		return news;
	}

	public void setNews(List<News> news) {
		this.news = news;
	}

	@ManagedProperty("#{newsService}")
	private NewsService newsService;
	
	private List<Integer> tempAttachments=new ArrayList<Integer>();
	
	public List<Integer> getTempAttachments() {
		return tempAttachments;
	}

	public void setTempAttachments(List<Integer> tempAttachments) {
		this.tempAttachments = tempAttachments;
	}

	private News item=new News();
	
	public News getItem() {
		return item;
	}

	public void setItem(News item) {
		this.item = item;
	}

	public NewsService getNewsService() {
		return newsService;
	}

	public void setNewsService(NewsService newsService) {
		this.newsService = newsService;
	}
	@PostConstruct
	public void initialize()  {
		try {
			this.labelView.addEventListener(this);
			this.news = newsService.list();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	public void prepareInsertNews(){
		this.item=new News();
	}
	@ManagedProperty("#{labelView}")
	private LabelView labelView;
	public LabelView getLabelView() {
		return labelView;
	}
	public void setLabelView(LabelView labelView) {
		this.labelView = labelView;
	}
	@ManagedProperty("#{attachmentBufferView}")
	private AttachmentBufferView attachmentBufferView;
	public AttachmentBufferView getAttachmentBufferView() {
		return attachmentBufferView;
	}
	public void setAttachmentBufferView(AttachmentBufferView attachmentBufferView) {
		this.attachmentBufferView = attachmentBufferView;
	}
	public void onSubmit() throws ServiceException{
		newsService.insert(item);
		item=new News();
		this.attachmentBufferView.clear(viewId);
		this.tempAttachments.clear();
		this.news = newsService.list();
	}
	public void uploadAttachmentFile(FileUploadEvent event) throws IOException {
		Attachment attachment=Helper.extractAttachment(event.getFile());
		item.getAttachments().add(attachment);
		if(attachment.getContentType().matches("image/.*")){
			this.tempAttachments.add(attachmentBufferView.addAttachment(viewId, attachment));
		}
	}
	public void updateItem(){
		item.setLabels(this.labelView.getSelectedLabels());
	}
	@Override
	public int getType() {
		return Event.SUBMIT;
	}
	@Override
	public void handle(Event event) throws Exception {
		this.updateItem();
		if(this.updateIds!=null&&event.getUpdateIds()!=null && this.updateIds.equals(event.getUpdateIds())){
			this.newsService.update(item);
			this.news = newsService.list();
		}
	}
	@Override
	public String getId() {
		return this.viewId;
	}
	private String updateIds=null;
	public void preChangeLabels(News item,String updateIds) throws ServiceException{
		this.item=item;
		this.labelView.prepareLabels(item.getLabels(), updateIds);
		this.updateIds=updateIds;
	}
}
