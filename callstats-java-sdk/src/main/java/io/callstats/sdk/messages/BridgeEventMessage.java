package io.callstats.sdk.messages;

import io.callstats.sdk.BridgeStatusInfo;
import io.callstats.sdk.EndpointInfo;
import io.callstats.sdk.HealthStatusData;
import io.callstats.sdk.TrafficStatusData;

/**
 * The Class BridgeEventMessage.
 */
public class BridgeEventMessage {
	
	/** The Constant MSG_TYPE. */
	private static final String MSG_TYPE = "CallStatsBridgeEvent";
	
	/** The app id. */
	private int appID;
	
	/** The bridge id. */
	private String bridgeID;
	
	/** The version. */
	private String version;
	
	/** The endpoint type. */
	private String endpointType;
	
	/** The api ts. */
	private String apiTS;
	
	/** The token. */
	private String token;
	
	/** The event. */
	private EventInfo event;
		
	/**
	 * Instantiates a new bridge event message.
	 *
	 * @param appID the app id
	 * @param bridgeID the bridge id
	 * @param version the version
	 * @param endpointType the endpoint type
	 * @param apiTS the api ts
	 * @param token the token
	 * @param bridgeStatusInfo the bridge status info
	 * @param endpointInfo the endpoint info
	 */
	public BridgeEventMessage(int appID, String bridgeID, String version,
		String endpointType, String apiTS, String token,BridgeStatusInfo bridgeStatusInfo,EndpointInfo endpointInfo) {
		super();
		this.appID = appID;
		this.bridgeID = bridgeID;
		this.version = version;
		this.endpointType = endpointType;
		this.apiTS = apiTS;
		this.token = token;
		this.event = new EventInfo();
		
		setData(bridgeStatusInfo);		
		
		event.setEventType(MSG_TYPE);
		event.setEndpointInfo(endpointInfo);		
	}

	/**
	 * Sets the data.
	 *
	 * @param bridgeStatusInfo the new data
	 */
	private void setData(BridgeStatusInfo bridgeStatusInfo) {
		EventData data = new EventData();
		HealthStatusData healthStatusData = new HealthStatusData();
		TrafficStatusData trafficStatusData = new TrafficStatusData();
		
		healthStatusData.setCpuUsage(bridgeStatusInfo.getCpuUsage());
		healthStatusData.setMemoryUsage(bridgeStatusInfo.getMemoryUsage());
		
		trafficStatusData.setAvgIntervalJitter(bridgeStatusInfo.getAvgIntervalJitter());
		trafficStatusData.setAvgIntervalRtt(bridgeStatusInfo.getAvgIntervalRtt());
		trafficStatusData.setIntervalLoss(bridgeStatusInfo.getIntervalLoss());
		trafficStatusData.setReceivedBytes(bridgeStatusInfo.getReceivedBytes());
		trafficStatusData.setSentBytes(bridgeStatusInfo.getSentBytes());
		trafficStatusData.setTotalLoss(bridgeStatusInfo.getTotalLoss());
		
		
		data.setHealthStatusData(healthStatusData);
		data.setTrafficStatusData(trafficStatusData);
		event.setEventData(data);
	}
	
	/**
	 * Gets the app id.
	 *
	 * @return the app id
	 */
	public int getAppID() {
		return appID;
	}

	/**
	 * Sets the app id.
	 *
	 * @param appID the new app id
	 */
	public void setAppID(int appID) {
		this.appID = appID;
	}

	/**
	 * Gets the bridge id.
	 *
	 * @return the bridge id
	 */
	public String getBridgeID() {
		return bridgeID;
	}

	/**
	 * Sets the bridge id.
	 *
	 * @param userID the new bridge id
	 */
	public void setBridgeID(String userID) {
		this.bridgeID = userID;
	}

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Sets the version.
	 *
	 * @param version the new version
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Gets the endpoint type.
	 *
	 * @return the endpoint type
	 */
	public String getEndpointType() {
		return endpointType;
	}

	/**
	 * Sets the endpoint type.
	 *
	 * @param endpointType the new endpoint type
	 */
	public void setEndpointType(String endpointType) {
		this.endpointType = endpointType;
	}

	/**
	 * Gets the api ts.
	 *
	 * @return the api ts
	 */
	public String getApiTS() {
		return apiTS;
	}

	/**
	 * Sets the api ts.
	 *
	 * @param apiTS the new api ts
	 */
	public void setApiTS(String apiTS) {
		this.apiTS = apiTS;
	}

	/**
	 * Gets the token.
	 *
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * Sets the token.
	 *
	 * @param token the new token
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * Gets the event.
	 *
	 * @return the event
	 */
	public EventInfo getEvent() {
		return event;
	}

	/**
	 * Sets the event.
	 *
	 * @param event the new event
	 */
	public void setEvent(EventInfo event) {
		this.event = event;
	}
		
	/**
	 * The Class EventData.
	 */
	private class EventData {
		
		/** The health status data. */
		HealthStatusData healthStatusData;
		
		/** The traffic status data. */
		TrafficStatusData trafficStatusData;
		
		/**
		 * Instantiates a new event data.
		 */
		public EventData() {
			healthStatusData = new HealthStatusData();
			trafficStatusData = new TrafficStatusData();
		}
		
		/**
		 * Gets the health status data.
		 *
		 * @return the health status data
		 */
		public HealthStatusData getHealthStatusData() {
			return healthStatusData;
		}
		
		/**
		 * Sets the health status data.
		 *
		 * @param healthStatusData the new health status data
		 */
		public void setHealthStatusData(HealthStatusData healthStatusData) {
			this.healthStatusData = healthStatusData;
		}
		
		/**
		 * Gets the traffic status data.
		 *
		 * @return the traffic status data
		 */
		public TrafficStatusData getTrafficStatusData() {
			return trafficStatusData;
		}
		
		/**
		 * Sets the traffic status data.
		 *
		 * @param trafficStatusData the new traffic status data
		 */
		public void setTrafficStatusData(TrafficStatusData trafficStatusData) {
			this.trafficStatusData = trafficStatusData;
		}
		
	}
	
	/**
	 * The Class EventInfo.
	 */
	private class EventInfo {
		
		/** The event type. */
		String eventType;
		
		/** The event data. */
		EventData eventData;
		
		/** The endpoint info. */
		EndpointInfo endpointInfo;
		
		/**
		 * Instantiates a new event info.
		 */
		public EventInfo() {
			super();
			eventData = new EventData();
			endpointInfo = new EndpointInfo();
		}
		
		/**
		 * Gets the event type.
		 *
		 * @return the event type
		 */
		public String getEventType() {
			return eventType;
		}
		
		/**
		 * Sets the event type.
		 *
		 * @param eventType the new event type
		 */
		public void setEventType(String eventType) {
			this.eventType = eventType;
		}
		
		/**
		 * Gets the event data.
		 *
		 * @return the event data
		 */
		public EventData getEventData() {
			return eventData;
		}
		
		/**
		 * Sets the event data.
		 *
		 * @param eventData the new event data
		 */
		public void setEventData(EventData eventData) {
			this.eventData = eventData;
		}
		
		/**
		 * Gets the endpoint info.
		 *
		 * @return the endpoint info
		 */
		public EndpointInfo getEndpointInfo() {
			return endpointInfo;
		}
		
		/**
		 * Sets the endpoint info.
		 *
		 * @param endpointInfo the new endpoint info
		 */
		public void setEndpointInfo(EndpointInfo endpointInfo) {
			this.endpointInfo = endpointInfo;
		}
	}
}
 