package com.moontrader.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import com.moontrader.dto.Attachment;
import com.moontrader.dto.Comment;
import com.moontrader.dto.Currency;
import com.moontrader.dto.Diary;
import com.moontrader.dto.Label;
import com.moontrader.dto.News;
import com.moontrader.dto.Operation;
import com.moontrader.dto.Trade;
import com.moontrader.dto.TradeItem;
import com.moontrader.dto.User;
import com.moontrader.dto.UserRole;
import com.moontrader.entities.AttachmentEntity;
import com.moontrader.entities.CommentEntity;
import com.moontrader.entities.CurrencyEntity;
import com.moontrader.entities.DiaryEntity;
import com.moontrader.entities.LabelEntity;
import com.moontrader.entities.NewsEntity;
import com.moontrader.entities.OperationEntity;
import com.moontrader.entities.TradeEntity;
import com.moontrader.entities.TradeItemEntity;
import com.moontrader.entities.UserEntity;
import com.moontrader.entities.UserRoleEntity;

public class EntityUtils {
	
	public static String getConfigParam(String key){
		String param=SystemConfiguration.getParameter(key);
		if(param==null) return "";
		return param;
	}
	private static Logger logger=Logger.getLogger(EntityUtils.class);
	public static <T,E> T copyProperties(E e, Class<T> clzz){
		try {
			T t = clzz.newInstance();
			BeanUtils.copyProperties(t, e);
			return t;
		} catch (InstantiationException e1) {
			throw Helper.logException(new IllegalArgumentException(clzz.getName()+" is not valid dto class",e1), logger);
		} catch (IllegalAccessException e1) {
			throw Helper.logException(new IllegalArgumentException(clzz.getName()+" is not valid dto class",e1), logger);
		} catch (InvocationTargetException e1) {
			throw Helper.logException(new IllegalArgumentException(clzz.getName()+" is not valid dto class",e1), logger);
		}
	}
	public static Currency entityToDto(CurrencyEntity entity){
		return copyProperties(entity,Currency.class);
	}
	public static CurrencyEntity dtoToEntity(Currency currency){
		return copyProperties(currency,CurrencyEntity.class);
	}
	public static UserRoleEntity dtoToEntity(UserRole role,UserEntity userEntity){
		UserRoleEntity userRoleEntity=new UserRoleEntity();
		userRoleEntity.setUser(userEntity);
		userRoleEntity.setName(role.getName());
		return userRoleEntity;
	}
	public static List<UserRoleEntity> dtoToEntity(List<UserRole> roles,UserEntity userEntity){
		List<UserRoleEntity> entities=new ArrayList<UserRoleEntity>();
		for(UserRole role:roles){
			entities.add(dtoToEntity(role,userEntity));
		}
		return entities;
	}
	public static UserRole entityToDto(UserRoleEntity entity){
		UserRole role=new UserRole();
		role.setName(entity.getName());
		return role;
	}
	public static TradeItemEntity dtoToEntity(TradeItem item,TradeEntity trade){
		TradeItemEntity entity=new TradeItemEntity();
		entity.setAnalyse(item.getAnalyse());
		entity.setId(item.getId());
		entity.setOperation(dtoToEntity(item.getOperation()));
		entity.setOperationTips(item.getOperationTips());
		entity.setPrice(item.getPrice());
		entity.setState(item.getState());
		entity.setBeginTime(item.getBeginTime());
		entity.setEndTime(item.getEndTime());
		entity.setTitle(item.getTitle());
		return entity;
	}
	public static TradeItem entityToDto(TradeItemEntity entity){
		TradeItem item=new TradeItem();
		item.setAnalyse(entity.getAnalyse());
		item.setId(entity.getId());
		item.setOperation(entityToDto(entity.getOperation()));
		item.setOperationTips(entity.getOperationTips());
		item.setPrice(entity.getPrice());
		item.setState(entity.getState());
		item.setBeginTime(entity.getBeginTime());
		item.setEndTime(entity.getEndTime());
		item.setTitle(entity.getTitle());
		return item;
	}
	public static List<TradeItemEntity> dtoToEntity(List<TradeItem> items,TradeEntity trade){
		List<TradeItemEntity> result=new ArrayList<TradeItemEntity>();
		for(TradeItem item:items){
			result.add(dtoToEntity(item,trade));
		}
		return result;
	}
	public static List<TradeItem> entityToDto(List<TradeItemEntity> entities){
		List<TradeItem> items=new ArrayList<TradeItem>();
		for(TradeItemEntity entity:entities){
			items.add(entityToDto(entity));
		}
		return items;
	}
	public static byte[] loadFile(String filepath) throws IOException{
		ByteArrayOutputStream outputStream=null;
		FileInputStream inputStream=null;
		try{
			inputStream=new FileInputStream(filepath);
			outputStream=new ByteArrayOutputStream();
			byte [] buffer=new byte[1024];
			int size=0;
			while(0<(size=inputStream.read(buffer))){
				outputStream.write(buffer, 0, size);
			}
			return outputStream.toByteArray();
		}
		finally{
			if(inputStream!=null) inputStream.close();
			if(outputStream!=null) outputStream.close();
		}
		
	}
	public static Attachment entityToDto(AttachmentEntity entity) throws IOException{
		Attachment attachment=new Attachment();
		attachment.setEncoding(entity.getEncoding());
		attachment.setFilename(entity.getFilename());
		attachment.setContentType(entity.getContentType());
		attachment.setId(entity.getId());
		String filepath=EntityUtils.getConfigParam("file.folder");
		Assert.assertNotNull(filepath, "file.folder");
		filepath=filepath.trim();
		if(!filepath.isEmpty()){
			filepath+="/"+entity.getFilepath();
		}
		try{
			attachment.setContent(loadFile(filepath));
			attachment.setValidFile(true);
		}
		catch(IOException e){
			Helper.logException(e, logger);
			attachment.setValidFile(false);
		}
		
		return attachment;
	}
	public static Comment entityToDto(CommentEntity entity) throws IOException{
		Comment comment=new Comment();
		comment.setContent(entity.getContent());
		comment.setId(entity.getId());
		if(entity.getTime()!=null){
			comment.setTime(entity.getTime());
		}
		List<Attachment> attachments=new ArrayList<Attachment>();
		if(entity.getAttachments()!=null){
			for(AttachmentEntity et:entity.getAttachments()){
			    attachments.add(entityToDto(et));	
			}
		}
		comment.setAttachments(attachments);
		return comment;
	}
	public static List<Comment> commentEntitiesToDtos(List<CommentEntity> entities) throws IOException{
		ArrayList<Comment> result=new ArrayList<Comment>();
		for(CommentEntity entity:entities){
			result.add(entityToDto(entity));
		}
		Collections.sort(result, new Comparator<Comment>(){

			@Override
			public int compare(Comment o1, Comment o2) {
				return o2.getId()-o1.getId();
			}
			
		});
		return result;
	}
	public static Trade entityToDto(TradeEntity entity) throws IOException{
		Trade result=new Trade();
		result.setBeginTime(entity.getBeginTime());
		result.setEndTime(entity.getEndTime());
		result.setCreateTime(entity.getCreateTime());
		result.setCurrency(EntityUtils.entityToDto(entity.getCurrency()));
		result.setTitle(entity.getTitle());
		result.setItems(EntityUtils.entityToDto(entity.getItems()));
		result.setComments(commentEntitiesToDtos(entity.getComments()));
		result.setAttachments(EntityUtils.attachmentsToDtos(entity.getAttachments()));
		result.setState(entity.getState());
		result.setAnalyse(entity.getAnalyse());
		result.setId(entity.getId());
		result.setNewsItems(newsEntitiesToDtoes(entity.getNewsItems()));
		result.setLabels(labelEntitiesToDtoes(entity.getLabels()));
		return result;
	}
	public static TradeEntity dtoToEntity(Trade trade) throws IOException{
		TradeEntity entity=new TradeEntity();
		entity.setBeginTime(trade.getBeginTime());
		entity.setEndTime(trade.getEndTime());
		entity.setCreateTime(trade.getCreateTime());
		entity.setCurrency(EntityUtils.dtoToEntity(trade.getCurrency()));
		entity.setTitle(trade.getTitle());
		entity.setItems(EntityUtils.dtoToEntity(trade.getItems(), entity));
		entity.setState(trade.getState());
		entity.setAttachments(EntityUtils.dtoToEntity(trade.getAttachments()));
		entity.setId(trade.getId());
		entity.setNewsItems(newsDtosToEntities(trade.getNewsItems()));
		entity.setLabels(labelDtoesToEntities(trade.getLabels()));
		return entity;
	}
	public static List<Label> labelEntitiesToDtoes(List<LabelEntity> entites){
		List<Label> res=new ArrayList<Label>();
		for(LabelEntity entity:entites){
			res.add(entityToDto(entity));
		}
		return res;
	}
	public static List<LabelEntity> labelDtoesToEntities(List<Label> labels){
		List<LabelEntity> res=new ArrayList<LabelEntity>();
		for(Label entity:labels){
			res.add(dtoToEntity(entity));
		}
		return res;
	}
	public static User entityToDto(UserEntity entity){
		User user=new User();
		user.setEmail(entity.getEmail());
		user.setId(entity.getId());
		user.setNickname(entity.getNickname());
		user.setPassword(entity.getPassword());
		for(UserRoleEntity roleEntity:entity.getRoles()){
        	UserRole role=new UserRole();
         	role.setName(roleEntity.getName());
         	user.getRoles().add(role);
        }
		return user;
	}
	public static UserEntity entityToDto(User user){
		UserEntity entity=new UserEntity();
		entity.setEmail(user.getEmail());
        entity.setId(user.getId());
        entity.setNickname(user.getNickname());
        entity.setPassword(user.getPassword());
        entity.setRoles(new ArrayList<UserRoleEntity>());
        for(UserRole role:user.getRoles()){
        	UserRoleEntity roleEntity=new UserRoleEntity();
         	roleEntity.setName(role.getName());
            entity.getRoles().add(roleEntity);
        }
        return entity;
	}
	
