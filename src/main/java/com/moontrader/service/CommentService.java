package com.moontrader.service;

import com.moontrader.dto.Comment;
import com.moontrader.dto.Trade;

public interface CommentService {
	public void addComment(Comment comment,Trade trade) throws ServiceException;
}
