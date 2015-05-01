package com.moontrader.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moontrader.dao.AttachmentDao;
import com.moontrader.dto.Attachment;
import com.moontrader.entities.AttachmentEntity;
import com.moontrader.service.AttachmentService;
import com.moontrader.service.ServiceException;
import com.moontrader.util.EntityUtils;
@Service("attachmentService")
public class AttachmentServiceImpl implements AttachmentService {
	@Autowired
	private AttachmentDao attachmentDao;
	public AttachmentDao getAttachmentDao() {
		return attachmentDao;
	}
	public void setAttachmentDao(AttachmentDao attachmentDao) {
		this.attachmentDao = attachmentDao;
	}
	@Transactional
	@Override
	public Attachment getAttachment(int id) throws ServiceException {
		AttachmentEntity entity=attachmentDao.findById(id);
		Attachment attachment;
		try {
			attachment = EntityUtils.entityToDto(entity);
			return attachment;
		} catch (IOException e) {
			throw new ServiceException(e);
		}
		
	}

}
