package com.moontrader.csvreader;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class CsvToObject {
	public CsvToObject(){
		
	}
	public <T> List<T> readEntities(Reader reader,Class<T> entity){
	    List<T> result=new ArrayList<T>();
	    CsvReader csvReader=new CsvReader(reader);
	    return result;
	}
}
