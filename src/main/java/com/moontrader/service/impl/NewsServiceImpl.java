package com.moontrader.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moontrader.dao.LabelDao;
import com.moontrader.dao.NewsDao;
import com.moontrader.dto.News;
import com.moontrader.entities.LabelEntity;
import com.moontrader.entities.NewsEntity;
import com.moontrader.service.NewsService;
import com.moontrader.service.ServiceException;
import com.moontrader.util.EntityUtils;
import com.moontrader.util.Helper;

@Service("newsService")
public class NewsServiceImpl implements NewsService {
	private Logger logger = Logger.getLogger(NewsServiceImpl.class);
	@Autowired
	private NewsDao newsDao;
	@Autowired
	private LabelDao labelDao;

	public LabelDao getLabelDao() {
		return labelDao;
	}
	public void setLabelDao(LabelDao labelDao) {
		this.labelDao = labelDao;
	}
	public NewsDao getNewsDao() {
		return newsDao;
	}
	public void setNewsDao(NewsDao newsDao) {
		this.newsDao = newsDao;
	}
	@Transactional
	@Override
	public void insert(News news) throws ServiceException {
		try {
			NewsEntity newsEntity=EntityUtils.dtoToEntity(news);
			List<LabelEntity> labels=newsEntity.getLabels();
			for(LabelEntity label:labels){
				if(label.getId()==0){
					labelDao.saveOrUpdate(label);
				}
			}
			newsDao.saveOrUpdate(newsEntity);
			System.out.println(newsEntity.getId());
		} catch (IOException e) {
			throw Helper.logException(new ServiceException(e), logger);
		}
	}
	@Transactional
	@Override
	public void update(News news) throws ServiceException {
		try {
			NewsEntity newsEntity=EntityUtils.dtoToEntity(news);
			List<LabelEntity> labels=newsEntity.getLabels();
			for(LabelEntity label:labels){
				if(label.getId()==0){
					labelDao.saveOrUpdate(label);
				}
			}
			newsDao.saveOrUpdate(newsEntity);
		} catch (IOException e) {
			throw Helper.logException(new ServiceException(e), logger);
		}
	}
	@Transactional
	@Override
	public void delete(int id) throws ServiceException {
		NewsEntity newsEntity = new NewsEntity();
		newsEntity.setId(id);
		newsDao.delete(newsEntity);
	}
	@Transactional
	@Override
	public List<News> list() throws ServiceException {
		List<NewsEntity> entities = newsDao.list();
		List<News> result = new ArrayList<News>();
		try {
			for (NewsEntity entity : entities) {
				result.add(EntityUtils.entityToDto(entity));
			}
			return result;
		} catch (IOException e) {
			throw Helper.logException(new ServiceException(e), logger);
		}
	}
}