	public static Operation entityToDto(OperationEntity entity){
		return copyProperties(entity,Operation.class);
	}
	public static OperationEntity dtoToEntity(Operation currency){
		return copyProperties(currency,OperationEntity.class);
	}
	public static void storeFile(String filename,byte[] content) throws IOException{
		final String fileFoler="file.folder";
		String foler=getConfigParam(fileFoler);
		Assert.assertNotNull(foler, fileFoler);
		foler=foler.trim();
		String filepath=foler;
		if(filepath.isEmpty())filepath=filename;
		else filepath+="/"+filename;
		OutputStream outputStream=null;
		try{
			File file=new File(filepath);
			logger.debug("File path:"+file.getAbsolutePath());
			outputStream=new FileOutputStream(file);
			outputStream.write(content);
		}
		finally{
			if(outputStream!=null)outputStream.close();
		}
		
	}
	public static NewsEntity dtoToEntity(News news) throws IOException{
		NewsEntity entity=new NewsEntity();
		entity.setAttachments(dtoToEntity(news.getAttachments()));
		entity.setComments(dtosToEntities(news.getComments()));
		entity.setContent(news.getContent());
		entity.setId(news.getId());
		entity.setImportance(news.getImportance());
		entity.setTime(news.getTime());
		entity.setUrl(news.getUrl());
		entity.setTitle(news.getTitle());
		entity.setLabels(labelDtoesToEntities(news.getLabels()));
		entity.setBeginTime(news.getBeginTime());
		entity.setEndTime(news.getEndTime());
		return entity;
	}
	public static List<CommentEntity> dtosToEntities(List<Comment> comments) throws IOException{
		List<CommentEntity> entities=new ArrayList<CommentEntity>();
		for(Comment comment:comments){
			entities.add(dtoToEntity(comment));
		}
		return entities;
	}
	public static AttachmentEntity dtoToEntity(Attachment attachment) throws IOException{
		AttachmentEntity entity=new AttachmentEntity();
		entity.setFilename(attachment.getFilename());
		UUID uuid = UUID.randomUUID();  
		entity.setFilepath(uuid.toString());
		entity.setContentType(attachment.getContentType());
		entity.setEncoding(attachment.getEncoding());
		storeFile(uuid.toString(),attachment.getContent());
		return entity;
	}
	public static List<AttachmentEntity> dtoToEntity(List<Attachment> attachments) throws IOException{
		List<AttachmentEntity> entities=new ArrayList<AttachmentEntity>();
		for(Attachment attachment:attachments){
			entities.add(dtoToEntity(attachment));
		}
		return entities;
	}
	public static CommentEntity dtoToEntity(Comment comment) throws IOException{
		CommentEntity entity=new CommentEntity();
		entity.setAttachments(dtoToEntity(comment.getAttachments()));
		entity.setContent(comment.getContent());
		entity.setId(comment.getId());
		entity.setTime(comment.getTime());
		return entity;
	}
	public static List<Attachment> attachmentsToDtos(List<AttachmentEntity> entities) throws IOException{
		List<Attachment> attachments=new ArrayList<Attachment>();
		for(AttachmentEntity entity:entities){
			attachments.add(entityToDto(entity));
		}
		return attachments;
	}
	public static Diary entityToDto(DiaryEntity entity) throws IOException{
		Diary diary=new Diary();
		diary.setAttachments(attachmentsToDtos(entity.getAttachments()));
		diary.setTitle(entity.getTitle());
		diary.setContent(entity.getContent());
		diary.setId(entity.getId());
		diary.setTime(entity.getTime());
		diary.setNewsItems(newsEntitiesToDtoes(entity.getNewsItems()));
		return diary;
	}
	public static DiaryEntity dtoToEntity(Diary diary) throws IOException{
		DiaryEntity entity=new DiaryEntity();
		entity.setAttachments(dtoToEntity(diary.getAttachments()));
		entity.setTitle(diary.getTitle());
		entity.setContent(diary.getContent());
		entity.setId(diary.getId());
		entity.setTime(diary.getTime());
		entity.setNewsItems(newsDtosToEntities(diary.getNewsItems()));
		return entity;
	}
	public static List<NewsEntity> newsDtosToEntities(List<News> newsItems) throws IOException{
		List<NewsEntity> result=new ArrayList<NewsEntity>();
		for(News item:newsItems){
			result.add(dtoToEntity(item));
		}
		return result;
	}
	public static List<News> newsEntitiesToDtoes(List<NewsEntity> newsItems) throws IOException{
		List<News> result=new ArrayList<News>();
		for(NewsEntity item:newsItems){
			result.add(entityToDto(item));
		}
		return result;
	}
	public static News entityToDto(NewsEntity entity) throws IOException {
		News news=new News();
		List<Attachment> attachments=new ArrayList<Attachment>();
		for(AttachmentEntity attachment:entity.getAttachments()){
			attachments.add(entityToDto(attachment));
		}
		news.setAttachments(attachments);
		List<Comment> comments=new ArrayList<Comment>();
		for(CommentEntity comment:entity.getComments()){
			comments.add(entityToDto(comment));
		}
		news.setComments(comments);
		news.setContent(entity.getContent());
		news.setId(entity.getId());
		news.setImportance(entity.getImportance());
		news.setTime(entity.getTime());
		news.setUrl(entity.getUrl());
		news.setTitle(entity.getTitle());
		news.setLabels(labelEntitiesToDtoes(entity.getLabels()));
		news.setBeginTime(entity.getBeginTime());
		news.setEndTime(entity.getEndTime());
		return news;
	}
	public static Label entityToDto(LabelEntity entity){
		Label res=new Label();
		res.setId(entity.getId());
		res.setName(entity.getName());
		return res;
	}
	public static LabelEntity dtoToEntity(Label label){
		LabelEntity entity=new LabelEntity();
		entity.setId(label.getId());
		entity.setName(label.getName());
		return entity;
	}
}
