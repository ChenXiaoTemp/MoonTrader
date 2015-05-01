package com.moontrader.controller.jsf;

public interface EventSource {
	void dispatch(Event event) throws Exception;
	void addEventListener(EventListener listener);
}
