package com.moontrader.util;

import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtilsBean;

public class ObjectReader {
	private Filter primitiveFilter=new PrimitiveFilter();
	private Filter mapFilter=new MapFilter();
	private Filter listFilter=new ListFilter();
	public Map<String,String> getValues(Object obj){
		Map<String,String> result=new HashMap<String,String>();
		PropertyUtilsBean utilsBean=new PropertyUtilsBean();
		PropertyDescriptor []descriptors=utilsBean.getPropertyDescriptors(obj);
		for(PropertyDescriptor descriptor:descriptors){
			PropertyEditor editor=descriptor.createPropertyEditor(obj);
			Object value=editor.getValue();
			if(primitiveFilter.filter(value)){
				
			}
			else{
				
			}
		}
		return result;
	}
}
