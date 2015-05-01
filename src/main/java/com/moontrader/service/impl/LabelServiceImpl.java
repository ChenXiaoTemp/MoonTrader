package com.moontrader.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moontrader.dao.LabelDao;
import com.moontrader.dto.Label;
import com.moontrader.entities.LabelEntity;
import com.moontrader.service.LabelService;
import com.moontrader.service.ServiceException;
import com.moontrader.util.EntityUtils;
@Service("labelService")
public class LabelServiceImpl implements LabelService {
	@Autowired
	private LabelDao labelDao;
	public LabelDao getLabelDao() {
		return labelDao;
	}
	public void setLabelDao(LabelDao labelDao) {
		this.labelDao = labelDao;
	}
	@Transactional
	@Override
	public void insert(Label label) throws ServiceException {
		labelDao.saveOrUpdate(EntityUtils.dtoToEntity(label));
	}
	@Transactional
	@Override
	public void update(Label label) throws ServiceException {
		labelDao.saveOrUpdate(EntityUtils.dtoToEntity(label));
	}
	@Transactional
	@Override
	public void delete(int id) throws ServiceException {
		LabelEntity entity=new LabelEntity();
		entity.setId(id);
		labelDao.delete(entity);
	}
	@Transactional
	@Override
	public List<Label> list() throws ServiceException {
		List<Label> result=new ArrayList<Label>();
		List<LabelEntity> entities=labelDao.list();
		for(LabelEntity entity:entities){
			result.add(EntityUtils.entityToDto(entity));
		}
		return result;
	}

}
