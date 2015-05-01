package com.moontrader.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.moontrader.entities.TradeEntity;
@Repository
public class TradeDao extends Dao<TradeEntity, Integer> {
	@Autowired
	public TradeDao(SessionFactory sessionFactory) {
		super(TradeEntity.class, sessionFactory);
	}
	
	@SuppressWarnings("unchecked")
	public List<TradeEntity> listHistory(){
		return getSession().createCriteria(getEntityClass()).add(Restrictions.ne("state", new Integer(0))).addOrder(Order.desc("beginTime")).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<TradeEntity> list(){
		return getSession().createCriteria(getEntityClass()).add(Restrictions.eq("state", new Integer(0))).addOrder(Order.desc("beginTime")).list();
	}

}
