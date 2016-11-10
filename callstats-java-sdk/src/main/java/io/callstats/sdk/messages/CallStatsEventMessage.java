package io.callstats.sdk.messages;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CallStatsEventMessage {
	private String version;
	private int appID;
	private String endpointType;
	private String conferenceID;
	private long apiTS;
	private String token;
	private String localID;
	private String remoteID;
	private String ucID;
	private EventInfo event;
	private static final Logger logger = LogManager.getLogger("CallStats");

	public String getUcID() {
		return ucID;
	}
	public void setUcID(String ucID) {
		this.ucID = ucID;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public int getAppID() {
		return appID;
	}
	public void setAppID(int appID) {
		this.appID = appID;
	}
	public String getEndpointType() {
		return endpointType;
	}
	public void setEndpointType(String endpointType) {
		this.endpointType = endpointType;
	}
	public String getConferenceID() {
		return conferenceID;
	}
	public void setConferenceID(String conferenceID) {
		this.conferenceID = conferenceID;
	}
	public long getApiTS() {
		return apiTS;
	}
	public void setApiTS(long apiTS) {
		this.apiTS = apiTS;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getLocalID() {
		return localID;
	}
	public void setLocalID(String localID) {
		this.localID = localID;
	}
	public String getRemoteID() {
		return remoteID;
	}
	public void setRemoteID(String remoteID) {
		this.remoteID = remoteID;
	}
	public EventInfo getEvent() {
		return event;
	}
	public void setEvent(EventInfo event) {
		this.event = event;
	}

	public CallStatsEventMessage(String version, int appID, String endpointType, String conferenceID, long apiTS, String token, String localID,
			String remoteID, EventInfo event) {
		super();
		this.version = version;
		this.appID = appID;
		this.endpointType = endpointType;	
		this.apiTS = apiTS;
		this.token = token;
		this.event = event;
		try {
			this.conferenceID = URLEncoder.encode(conferenceID, "UTF-8");
			this.localID = URLEncoder.encode(localID, "UTF-8");
			this.remoteID = URLEncoder.encode(remoteID, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("UnsupportedEncodingException " + e.getMessage(), e);
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public CallStatsEventMessage(String version, int appID, String endpointType, String conferenceID, long apiTS, String token, String localID,
			String remoteID, String ucID, EventInfo event) {
		super();
		this.version = version;
		this.appID = appID;
		this.endpointType = endpointType;	
		this.apiTS = apiTS;
		this.token = token;
		this.event = event;
		this.ucID = ucID;
		
		try {
			this.conferenceID = URLEncoder.encode(conferenceID, "UTF-8");
			this.localID = URLEncoder.encode(localID, "UTF-8");
			this.remoteID = URLEncoder.encode(remoteID, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("UnsupportedEncodingException " + e.getMessage(), e);
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
