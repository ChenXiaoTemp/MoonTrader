package com.moontrader.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.moontrader.entities.UserEntity;
import com.moontrader.entities.UserRoleEntity;
@Repository
public class UserDao extends Dao<UserEntity, Integer> {
	@Autowired
	public UserDao( SessionFactory sessionFactory) {
		super(UserEntity.class, sessionFactory);
	}
	public UserEntity findUserByEmail(String email){
		Session session = getSession();
		@SuppressWarnings("unchecked")
		List<UserEntity> entities=session.createCriteria(UserEntity.class).add(Restrictions.eq("email", email)).list();
		if(entities.isEmpty()){
			return null;
		}
		return entities.get(0);
	}
	@SuppressWarnings("unchecked")
	public List<UserEntity> listRegisterEntities(String name){
		Session session=getSession();
		List<UserRoleEntity> entities=session.createCriteria(UserRoleEntity.class).add(Restrictions.eq("name", name)).list();
	    List<UserEntity> users=new ArrayList<UserEntity>();
	    for(UserRoleEntity roleEntity:entities){
	    	users.add(roleEntity.getUser());
	    }
	    return users;
	}
}
