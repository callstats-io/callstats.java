package io.callstats.sdk.messages;

import io.callstats.sdk.EndpointInfo;
import io.callstats.sdk.HealthStatusData;
import io.callstats.sdk.TrafficStatusData;


public class CallStatsBridgeEventMessage {
	
	static final String MSG_TYPE = "CallStatsEvent";
	
	int appID;
	String bridgeID;
	String version;
	String endpointType;
	String apiTS;
	String token;
	EventInfo event;
		
	public CallStatsBridgeEventMessage(int appID, String bridgeID, String version,
		String endpointType, String apiTS, String token,HealthStatusData healthStatusData,
			TrafficStatusData trafficStatusData,EndpointInfo endpointInfo) {
		super();
		this.appID = appID;
		this.bridgeID = bridgeID;
		this.version = version;
		this.endpointType = endpointType;
		this.apiTS = apiTS;
		this.token = token;
		this.event = new EventInfo();
		EventData data = new EventData();
		data.setHealthStatusData(healthStatusData);
		data.setTrafficStatusData(trafficStatusData);		
		event.setEventData(data);
		event.setEventType(MSG_TYPE);
		event.setEndpointInfo(endpointInfo);		
	}

	public int getAppID() {
		return appID;
	}

	public void setAppID(int appID) {
		this.appID = appID;
	}

	public String getBridgeID() {
		return bridgeID;
	}

	public void setBridgeID(String userID) {
		this.bridgeID = userID;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getEndpointType() {
		return endpointType;
	}

	public void setEndpointType(String endpointType) {
		this.endpointType = endpointType;
	}

	public String getApiTS() {
		return apiTS;
	}

	public void setApiTS(String apiTS) {
		this.apiTS = apiTS;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public EventInfo getEvent() {
		return event;
	}

	public void setEvent(EventInfo event) {
		this.event = event;
	}
		
	private class EventData {
		HealthStatusData healthStatusData;
		TrafficStatusData trafficStatusData;
		
		public EventData() {
			healthStatusData = new HealthStatusData();
			trafficStatusData = new TrafficStatusData();
		}
		
		public HealthStatusData getHealthStatusData() {
			return healthStatusData;
		}
		public void setHealthStatusData(HealthStatusData healthStatusData) {
			this.healthStatusData = healthStatusData;
		}
		public TrafficStatusData getTrafficStatusData() {
			return trafficStatusData;
		}
		public void setTrafficStatusData(TrafficStatusData trafficStatusData) {
			this.trafficStatusData = trafficStatusData;
		}
		
	}
	
	private class EventInfo {
		String eventType;
		EventData eventData;
		EndpointInfo endpointInfo;
		
		public EventInfo() {
			super();
			eventData = new EventData();
			endpointInfo = new EndpointInfo();
		}
		
		public String getEventType() {
			return eventType;
		}
		public void setEventType(String eventType) {
			this.eventType = eventType;
		}
		public EventData getEventData() {
			return eventData;
		}
		public void setEventData(EventData eventData) {
			this.eventData = eventData;
		}
		public EndpointInfo getEndpointInfo() {
			return endpointInfo;
		}
		public void setEndpointInfo(EndpointInfo endpointInfo) {
			this.endpointInfo = endpointInfo;
		}
	}
}
 