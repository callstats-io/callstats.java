package io.callstats.sdk.messages;

public class CallStatsEventResponse {
	
	int appID;
	String userID;
	String version;
	String endpointType;
	CallStatsEventResponseData eventData;
	
	public CallStatsEventResponse() {
		eventData = new CallStatsEventResponseData();
	}

	private class CallStatsEventResponseData {
		String status;
		String reason;
		String description;
		
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getReason() {
			return reason;
		}
		public void setReason(String reason) {
			this.reason = reason;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
	}
	
	
	public int getAppID() {
		return appID;
	}

	public void setAppID(int appID) {
		this.appID = appID;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
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
	
	public String getStatus() {
		return eventData.getStatus();
	}
	public void setStatus(String status) {
		eventData.setStatus(status);
	}
	public String getReason() {
		return eventData.getReason();
	}
	public void setReason(String reason) {
		eventData.setReason(reason);
	}
	public String getDescription() {
		return eventData.getDescription();
	}
	public void setDescription(String description) {
		eventData.setDescription(description);
	}
}
