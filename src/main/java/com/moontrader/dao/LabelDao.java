package com.moontrader.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.moontrader.entities.LabelEntity;
@Repository
public class LabelDao extends Dao<LabelEntity,Integer> {
	@Autowired
	public LabelDao(SessionFactory sessionFactory) {
		super(LabelEntity.class, sessionFactory);
	}
}
