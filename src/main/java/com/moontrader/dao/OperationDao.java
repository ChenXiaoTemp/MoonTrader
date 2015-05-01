package com.moontrader.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.moontrader.entities.OperationEntity;
import com.moontrader.entities.UserEntity;
@Repository
public class OperationDao extends Dao<OperationEntity, Integer> {
	@Autowired
	public OperationDao(SessionFactory sessionFactory) {
		super(OperationEntity.class, sessionFactory);
	}
	@SuppressWarnings("unchecked")
	public List<OperationEntity> list(UserEntity userEntity){
		Session session=getSession();
		return session.createCriteria(OperationEntity.class).add(Restrictions.eq("userEntity", userEntity)).list();
	}
}
