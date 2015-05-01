package com.moontrader.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moontrader.dao.CurrencyDao;
import com.moontrader.dao.DiaryDao;
import com.moontrader.dao.UserDao;
import com.moontrader.dto.Diary;
import com.moontrader.entities.CurrencyEntity;
import com.moontrader.entities.DiaryEntity;
import com.moontrader.service.DiaryService;
import com.moontrader.service.ServiceException;
import com.moontrader.util.EntityUtils;

@Service("diaryService")
public class DiaryServiceImpl implements DiaryService {
	public CurrencyDao getCurrencyDao() {
		return currencyDao;
	}

	public void setCurrencyDao(CurrencyDao currencyDao) {
		this.currencyDao = currencyDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	@Autowired
	private CurrencyDao currencyDao;
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private UserDao userDao;
	@Autowired
	private DiaryDao diaryDao;
	public DiaryDao getDiaryDao() {
		return diaryDao;
	}

	public void setDiaryDao(DiaryDao diaryDao) {
		this.diaryDao = diaryDao;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	@Transactional
	@Override
	public void insert(Diary diary) throws ServiceException {
		DiaryEntity entity=new DiaryEntity();
		entity.setContent(diary.getContent());
		entity.setUser(userDao.findById(diary.getUser().getId()));
		entity.setCurrency(currencyDao.findById(diary.getCurrency().getId()));
		entity.setTime(diary.getTime());
		entity.setTitle(diary.getTitle());
		try {
			entity.setAttachments(EntityUtils.dtoToEntity(diary.getAttachments()));
			diaryDao.saveOrUpdate(entity);
		} catch (IOException e) {
			throw new ServiceException(e);
		}
		
	}
	@Transactional
	@Override
	public void update(Diary diary) {
		DiaryEntity entity=new DiaryEntity();
		entity.setContent(diary.getContent());
		entity.setCurrency(EntityUtils.copyProperties(diary, CurrencyEntity.class));
		entity.setTime(diary.getTime());
		entity.setId(diary.getId());
		sessionFactory.getCurrentSession().save(entity);
	}
	@Transactional
	@Override
	public void delete(int id) {
		DiaryEntity entity=new DiaryEntity();
		entity.setId(id);
		sessionFactory.getCurrentSession().delete(entity);
	}
	@Transactional
	@Override
	public List<Diary> list() throws ServiceException {
		List<DiaryEntity> entities=diaryDao.list();
		List<Diary> result=new ArrayList<Diary>();
		for(DiaryEntity entity:entities){
			try {
				result.add(EntityUtils.entityToDto(entity));
			} catch (IOException e) {
				throw new ServiceException(e);
			}
		}
		return result;
	}

}
