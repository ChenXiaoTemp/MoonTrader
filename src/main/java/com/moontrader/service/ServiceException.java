package com.moontrader.service;

public class ServiceException extends Exception {
	private static final long serialVersionUID = -9066096244997243939L;
	public ServiceException(){
		
	}
	public ServiceException(String message){
		super(message);
	}
	public ServiceException(String message, Throwable cause){
		super(message,cause);
	}
	public ServiceException(Throwable cause){
		super(cause);
	}
}
