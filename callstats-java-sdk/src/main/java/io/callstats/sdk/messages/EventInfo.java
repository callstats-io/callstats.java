package io.callstats.sdk.messages;

public class EventInfo {
	
	private String eventType;
	private String eventData;
		
	public EventInfo(String eventType, String eventData) {
		super();
		this.eventType = eventType;
		this.eventData = eventData;
	}
	
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getEventData() {
		return eventData;
	}
	public void setEventData(String eventData) {
		this.eventData = eventData;
	}
}
