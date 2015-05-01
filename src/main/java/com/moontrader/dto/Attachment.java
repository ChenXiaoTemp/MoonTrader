package com.moontrader.dto;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

public class Attachment implements Serializable {
	private static final long serialVersionUID = 3516063003701843707L;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private String filename;
	private String contentType;
	private boolean validFile;
	private String encoding;

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public boolean isValidFile() {
		return validFile;
	}

	public void setValidFile(boolean validFile) {
		this.validFile = validFile;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	private byte[] content;
	private StreamedContent streamContent;

	public StreamedContent getStreamContent() {
		return streamContent;
	}

	public void setStreamContent(StreamedContent streamContent) {
		this.streamContent = streamContent;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
		DefaultStreamedContent stream;
		try {
			stream = new DefaultStreamedContent(new ByteArrayInputStream(content),this.getContentType(),URLEncoder.encode(getFilename(), "UTF-8").replaceAll(
			        "\\+", "%20"));
			System.out.println("Content file name:"+this.getFilename());
			streamContent=stream;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	private int id;
}
