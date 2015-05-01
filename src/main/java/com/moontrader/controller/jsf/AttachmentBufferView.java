package com.moontrader.controller.jsf;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;

import com.moontrader.dto.Attachment;
import com.moontrader.util.Assert;

@ManagedBean
@SessionScoped
public class AttachmentBufferView implements Serializable{
	private static final long serialVersionUID = -2639368213858040401L;
	public static class ViewBuffer{
		private int counter=0;
		private Map<Integer,Attachment> attachments=new TreeMap<Integer,Attachment>();
		public Attachment findAttachment(int id){
			return attachments.get(id);
		}
		public int add(Attachment attachment){
			counter++;
			attachments.put(counter, attachment);
			return counter;
		}
		public void clear(){
			attachments.clear();
		}
	}
	private Map<String,ViewBuffer> buffer=new HashMap<String,ViewBuffer>();
	public 	void addViewBuffer(String viewId){
		buffer.put(viewId, new ViewBuffer());
	}
	public ViewBuffer getViewBuffer(String viewId){
		return buffer.get(viewId);
	}
	private Logger logger=Logger.getLogger(AttachmentBufferView.class);
	public int addAttachment(String viewId,Attachment attachment){
		logger.debug("viewId:"+viewId);
		if(buffer.get(viewId)==null){
			addViewBuffer(viewId);
		}
		return getViewBuffer(viewId).add(attachment);
	}
	public Attachment getAttachment(String viewId,int id){
		return Assert.assertNotNull(getViewBuffer(viewId), "viewId").findAttachment(id);
	}
	public void clear(String viewId){
		if(getViewBuffer(viewId)!=null){
			getViewBuffer(viewId).clear();
		}
	}
}
