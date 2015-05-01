package com.moontrader.controller.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.moontrader.dto.News;
import com.moontrader.service.NewsService;
import com.moontrader.service.ServiceException;


@ManagedBean
@ViewScoped
public class NewsSelectedView implements Serializable,EventSource{
	private static final long serialVersionUID = -3144665087114548510L;
	private Map<Integer,List<EventListener>> listenters=new HashMap<Integer,List<EventListener>>();
	@Override
	public void dispatch(Event event) throws Exception {
		if(listenters.containsKey(event.getType())){
			for(EventListener e:listenters.get(event.getType())){
				e.handle(event);
			}
		}
	}
	private String updateIds;
	public String getUpdateIds() {
		return updateIds;
	}
	public void setUpdateIds(String updateIds) {
		this.updateIds = updateIds;
	}
	@Override
	public void addEventListener(EventListener listener) {
		int type=listener.getType();
		if(listenters.get(type)==null){
			listenters.put(type, new ArrayList<EventListener>());
		}
		listenters.get(type).add(listener);
	}
	@ManagedProperty("#{newsService}")
	private NewsService newsService;
	private List<News> allNews;
	public List<News> getAllNews() {
		return allNews;
	}
	public NewsService getNewsService() {
		return newsService;
	}
	public void setNewsService(NewsService newsService) {
		this.newsService = newsService;
	}
	public void prepareAvailableNews(List<News> items,String updateIds) throws ServiceException{
		this.allNews=this.newsService.list();
		this.updateIds=updateIds;
		if(items==null){
			items=new ArrayList<News>();
		}
		this.selectedNews=new ArrayList<News>();
		this.availableNews=new ArrayList<News>();
		Map<Integer,News> indexes=new HashMap<Integer,News>();
		for(News news:items){
			indexes.put(news.getId(), news);
			this.selectedNews.add(news);
		}
		for(News news:this.allNews){
			if(!indexes.containsKey(news.getId())){
				availableNews.add(news);
			}
		}
	}
	private List<News> availableNews=new ArrayList<News>();
	private News[] tempNews1;
	public News[] getTempNews1() {
		return tempNews1;
	}
	public void setTempNews1(News[] tempNews1) {
		this.tempNews1 = tempNews1;
	}
	private News[] tempNews2;
	public News[] getTempNews2() {
		return tempNews2;
	}
	public void setTempNews2(News[] tempNews2) {
		this.tempNews2 = tempNews2;
	}
	public List<News> getAvailableNews() {
		return availableNews;
	}
	public void setAvailableNews(List<News> availableNews) {
		this.availableNews = availableNews;
	}
	public void setSelectedNews(List<News> selectedNews) {
		this.selectedNews = selectedNews;
	}
	private List<News> selectedNews=new ArrayList<News>();
	
	public List<News> getSelectedNews(){
		return this.selectedNews;
	}
	private News news;
	public News getNews() {
		return news;
	}
	public void setNews(News news) {
		this.news = news;
	}
	public void selectNews(News news){
		this.news=news;
	}
	public static void remove(List<News> src,News[] items){
		Map<Integer,News> indexes=new HashMap<Integer,News>();
		for(News item:items){
			indexes.put(item.getId(), item);
		}
		List<News> temp=new ArrayList<News>();
		for(News item:src){
			if(!indexes.containsKey(item.getId())){
				temp.add(item);
			}
		}
		src.clear();
		src.addAll(temp);
	}
	public static void addAll(List<News> src,News[] items){
		for(News item:items){
			src.add(item);
		}
	}
	public void addNews(){
		if(this.tempNews1!=null&&this.tempNews1.length>0){
			addAll(this.selectedNews,this.tempNews1);
			remove(this.availableNews,this.tempNews1);
		}
	}
	public void removeNews(){
		if(this.tempNews1!=null&&this.tempNews1.length>0){
			addAll(this.availableNews,this.tempNews2);
			remove(this.selectedNews,this.tempNews2);
		}
	}
	private class NewsEvent implements Event{
		private EventSource eventSource;
		private Object data;
		public NewsEvent(EventSource eventSource,Object data){
			this.eventSource=eventSource;
			this.data=data;
		}
		@Override
		public int getType() {
			return Event.UPDATE_NEWS;
		}

		@Override
		public EventSource getEventSource() {
			return eventSource;
		}

		@Override
		public Object getData() {
			return data;
		}
		@Override
		public String getUpdateIds() {
			return updateIds;
		}
		
	}
	public void onSubmit() throws Exception{
		Event event=new NewsEvent(this,this.getSelectedNews());
		this.dispatch(event);
	}
	@PostConstruct
	public void init(){
		try {
			this.availableNews=newsService.list();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
}
