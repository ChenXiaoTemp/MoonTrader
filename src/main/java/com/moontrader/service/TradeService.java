package com.moontrader.service;

import java.io.IOException;
import java.util.List;

import com.moontrader.dto.Trade;

public interface TradeService {
	public void insert(Trade trade)throws ServiceException;
	public void delete(int id)throws ServiceException;
	public void update(Trade trade)throws ServiceException;
	public Trade get(int id) throws ServiceException;
	public List<Trade> list()throws ServiceException;
	public List<Trade> listHistory() throws ServiceException;
}
