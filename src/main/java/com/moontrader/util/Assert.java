package com.moontrader.util;

public class Assert {
	public static <T> T assertNotNull(T obj,String param){
		if(obj==null){
			throw new IllegalStateException(param+" should not be null.");
		}
		return obj;
	}
}
