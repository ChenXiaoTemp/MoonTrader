package com.moontrader.controller.jsf;

public interface Event {
	int getType();
	EventSource getEventSource();
	Object getData();
	String getUpdateIds();
	final static int NEW_LABEL=1;
	final static int UPDATE_NEWS=2;
	final static int SUBMIT=0;
}
