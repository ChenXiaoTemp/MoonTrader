package com.moontrader.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.moontrader.entities.CommentEntity;
@Repository
public class CommentDao extends Dao<CommentEntity, Integer> {
	@Autowired
	public CommentDao(SessionFactory sessionFactory) {
		super(CommentEntity.class, sessionFactory);
	}
}
