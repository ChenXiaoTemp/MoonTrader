package com.moontrader.dto;

import java.io.Serializable;

public class Currency implements Serializable{
	private static final long serialVersionUID = -4059350687163501L;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private int id;
	private String name;
}
