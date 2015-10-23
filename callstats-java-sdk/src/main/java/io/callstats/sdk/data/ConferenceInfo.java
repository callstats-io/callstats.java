package io.callstats.sdk.data;

public class ConferenceInfo {

	private String confID;
	private String initiatorID;
	
	public String getConfID() {
		return confID;
	}
	public void setConfID(String confID) {
		this.confID = confID;
	}
	public String getInitiatorID() {
		return initiatorID;
	}
	public void setInitiatorID(String initiatorID) {
		this.initiatorID = initiatorID;
	}
	public ConferenceInfo(String confID, String initiatorID) {
		super();
		this.confID = confID;
		this.initiatorID = initiatorID;
	}	
}
