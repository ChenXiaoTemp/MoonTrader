package com.moontrader.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.codec.Utf8;

import com.moontrader.dto.Attachment;
import com.moontrader.dto.Operation;
import com.moontrader.entities.OperationEntity;

public final class Helper {
	private static Logger logger = Logger.getLogger(Helper.class);

	public static Operation entityToDto(OperationEntity entity) {
		Operation operation = new Operation();
		operation.setName(entity.getName());
		operation.setId(entity.getId());
		return operation;
	}

	public static <T extends Throwable> T logException(T e, Logger logger) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(outputStream);
		e.printStackTrace(printStream);
		logger.error(outputStream.toString());
		return e;
	}

	public static String digest(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(Utf8.encode(password)); // Change this to "UTF-16" if
			byte[] digest = md.digest();
			return new String(Hex.encode(digest));
		} catch (NoSuchAlgorithmException e) {
			logException(e, logger);
		} 
		return password;
	}
	public static void forEach(Object obj,FieldVisitor visitor){
		PropertyUtilsBean utilBean=new PropertyUtilsBean();
		PropertyDescriptor[] properties=utilBean.getPropertyDescriptors(obj);
		for(PropertyDescriptor descriptor:properties){
			visitor.vist(descriptor, obj);
		}
	}
	public static void checkNull(Object obj,String fieldName){
		if(obj==null){
			throw new IllegalArgumentException("The param "+fieldName+" should not be null.");
		}
	}
	public static void checkLeZero(int value,String fieldName){
		if(value<=0){
			throw new IllegalArgumentException("The param "+fieldName+" should not be zero.");
		}
	}
	public static void logObject(Object obj,Logger logger){
		try {
			Map<String,String> values=BeanUtils.describe(obj);
			String temp="Object :";
			for(Map.Entry<String, String> it:values.entrySet()){
				temp+=it.getKey()+"="+it.getValue()+"\n";
			}
			logger.debug(temp);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static Attachment extractAttachment(UploadedFile uploadedFile) throws IOException{
		Attachment attachment=new Attachment();
		attachment.setFilename(uploadedFile.getFileName());
		attachment.setContentType(uploadedFile.getContentType());
		attachment.setEncoding("utf-8");
		ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
		InputStream inputStream=null;
		if(uploadedFile.getInputstream()!=null){
			try{
				inputStream=uploadedFile.getInputstream();
				byte[] buffer=new byte[1024];
				int size=0;
				while(0<(size=inputStream.read(buffer))){
					outputStream.write(buffer,0,size);
				}
			}
			finally{
				if(inputStream!=null)
				inputStream.close();
				outputStream.close();
			}
			attachment.setContent(outputStream.toByteArray());
		}
		
		return attachment;
	}
	public static String mapToString(Map<?,?> values){
		StringBuilder builder=new StringBuilder();
		for(Object v:values.keySet()){
			builder.append(v).append("=").append(values.get(v)).append("\n");
		}
		return builder.toString();
	}
	public static String getRequestParam(String key){
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		Map<String,String[]> params=request.getParameterMap();
		String info="Params:\n";
		for(String k:params.keySet()){
			String temp=k+"=[";
			for(String t:params.get(k)){
				temp+=t+",";
			}
			temp+="]";
			info+=temp+"\n";
		}
		logger.debug(info);
		String param = request.getParameter(key);
		if(param==null||param.isEmpty()){
			throw new IllegalArgumentException("Unkown param:"+key);
		}
		return param;
	}
	public static StreamedContent createInvalidImage(){
		BufferedImage bufferedImg = new BufferedImage(500, 300, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = bufferedImg.createGraphics();
        g2.drawString("Can not find the image.", 0, 150);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
			ImageIO.write(bufferedImg, "png", os);
		} catch (IOException e1) {
			Helper.logException(e1, logger);
		}
        return new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()),"image/png");
	}
}
