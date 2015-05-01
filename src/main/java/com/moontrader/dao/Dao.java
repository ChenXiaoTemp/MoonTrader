package com.moontrader.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
public class Dao<E, ID extends Serializable> {
	private final Class<E> entityClass;
	protected SessionFactory sessionFactory;

	public Dao(Class<E> entityClass, SessionFactory sessionFactory) {
		this.entityClass = entityClass;
		this.sessionFactory = sessionFactory;
	}
	
	protected Session getSession(){
		return sessionFactory.getCurrentSession();
	}

	public Class<E> getEntityClass() {
		return entityClass;
	}

	@SuppressWarnings({ "unchecked" })
	public E findById(ID id) {
		Object obj = null;
		try {
			obj = getSession().get(getEntityClass(), id);
		} catch (ObjectNotFoundException e) {
			return null;
		}
		return (E) obj;
	}
	
	public void saveOrUpdate(E e){
		getSession().saveOrUpdate(e);
	}
	
	public void delete(E e){
		getSession().delete(e);
	}
	
	@SuppressWarnings("unchecked")
	public List<E> list(){
		return getSession().createCriteria(getEntityClass()).list();
	}
}
