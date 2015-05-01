package com.moontrader.dto;

import org.primefaces.model.StreamedContent;


public class Image extends Attachment{
	private static final long serialVersionUID = -5715289694958354460L;
	@Override
	public int getId() {
		return attachment.getId();
	}
	@Override
	public void setId(int id) {
		attachment.setId(id);
	}
	@Override
	public String getContentType() {
		return attachment.getContentType();
	}
	@Override
	public void setContentType(String contentType) {
		attachment.setContentType(contentType);
	}
	@Override
	public StreamedContent getStreamContent() {
		return attachment.getStreamContent();
	}
	@Override
	public void setStreamContent(StreamedContent streamContent) {
		attachment.setStreamContent(streamContent);
	}
	@Override
	public byte[] getContent() {
		return attachment.getContent();
	}
	@Override
	public void setContent(byte[] content) {
		attachment.setContent(content);
	}
	@Override
	public String getFilename() {
		return attachment.getFilename();
	}
	@Override
	public void setFilename(String filename) {
		attachment.setFilename(filename);
	}
	Attachment attachment;
	public Image(Attachment attachment){
		this.attachment=attachment;
	}
}
