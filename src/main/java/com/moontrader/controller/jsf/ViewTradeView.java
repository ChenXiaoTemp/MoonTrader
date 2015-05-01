package com.moontrader.controller.jsf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.moontrader.dto.Attachment;
import com.moontrader.dto.Comment;
import com.moontrader.dto.News;
import com.moontrader.dto.Trade;
import com.moontrader.service.CommentService;
import com.moontrader.service.ServiceException;
import com.moontrader.service.TradeService;
import com.moontrader.util.Helper;

@ManagedBean
@ViewScoped
public class ViewTradeView implements EventListener{
	private String viewId = UUID.randomUUID().toString();
	
	@ManagedProperty("#{labelView}")
	private LabelView labelView;
	
	public LabelView getLabelView() {
		return labelView;
	}

	public void setLabelView(LabelView labelView) {
		this.labelView = labelView;
	}

	public String getViewId() {
		return viewId;
	}

	public void setViewId(String viewId) {
		this.viewId = viewId;
	}

	private StreamedContent graphicText;

	public StreamedContent getGraphicText() {
		return graphicText;
	}

	public void setGraphicText(StreamedContent graphicText) {
		this.graphicText = graphicText;
	}

	private static Logger logger = Logger.getLogger(ViewTradeView.class);
	private Comment comment = new Comment();
	@ManagedProperty("#{tradeService}")
	private TradeService tradeService;
	@ManagedProperty("#{commentService}")
	private CommentService commentService;

	public CommentService getCommentService() {
		return commentService;
	}

	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}

	private boolean editComment = false;

	public boolean getEditComment() {
		return editComment;
	}

	public void setEditComment(boolean editComment) {
		this.editComment = editComment;
	}

	public TradeService getTradeService() {
		return tradeService;
	}

	public void setTradeService(TradeService tradeService) {
		this.tradeService = tradeService;
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	public Trade getTrade() {
		return trade;
	}

	public void setTrade(Trade trade) {
		this.trade = trade;
	}

	public void beginComment() {
		this.comment = new Comment();
		this.editComment = true;
		this.attachmentBufferView.clear(viewId);
	}

	public void addComment() throws ServiceException {
		commentService.addComment(comment, trade);
		this.trade = tradeService.get(trade.getId());
		editComment = false;
	}

	private Trade trade;

	private List<Integer> tempAttachments = new ArrayList<Integer>();

	@ManagedProperty("#{attachmentBufferView}")
	private AttachmentBufferView attachmentBufferView;

	public AttachmentBufferView getAttachmentBufferView() {
		return attachmentBufferView;
	}

	public void setAttachmentBufferView(
			AttachmentBufferView attachmentBufferView) {
		this.attachmentBufferView = attachmentBufferView;
	}

	public List<Integer> getTempAttachments() {
		return tempAttachments;
	}

	public void setTempAttachments(List<Integer> tempAttachments) {
		this.tempAttachments = tempAttachments;
	}
	
	public void prepareLabelViews(String updateIds) throws ServiceException{
		this.labelView.prepareLabels(this.trade.getLabels(), updateIds);
	}

	@PostConstruct
	public void init() {
		try {
			HttpServletRequest request = (HttpServletRequest) FacesContext
					.getCurrentInstance().getExternalContext().getRequest();
			String idS = request.getParameter("trade.id");
			int id = Integer.valueOf(idS);
			this.trade = tradeService.get(id);
			this.labelView.addEventListener(this);
			this.newsSelectedView.addEventListener(new EventListenerImpl());
			return;
		} catch (ServiceException e) {
			Helper.logException(e, logger);
		} catch (Exception e) {
			Helper.logException(e, logger);
		}
		try {
			FacesContext.getCurrentInstance()
			   .getExternalContext().redirect("tradeList.xhtml");
		} catch (IOException e) {
			Helper.logException(e, logger);
		}
	}

	private String printFileInfo(UploadedFile file) {
		if (file == null)
			return null;
		String des = "";
		des += file.getFileName() + "\nsize:" + file.getSize() + "\nfile type:"
				+ file.getContentType();
		return des;
	}

	public void uploadAttachmentFile(FileUploadEvent event) throws IOException {
		logger.debug("Uploaded Files. " + printFileInfo(event.getFile()));
		Attachment attachment = Helper.extractAttachment(event.getFile());
		comment.addAttachment(attachment);
		if (attachment.getContentType().matches("image/.*")) {
			this.tempAttachments.add(attachmentBufferView.addAttachment(viewId,
					attachment));
		}
	}
	
	public void update() throws ServiceException{
		trade.setLabels(this.labelView.getSelectedLabels());
		tradeService.update(trade);
	}

	public void close() throws ServiceException {
		trade.setState(1);
		tradeService.update(trade);
	}

	@Override
	public int getType() {
		return Event.SUBMIT;
	}

	@Override
	public void handle(Event event) throws Exception {
		System.out.println("HandleEvent");
		this.update();
	}

	@Override
	public String getId() {
		return this.viewId;
	}
	@ManagedProperty("#{newsSelectedView}")
	private NewsSelectedView newsSelectedView;
	
	public NewsSelectedView getNewsSelectedView() {
		return newsSelectedView;
	}

	public void setNewsSelectedView(NewsSelectedView newsSelectedView) {
		this.newsSelectedView = newsSelectedView;
	}
	
	private class EventListenerImpl implements EventListener{

		@Override
		public int getType() {
			return Event.UPDATE_NEWS;
		}

		@Override
		public void handle(Event event) throws Exception {
			List<News> selectedNews=(List<News>)event.getData();
			ViewTradeView.this.insertNews(selectedNews);
		}

		@Override
		public String getId() {
			return ViewTradeView.this.viewId;
		}
		
	};
	public void preInsertNews(String updateIds) throws ServiceException{
		newsSelectedView.setUpdateIds(updateIds);
		newsSelectedView.prepareAvailableNews(this.trade.getNewsItems(),updateIds);
	}
	public void insertNews(List<News> selectedNews) throws ServiceException{
		trade.setNewsItems(selectedNews);
		tradeService.update(trade);
	}
	public void deleteNews(News news) throws ServiceException{
		List<News> newsItems=new ArrayList<News>();
		for(News newsIndex:trade.getNewsItems()){
			if(newsIndex.getId()!=news.getId()){
				newsItems.add(newsIndex);
			}
		}
		trade.setNewsItems(newsItems);
		tradeService.update(trade);
	}
	public void viewNews(News news){
		this.newsSelectedView.setNews(news);
	}
}
