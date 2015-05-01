package com.moontrader.controller.jsf;

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
public class HistoryTradeListView {
	private Logger logger=Logger.getLogger(HistoryTradeListView.class);
	@ManagedProperty("#{tradeService}")
	private TradeService tradeService;
	public TradeService getTradeService() {
		return tradeService;
	}
	public void setTradeService(TradeService tradeService) {
		this.tradeService = tradeService;
	}
	private List<Trade> historyTrades;
	public List<Trade> getHistoryTrades() {
		return historyTrades;
	}
	public void setHistoryTrades(List<Trade> historyTrades) {
		this.historyTrades = historyTrades;
	}
	@PostConstruct
	public void init(){
		try {
			historyTrades=tradeService.listHistory();
		} catch (ServiceException e) {
		    Helper.logException(e, logger);
		}
	}
}
