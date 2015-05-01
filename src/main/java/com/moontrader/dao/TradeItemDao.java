package com.moontrader.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.moontrader.entities.TradeItemEntity;
@Repository
public class TradeItemDao extends Dao<TradeItemEntity, Integer> {
	@Autowired
	public TradeItemDao(SessionFactory sessionFactory) {
		super(TradeItemEntity.class, sessionFactory);
	}
	public void saveOrUpdate(List<TradeItemEntity> entities){
		for(TradeItemEntity entity:entities){
			super.saveOrUpdate(entity);
		}
	}
}
