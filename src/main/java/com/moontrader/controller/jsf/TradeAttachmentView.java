package com.moontrader.controller.jsf;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.apache.log4j.Logger;
import org.primefaces.model.StreamedContent;

import com.moontrader.dto.Attachment;
import com.moontrader.service.AttachmentService;
import com.moontrader.util.Assert;
import com.moontrader.util.Helper;
@ManagedBean
@RequestScoped
public class TradeAttachmentView {
	public AttachmentService getAttachmentService() {
		return attachmentService;
	}

	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}

	public AttachmentBufferView getAttachmentBufferView() {
		return attachmentBufferView;
	}

	public void setAttachmentBufferView(AttachmentBufferView attachmentBufferView) {
		this.attachmentBufferView = attachmentBufferView;
	}

	private StreamedContent streamedContent;
	@ManagedProperty("#{attachmentService}")
	private AttachmentService attachmentService;
	@ManagedProperty("#{attachmentBufferView}")
	private AttachmentBufferView attachmentBufferView;
	public StreamedContent getStreamedContent() {
		return streamedContent;
	}

	public void setStreamedContent(StreamedContent streamedContent) {
		this.streamedContent = streamedContent;
	}
	private Logger logger=Logger.getLogger(TradeAttachmentView.class);
	@PostConstruct
	public void init(){
		try{
			String diaryIdS=Helper.getRequestParam("trade_id");
			String attachmentTempIdS=Helper.getRequestParam("attachment_id");
			String viewId=Helper.getRequestParam("view_id");
			int diaryId=Integer.valueOf(diaryIdS);
			int attachmentId=Integer.valueOf(attachmentTempIdS);
			Attachment attachment=null;
			if(diaryId==0){
				logger.debug("viewId"+viewId);
				attachment=attachmentBufferView.getAttachment(viewId, Integer.valueOf(attachmentTempIdS));
			}
			else{
				attachment=attachmentService.getAttachment(attachmentId);
			}
			this.streamedContent=attachment.getStreamContent();
			Assert.assertNotNull(streamedContent, "Content");
		}
		catch(Exception e){
			this.streamedContent=Helper.createInvalidImage();
		}
	}
}
