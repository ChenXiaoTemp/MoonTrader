package com.moontrader.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moontrader.dao.OperationDao;
import com.moontrader.dao.UserDao;
import com.moontrader.dto.Operation;
import com.moontrader.dto.User;
import com.moontrader.entities.OperationEntity;
import com.moontrader.service.OperationService;
import com.moontrader.util.EntityUtils;
import com.moontrader.util.Helper;

@Service("operationService")
public class OperationServiceImpl implements OperationService {
	@Override
	public Operation get(int id) {
		OperationEntity entity=operationDao.findById(id);
		return EntityUtils.entityToDto(entity);
	}
	Logger logger = Logger.getLogger(OperationServiceImpl.class);
	@Autowired
	private OperationDao operationDao;
	@Autowired
	private UserDao userDao;
	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public OperationDao getOperationDao() {
		return operationDao;
	}

	public void setOperationDao(OperationDao operationDao) {
		this.operationDao = operationDao;
	}
	@Transactional
	@Override
	public void insert(Operation operation,User user) {
		logger.debug("Debug:id"+operation.getId()+"\nname:"+operation.getName());
		OperationEntity entity=new OperationEntity();
		entity.setDescription("none");
		entity.setName(operation.getName());
		entity.setUserEntity(userDao.findById(user.getId()));
		operationDao.saveOrUpdate(entity);
		operation.setId(entity.getId());
	}
	@Transactional
	@Override
	public void update(Operation operation,User user) {
		Helper.checkNull(operation, "operation");
		Helper.checkLeZero(operation.getId(), "operation Id");
		Helper.logObject(operation, logger);
		OperationEntity entity=EntityUtils.dtoToEntity(operation);
		entity.setUserEntity(userDao.findById(user.getId()));
		Helper.logObject(entity, logger);
		logger.debug("Debug"+entity.getId());
		operationDao.saveOrUpdate(entity);
	}
	@Transactional
	@Override
	public void delete(int id) {
		OperationEntity entity=new OperationEntity();
		entity.setId(id);
		operationDao.delete(entity);
	}
	@Transactional
	@Override
	public List<Operation> list(User user) {
		List<OperationEntity> entities=operationDao.list(userDao.findById(user.getId()));
		List<Operation> result=new ArrayList<Operation>();
		for(OperationEntity entity:entities){
			Operation operation=EntityUtils.copyProperties(entity, Operation.class);
			result.add(operation);
		}
		return result;
	}

}
