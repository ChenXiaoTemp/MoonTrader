package com.moontrader.service.impl;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moontrader.dao.CommentDao;
import com.moontrader.dao.TradeDao;
import com.moontrader.dto.Comment;
import com.moontrader.dto.Trade;
import com.moontrader.entities.CommentEntity;
import com.moontrader.entities.TradeEntity;
import com.moontrader.service.CommentService;
import com.moontrader.service.ServiceException;
import com.moontrader.util.EntityUtils;

@Service("commentService")
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentDao commentDao;
	@Autowired
	private TradeDao tradeDao;
	public CommentDao getCommentDao() {
		return commentDao;
	}
	public void setCommentDao(CommentDao commentDao) {
		this.commentDao = commentDao;
	}
	@Transactional
	@Override
	public void addComment(Comment comment, Trade trade) throws ServiceException {
		try {
			TradeEntity tradeEntity=tradeDao.findById(trade.getId());
			CommentEntity commentEntity=EntityUtils.dtoToEntity(comment);
			commentDao.saveOrUpdate(commentEntity);
			if(tradeEntity.getComments()==null)tradeEntity.setComments(new ArrayList<CommentEntity>());
			tradeEntity.getComments().add(commentEntity);
		} catch (IOException e) {
			throw new ServiceException(e);
		}
	}

}
