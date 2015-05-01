package com.moontrader.service;

import com.moontrader.dto.Trade;
import com.moontrader.dto.TradeItem;

public interface TradeItemService {
	public void insert(TradeItem item,Trade trade);
	public void update(TradeItem item,Trade trade);
	public void delete(int id);
}
