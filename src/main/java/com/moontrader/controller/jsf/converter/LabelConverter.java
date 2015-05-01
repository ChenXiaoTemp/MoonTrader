package com.moontrader.controller.jsf.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.moontrader.dto.Label;

@FacesConverter(forClass=Label.class,value="labelConverter")
public class LabelConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		Label label=new Label();
		label.setName(value);
		return label;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		return ((Label)value).getName();
	}

}
