package com.moontrader.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moontrader.dao.CurrencyDao;
import com.moontrader.dao.LabelDao;
import com.moontrader.dao.OperationDao;
import com.moontrader.dao.TradeDao;
import com.moontrader.dao.TradeItemDao;
import com.moontrader.dao.UserDao;
import com.moontrader.dto.Trade;
import com.moontrader.dto.TradeItem;
import com.moontrader.entities.LabelEntity;
import com.moontrader.entities.TradeEntity;
import com.moontrader.entities.TradeItemEntity;
import com.moontrader.service.ServiceException;
import com.moontrader.service.TradeService;
import com.moontrader.util.EntityUtils;
import com.moontrader.util.Helper;

@Service("tradeService")
public class TradeServiceImpl implements TradeService {
	@SuppressWarnings("unused")
	private static Logger logger=Logger.getLogger(TradeServiceImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private CurrencyDao currencyDao;
	@Autowired
	private OperationDao operationDao;
	@Autowired
	private TradeItemDao tradeItemDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private TradeDao tradeDao;
	@Autowired
	private LabelDao labelDao;
	public LabelDao getLabelDao() {
		return labelDao;
	}

	public void setLabelDao(LabelDao labelDao) {
		this.labelDao = labelDao;
	}

	public TradeDao getTradeDao() {
		return tradeDao;
	}

	public void setTradeDao(TradeDao tradeDao) {
		this.tradeDao = tradeDao;
	}

	public CurrencyDao getCurrencyDao() {
		return currencyDao;
	}

	public void setCurrencyDao(CurrencyDao currencyDao) {
		this.currencyDao = currencyDao;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	

	@Transactional
	@Override
	public void insert(Trade trade) throws ServiceException {
		TradeEntity entity=new TradeEntity();
		// set corresponding element.
		entity.setBeginTime(trade.getBeginTime());
		entity.setCreateTime(trade.getCreateTime());
		entity.setCurrency(currencyDao.findById(trade.getCurrency().getId()));
		entity.setUser(userDao.findById(trade.getOwner().getId()));
		entity.setEndTime(trade.getEndTime());
		entity.setTitle(trade.getTitle());
		entity.setAnalyse(trade.getAnalyse());
		List<TradeItem> items=trade.getItems();
		List<TradeItemEntity> itemEntities=EntityUtils.dtoToEntity(items, entity);
		entity.setItems(itemEntities);
		try {
			entity.setNewsItems(EntityUtils.newsDtosToEntities(trade.getNewsItems()));
			entity.setAttachments(EntityUtils.dtoToEntity(trade.getAttachments()));
			List<LabelEntity> labelsEntity=EntityUtils.labelDtoesToEntities(trade.getLabels());
			for(LabelEntity temp:labelsEntity){
				if(temp.getId()==0){
					labelDao.saveOrUpdate(temp);
				}
			}
			entity.setLabels(labelsEntity);
			tradeDao.saveOrUpdate(entity);
			trade.getCurrency().setName(entity.getCurrency().getName());
			for(int i=0;i<itemEntities.size();i++){
				items.get(i).setId(itemEntities.get(i).getId());
			}
			trade.setId(entity.getId());
		} catch (IOException e) {
			throw new ServiceException(e);
		}
		
	}
	@Transactional
	@Override
	public void delete(int id) {
		TradeEntity entity=tradeDao.findById(id);
		if(entity!=null)tradeDao.delete(entity);
	}
	@Transactional
	@Override
	public void update(Trade trade) throws ServiceException {
		Helper.checkNull(trade, "trade");
		Helper.checkLeZero(trade.getId(), "trade Id");
		TradeEntity entity=tradeDao.findById(trade.getId());
		// set corresponding element.
		List<TradeItem> items=trade.getItems();
		List<TradeItemEntity> itemEntities=EntityUtils.dtoToEntity(items, entity);
		tradeItemDao.saveOrUpdate(itemEntities);
		entity.setBeginTime(trade.getBeginTime());
		entity.setCreateTime(trade.getCreateTime());
		entity.setCurrency(currencyDao.findById(trade.getCurrency().getId()));
		entity.setEndTime(trade.getEndTime());
		entity.setTitle(trade.getTitle());
		entity.setState(trade.getState());
		entity.setItems(itemEntities);
		try {
			entity.setNewsItems(EntityUtils.newsDtosToEntities(trade.getNewsItems()));
			List<LabelEntity> labelsEntity=EntityUtils.labelDtoesToEntities(trade.getLabels());
			for(LabelEntity temp:labelsEntity){
				if(temp.getId()==0){
					labelDao.saveOrUpdate(temp);
				}
			}
			entity.setLabels(labelsEntity);
			tradeDao.saveOrUpdate(entity);
		} catch (Exception e) {
			throw Helper.logException(new ServiceException(e), logger);
		}
		
	}
	@Transactional
	@Override
	public List<Trade> list() throws ServiceException {
		try{
			List<TradeEntity> entities=tradeDao.list();
			List<Trade> result=new ArrayList<Trade>();
			for(TradeEntity entity:entities){
				Trade trade=EntityUtils.entityToDto(entity);
				result.add(trade);
			}
			return result;
		}
		catch(IOException e){
			throw new ServiceException(e);
		}
		
	}

	@Transactional
	@Override
	public Trade get(int id) throws ServiceException {
		TradeEntity entity=tradeDao.findById(id);
		if(entity==null){
			return null;
		}
		try {
			return EntityUtils.entityToDto(entity);
		} catch (IOException e) {
			throw new ServiceException(e);
		}
	}
	@Transactional
	@Override
	public List<Trade> listHistory() throws ServiceException {
		try{
			List<TradeEntity> entities=tradeDao.listHistory();
			List<Trade> result=new ArrayList<Trade>();
			for(TradeEntity entity:entities){
				Trade trade=EntityUtils.entityToDto(entity);
				result.add(trade);
			}
			return result;
		}
		catch(IOException e){
			throw new ServiceException(e);
		}
	}

}
