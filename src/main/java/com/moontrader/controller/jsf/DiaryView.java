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

import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;

import com.moontrader.dto.Attachment;
import com.moontrader.dto.Currency;
import com.moontrader.dto.Diary;
import com.moontrader.service.CurrencyService;
import com.moontrader.service.DiaryService;
import com.moontrader.service.ServiceException;
import com.moontrader.util.Helper;

@ManagedBean
@ViewScoped
public class DiaryView implements Serializable{
	private static final long serialVersionUID = -141534347516571225L;
	private String viewId=UUID.randomUUID().toString();
	public String getViewId() {
		return viewId;
	}
	public void setViewId(String viewId) {
		this.viewId = viewId;
	}
	public List<Diary> getDiaries() {
		return diaries;
	}
	public void setDiaries(List<Diary> diaries) {
		this.diaries = diaries;
	}
	public Diary getDiary() {
		return diary;
	}
	public void setDiary(Diary diary) {
		this.diary = diary;
	}
	public DiaryService getDiaryService() {
		return diaryService;
	}
	public void setDiaryService(DiaryService diaryService) {
		this.diaryService = diaryService;
	}
	@ManagedProperty("#{loginView}")
	private LoginView loginView;
	public LoginView getLoginView() {
		return loginView;
	}
	public void setLoginView(LoginView loginView) {
		this.loginView = loginView;
	}
	private List<Diary> diaries;
	private List<Integer> tempAttachments=new ArrayList<Integer>();
	public List<Integer> getTempAttachments() {
		return tempAttachments;
	}
	public void setTempAttachments(List<Integer> tempAttachments) {
		this.tempAttachments = tempAttachments;
	}
	private Diary diary=new Diary();
	@ManagedProperty("#{attachmentBufferView}")
	private AttachmentBufferView attachmentBufferView;
	public AttachmentBufferView getAttachmentBufferView() {
		return attachmentBufferView;
	}
	public void setAttachmentBufferView(AttachmentBufferView attachmentBufferView) {
		this.attachmentBufferView = attachmentBufferView;
	}
	@ManagedProperty("#{diaryService}")
	private DiaryService diaryService;
	@ManagedProperty("#{currencyService}")
	private CurrencyService currencyService;
	public CurrencyService getCurrencyService() {
		return currencyService;
	}
	public void setCurrencyService(CurrencyService currencyService) {
		this.currencyService = currencyService;
	}
	private List<Currency> currencies;
	public List<Currency> getCurrencies() {
		return currencies;
	}
	public void setCurrencies(List<Currency> currencies) {
		this.currencies = currencies;
	}
	private Logger logger=Logger.getLogger(DiaryView.class);
	@PostConstruct
	public void init(){
		try {
			diaries=diaryService.list();
			currencies=currencyService.list(loginView.getUser());
			diary.setUser(loginView.getUser());
		} catch (ServiceException e) {
			Helper.logException(e, logger);
		}
		
	}
	public void onSubmit() throws ServiceException{
		diaryService.insert(diary);
		diary=new Diary();
		this.attachmentBufferView.clear(viewId);
		this.tempAttachments.clear();
		diary.setUser(loginView.getUser());
		this.diaries=diaryService.list();
	}
	public void uploadAttachmentFile(FileUploadEvent event) throws IOException {
		Attachment attachment=Helper.extractAttachment(event.getFile());
		diary.addAttachment(attachment);
		if(attachment.getContentType().matches("image/.*")){
			this.tempAttachments.add(attachmentBufferView.addAttachment(viewId, attachment));
		}
	}
}
