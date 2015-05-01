package com.moontrader.controller.jsf;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.moontrader.dto.Trade;
import com.moontrader.service.ServiceException;
import com.moontrader.service.TradeService;
import com.moontrader.util.Helper;

@ManagedBean
@ViewScoped
public class TradeListView implements Serializable{
	private static final long serialVersionUID = 162042561912217984L;
	private Logger logger=Logger.getLogger(TradeListView.class);
	private List<Trade> trades;
	@ManagedProperty("#{tradeService}")
	private TradeService tradeService;
	public TradeService getTradeService() {
		return tradeService;
	}
	public void setTradeService(TradeService tradeService) {
		this.tradeService = tradeService;
	}
	public List<Trade> getTrades() {
		return trades;
	}
	public void setTrades(List<Trade> trades) {
		this.trades = trades;
	}
	@PostConstruct
	public void init() {
		try {
			trades=tradeService.list();
		} catch (ServiceException e) {
			Helper.logException(e, logger);
		}
	}
}
