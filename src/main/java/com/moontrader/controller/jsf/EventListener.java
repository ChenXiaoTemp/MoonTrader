package com.moontrader.controller.jsf;

public interface EventListener {
	int getType();
	void handle(Event event) throws Exception;
	String getId();
}
