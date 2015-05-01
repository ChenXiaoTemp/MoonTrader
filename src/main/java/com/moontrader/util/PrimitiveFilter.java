package com.moontrader.util;

public class PrimitiveFilter implements Filter {

	@Override
	public boolean filter(Object obj) {
		Class<?> clzz=obj.getClass();
		if(clzz.getName().startsWith("java.lang.")){
			return true;
		}
		return false;
	}

}
