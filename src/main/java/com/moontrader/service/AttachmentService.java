package com.moontrader.service;

import com.moontrader.dto.Attachment;

public interface AttachmentService {
	public Attachment getAttachment(int id) throws ServiceException;
}
