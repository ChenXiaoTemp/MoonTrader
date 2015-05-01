package com.moontrader.service.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moontrader.dto.Trade;
import com.moontrader.dto.TradeItem;
import com.moontrader.entities.TradeItemEntity;
import com.moontrader.service.TradeItemService;
import com.moontrader.util.EntityUtils;
import com.moontrader.util.Helper;

@Service("tradeItemService")
public class TradeItemServiceImpl implements TradeItemService {
	@Autowired
	private SessionFactory sessionFactory;
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessioFactory) {
		this.sessionFactory = sessioFactory;
	}
	@Transactional
	@Override
	public void insert(TradeItem item, Trade trade) {
		TradeItemEntity entity=EntityUtils.copyProperties(item,TradeItemEntity.class);
		sessionFactory.getCurrentSession().save(entity);
	}
	@Transactional
	@Override
	public void update(TradeItem item, Trade trade) {
		Helper.checkNull(item, "item");
		Helper.checkLeZero(item.getId(), "item Id");
	}
	@Transactional
	@Override
	public void delete(int id) {

	}

}
