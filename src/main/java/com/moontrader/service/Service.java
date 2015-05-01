package com.moontrader.service;

import java.util.List;

public interface Service<E> {
	public void insert(E diary) throws ServiceException;
	public void update(E diary)throws ServiceException;
	public void delete(int id)throws ServiceException;
	public List<E> list()throws ServiceException;
}
