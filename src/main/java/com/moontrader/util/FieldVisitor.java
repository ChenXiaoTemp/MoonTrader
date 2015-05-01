package com.moontrader.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtilsBean;

public abstract class FieldVisitor {
	abstract public void vist(PropertyDescriptor descriptor,Object source);
	public Object getFieldValue(Field field,Object source){
		PropertyUtilsBean utilsBean=new PropertyUtilsBean();
		try {
			return utilsBean.getProperty(source, field.getName());
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException("Can not access the value of "+source);
		} catch (InvocationTargetException e) {
			throw new IllegalArgumentException("Can not access the value of "+source);
		} catch (NoSuchMethodException e) {
			throw new IllegalArgumentException("Can not access the value of "+source);
		}
	}
}
