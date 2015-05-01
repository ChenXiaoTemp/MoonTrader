package com.moontrader.dto;

public class Label {
	public String getName() {
		return name;
	}
	public void setName(String name) {
		if(name!=null){
			this.name=name.trim();
		}
		else{
			this.name="";
		}
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	private String name;
	private int id;
}
