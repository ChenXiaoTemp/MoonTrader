package com.moontrader.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.log4j.Logger;

public class SystemConfiguration {
	private static Logger logger = Logger.getLogger(SystemConfiguration.class);
	private static Map<String,String> configs= Collections.synchronizedMap(new HashMap<String,String>());
	public static String getParameter(String key){
		if(configs.containsKey(key)){
			return configs.get(key);
		}
		return null;
	}
	static{
		Properties properties=new Properties();
		try {
			InputStream inputStream=Thread.currentThread().getContextClassLoader().getResourceAsStream("moonconfig.properties");
			if(inputStream!=null){
				properties.load(inputStream);
				for(Entry<Object, Object> entry:properties.entrySet()){
					configs.put(entry.getKey().toString(), entry.getValue().toString());
				}
				String config=Helper.mapToString(configs);
				logger.debug("System Configuration:"+config);
			}
			else{
				logger.debug("Can not find the moonconfig.properties.");
			}
			
		} catch (IOException e) {
			Helper.logException(e, logger);
		}
	}
}
