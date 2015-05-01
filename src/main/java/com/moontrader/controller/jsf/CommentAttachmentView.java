package com.moontrader.controller.jsf;

import java.io.Serializable;

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
public class CommentAttachmentView implements Serializable{
	private static final long serialVersionUID = 6620397286775321291L;

	@ManagedProperty("#{attachmentService}")
	private AttachmentService attachmentService;

	@ManagedProperty("#{attachmentBufferView}")
	private AttachmentBufferView attachmentBufferView;
	public AttachmentBufferView getAttachmentBufferView() {
		return attachmentBufferView;
	}

	public void setAttachmentBufferView(AttachmentBufferView attachmentBufferView) {
		this.attachmentBufferView = attachmentBufferView;
	}

	public AttachmentService getAttachmentService() {
		return attachmentService;
	}

	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}

	private StreamedContent image;
	private String filepath;
	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	Logger logger=Logger.getLogger(CommentAttachmentView.class);
	public StreamedContent getImage() {
		//Graphic Text
				
		return image;
	}

	public void setImage(StreamedContent image) {
		this.image = image;
	}
	
	@PostConstruct
	public void init(){
		try{
			String commentIdS=Helper.getRequestParam("comment_id");
			String attachmentTempIdS=Helper.getRequestParam("attachment_id");
			String viewId=Helper.getRequestParam("view_id");
			int commentId=Integer.valueOf(commentIdS);
			int attachmentId=Integer.valueOf(attachmentTempIdS);
			Attachment attachment=null;
			if(commentId==0){
				logger.debug("viewId"+viewId);
				attachment=attachmentBufferView.getAttachment(viewId, Integer.valueOf(attachmentTempIdS));
			}
			else{
				attachment=attachmentService.getAttachment(attachmentId);
			}
			this.image=attachment.getStreamContent();
			Assert.assertNotNull(image, "Content");
		}
		catch(Exception e){
			this.image=Helper.createInvalidImage();
		}
	}
	
	
}
