package com.moontrader.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.moontrader.entities.DiaryEntity;

@Repository
public class DiaryDao extends Dao<DiaryEntity, Integer> {

	@SuppressWarnings("unchecked")
	@Override
	public List<DiaryEntity> list() {
		return getSession().createCriteria(getEntityClass()).addOrder(Order.desc("time")).list();
	}
	@Autowired
	public DiaryDao(SessionFactory sessionFactory) {
		super(DiaryEntity.class, sessionFactory);
	}

}
