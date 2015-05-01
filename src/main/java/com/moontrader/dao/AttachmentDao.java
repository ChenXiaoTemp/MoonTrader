package com.moontrader.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.moontrader.entities.AttachmentEntity;
@Repository
public class AttachmentDao extends Dao<AttachmentEntity, Integer> {
	@Autowired
	public AttachmentDao(SessionFactory sessionFactory) {
		super(AttachmentEntity.class, sessionFactory);
	}

}
