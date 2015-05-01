package com.moontrader.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.moontrader.entities.NewsEntity;
@Repository
public class NewsDao extends Dao<NewsEntity,Integer>{
	@Autowired
	public NewsDao( SessionFactory sessionFactory) {
		super(NewsEntity.class, sessionFactory);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<NewsEntity> list() {
		return getSession().createCriteria(getEntityClass()).addOrder(Order.desc("time")).list();
	}
}
