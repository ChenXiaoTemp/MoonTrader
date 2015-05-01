package com.moontrader.controller.jsf;

import com.moontrader.dto.Label;

public class LabelEvent implements Event {
	private int type;
	private Label label;
	private String updateIds=null;
	public void setUpdateIds(String updateIds) {
		this.updateIds = updateIds;
	}
	private EventSource eventSource;
	public LabelEvent(int type,Label label,EventSource eventSource,String updateIds){
		this.type=type;
		this.label=label;
		this.eventSource=eventSource;
		this.updateIds=updateIds;
	}
	@Override
	public int getType() {
		return type;
	}

	@Override
	public EventSource getEventSource() {
		return eventSource;
	}

	@Override
	public Object getData() {
		return label;
	}
	@Override
	public String getUpdateIds() {
		return this.updateIds;
	}

}
