package com.moontrader.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.moontrader.entities.CurrencyEntity;
import com.moontrader.entities.UserEntity;
@Repository
public class CurrencyDao extends Dao<CurrencyEntity,Integer>{
	@Autowired
	public CurrencyDao(	SessionFactory sessionFactory) {
		super(CurrencyEntity.class, sessionFactory);
	}
	
	@SuppressWarnings("unchecked")
	public List<CurrencyEntity> list(UserEntity userEntity){
		Session session=getSession();
		return session.createCriteria(CurrencyEntity.class).add(Restrictions.eq("userEntity", userEntity)).list();
	}
}
