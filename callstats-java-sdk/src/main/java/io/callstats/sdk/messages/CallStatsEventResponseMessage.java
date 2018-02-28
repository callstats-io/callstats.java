package io.callstats.sdk.messages;

public class CallStatsEventResponseMessage {
	
	private String status;
	private String conferenceID;
	private String ucID;
	private int conferenceDuration;
	private CallStatsEventResponseData eventData;
	
	
	public String getStatus() {
		if(status != null) {
			return status;
		}
		else {
			return eventData.getStatus();
		}
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	public String getConferenceID() {
		return conferenceID;
	}
	public void setConferenceID(String conferenceID) {
		this.conferenceID = conferenceID;
	}
	public String getUcID() {
		return ucID;
	}
	public void setUcID(String ucID) {
		this.ucID = ucID;
	}
	public int getConferenceDuration() {
		return conferenceDuration;
	}
	public void setConferenceDuration(int conferenceDuration) {
		this.conferenceDuration = conferenceDuration;
	}
	
	public String getReason() {
		return this.eventData.getReason();
	}
	
	public CallStatsEventResponseMessage(String status, String conferenceID, String ucID, int conferenceDuration) {
		super();
		this.status = status;
		this.conferenceID = conferenceID;
		this.ucID = ucID;
		this.conferenceDuration = conferenceDuration;
		this.eventData = new CallStatsEventResponseData();
	}	
}
