package com.moontrader.controller.jsf;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;

import com.moontrader.dto.Attachment;
import com.moontrader.dto.Comment;
import com.moontrader.dto.Currency;
import com.moontrader.dto.News;
import com.moontrader.dto.Operation;
import com.moontrader.dto.Trade;
import com.moontrader.dto.TradeItem;
import com.moontrader.service.CurrencyService;
import com.moontrader.service.OperationService;
import com.moontrader.service.ServiceException;
import com.moontrader.service.TradeItemService;
import com.moontrader.service.TradeService;
import com.moontrader.util.Helper;

@ManagedBean
@ViewScoped
public class TradeView implements Serializable{
	private class NewsEventHandler implements EventListener{
		
		@Override
		public int getType() {
			return Event.UPDATE_NEWS;
		}

		@SuppressWarnings("unchecked")
		@Override
		public void handle(Event event) throws Exception {
			TradeView.this.updateNews((List<News>)event.getData());
		}

		@Override
		public String getId() {
			return TradeView.this.viewId;
		}
		
	}
	private String viewId=UUID.randomUUID().toString();
	public String getViewId() {
		return viewId;
	}

	public void setViewId(String viewId) {
		this.viewId = viewId;
	}

	private static final long serialVersionUID = 3933936821101546642L;

	public Trade getTrade() {
		return trade;
	}

	public void setTrade(Trade trade) {
		this.trade = trade;
	}

	public TradeItem getTradeItem() {
		return tradeItem;
	}

	public void setTradeItem(TradeItem tradeItem) {
		this.tradeItem = tradeItem;
	}

	public List<Currency> getCurrencies() {
		return currencies;
	}

	public void setCurrencies(List<Currency> currencies) {
		this.currencies = currencies;
	}

	// data model.
	private Trade trade=new Trade();

	private TradeItem tradeItem=new TradeItem();

	private Comment comment=new Comment();

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}
    public void updateNews(List<News> selectedNews){
    	this.trade.setNewsItems(selectedNews);
    }
	private List<Currency> currencies;
	private List<Operation> operations;

	@ManagedProperty("#{tradeService}")
	private TradeService tradeService;

	@ManagedProperty("#{tradeItemService}")
	private TradeItemService tradeItemService;
	@ManagedProperty("#{currencyService}")
	private CurrencyService currencyService;
	@ManagedProperty("#{loginView}")
	private LoginView loginView;
	
	@ManagedProperty("#{operationService}")
	private OperationService operationService;

	public List<Operation> getOperations() {
		return operations;
	}

	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}

	public OperationService getOperationService() {
		return operationService;
	}

	public void setOperationService(OperationService operationService) {
		this.operationService = operationService;
	}

	public LoginView getLoginView() {
		return loginView;
	}

	public void setLoginView(LoginView loginView) {
		this.loginView = loginView;
	}

	public CurrencyService getCurrencyService() {
		return currencyService;
	}

	public void setCurrencyService(CurrencyService currencyService) {
		this.currencyService = currencyService;
	}

	public TradeItemService getTradeItemService() {
		return tradeItemService;
	}

	public void setTradeItemService(TradeItemService tradeItemService) {
		this.tradeItemService = tradeItemService;
	}

	public TradeService getTradeService() {
		return tradeService;
	}

	public void setTradeService(TradeService tradeService) {
		this.tradeService = tradeService;
	}

	public void insertItem() {
		this.tradeItem=new TradeItem();
	}
	@ManagedProperty("#{newsSelectedView}")
	private NewsSelectedView newsSelectedView;
	public NewsSelectedView getNewsSelectedView() {
		return newsSelectedView;
	}

	public void setNewsSelectedView(NewsSelectedView newsSelectedView) {
		this.newsSelectedView = newsSelectedView;
	}

	public void prepareAddNews(String updateIds) throws ServiceException{
		this.newsSelectedView.prepareAvailableNews(this.trade.getNewsItems(),updateIds);
	}

	public void changeItem(TradeItem item) {
		this.tradeItem=item;
	}

	public void deleteItem(TradeItem item) {
		trade.getItems().remove(item);
	}

	public String create() throws ServiceException {
		if (trade.getId() == 0) {
			trade.setOwner(loginView.getUser());
			tradeService.insert(trade);
		} else {
			tradeService.update(trade);
		}
		return "/faces/trade/tradeList.xhtml";
	}

	public void onComplete() {
		logger.debug("OnComplete");
		for(Operation op:operations){
			if(tradeItem.getOperation().getId()==op.getId()){
				tradeItem.setOperation(op);
			}
		}
		for(int i=0;i<trade.getItems().size();i++){
			TradeItem item=trade.getItems().get(i);
			if(item==tradeItem){
				logger.debug("Find the item.");
				return;
			}
		}
		Helper.logObject(this.tradeItem, logger);
		this.trade.addItem(this.tradeItem);
	}
	@ManagedProperty("#{attachmentBufferView}")
	private AttachmentBufferView attachmentBufferView;
	public AttachmentBufferView getAttachmentBufferView() {
		return attachmentBufferView;
	}

	public void setAttachmentBufferView(AttachmentBufferView attachmentBufferView) {
		this.attachmentBufferView = attachmentBufferView;
	}

	@PostConstruct
	public void init() {
		this.currencies = currencyService.list(loginView.getUser());
		this.operations=operationService.list(loginView.getUser());
		this.newsSelectedView.addEventListener(new NewsEventHandler());
		logger.debug("Init Trade View.");
	}
	private List<Integer> tempAttachments=new ArrayList<Integer>();
	public List<Integer> getTempAttachments() {
		return tempAttachments;
	}

	public void setTempAttachments(List<Integer> tempAttachments) {
		this.tempAttachments = tempAttachments;
	}

	public void uploadAttachmentFile(FileUploadEvent event) throws IOException {
		Attachment attachment=Helper.extractAttachment(event.getFile());
		trade.addAttachment(attachment);
		if(attachment.getContentType().matches("image/.*")){
			this.tempAttachments.add(attachmentBufferView.addAttachment(viewId, attachment));
		}
	}

	public void addComment() {

	}
	private Logger logger=Logger.getLogger(TradeView.class);
	public void onAddItem(){
		logger.debug("temp");
		this.trade.addItem(this.tradeItem);
		logger.debug("size:"+this.trade.getItems().size());
		Helper.logObject(this, logger);
	}

}
