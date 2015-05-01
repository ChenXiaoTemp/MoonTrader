package com.moontrader.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moontrader.dao.CurrencyDao;
import com.moontrader.dao.UserDao;
import com.moontrader.dto.Currency;
import com.moontrader.dto.User;
import com.moontrader.entities.CurrencyEntity;
import com.moontrader.service.CurrencyService;
import com.moontrader.util.Helper;

@Service("currencyService")
public class CurrencyServiceImpl implements CurrencyService {
	private Logger logger=Logger.getLogger(CurrencyServiceImpl.class);
	@Autowired
	private CurrencyDao dao;
	@Autowired
	private UserDao userDao;
	public UserDao getUserDao() {
		return userDao;
	}
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	@Transactional
	@Override
	public void insert(Currency currency,User user) {
		CurrencyEntity entity=new CurrencyEntity();
		entity.setName(currency.getName());
		entity.setUserEntity(userDao.findById(user.getId()));
		dao.saveOrUpdate(entity);
		currency.setId(entity.getId());
	}
	@Transactional
	@Override
	public void update(Currency currency,User user) {
		Helper.checkNull(currency, "Currency");
		Helper.checkLeZero(currency.getId(), "Currency Id");
		CurrencyEntity entity=new CurrencyEntity();
		entity.setName(currency.getName());
		entity.setId(currency.getId());
		entity.setUserEntity(userDao.findById(user.getId()));
		Helper.logObject(entity, logger);
		dao.saveOrUpdate(entity);
	}
	@Transactional
	@Override
	public void delete(int id) {
		CurrencyEntity entity=new CurrencyEntity();
		entity.setId(id);
		dao.delete(entity);
	}
	@Transactional
	@Override
	public List<Currency> list(User user) {
		List<CurrencyEntity> entities=dao.list(userDao.findById(user.getId()));
		List<Currency> result=new ArrayList<Currency>();
		for(CurrencyEntity entity:entities){
			Currency currency=new Currency();
			currency.setId(entity.getId());
			currency.setName(entity.getName());
			result.add(currency);
		}
		return result;
	}

}
