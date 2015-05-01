package com.moontrader.dto;

import java.io.Serializable;

public class Operation implements Serializable{
	private static final long serialVersionUID = -3043270304851342174L;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private int id;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String name;
}
