package io.callstats.sdk.messages;

/**
 * The Class BridgeEventResponse.
 */
public class BridgeStatusUpdateResponse {
	
	/** The app id. */
	private int appID;
	
	/** The user id. */
	private String userID;
	
	/** The version. */
	private String version;
	
	/** The endpoint type. */
	private String endpointType;
	
	/** The event data. */
	private CallStatsEventResponseData eventData;
	
	/**
	 * Instantiates a new bridge event response.
	 */
	public BridgeStatusUpdateResponse() {
		eventData = new CallStatsEventResponseData();
	}

	/**
	 * The Class CallStatsEventResponseData.
	 */
	private class CallStatsEventResponseData {
		
		/** The status. */
		String status;
		
		/** The reason. */
		String reason;
		
		/** The description. */
		String description;
		
		/**
		 * Gets the status.
		 *
		 * @return the status
		 */
		public String getStatus() {
			return status;
		}
		
		/**
		 * Sets the status.
		 *
		 * @param status the new status
		 */
		public void setStatus(String status) {
			this.status = status;
		}
		
		/**
		 * Gets the reason.
		 *
		 * @return the reason
		 */
		public String getReason() {
			return reason;
		}
		
		/**
		 * Sets the reason.
		 *
		 * @param reason the new reason
		 */
		public void setReason(String reason) {
			this.reason = reason;
		}
		
		/**
		 * Gets the description.
		 *
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}
		
		/**
		 * Sets the description.
		 *
		 * @param description the new description
		 */
		public void setDescription(String description) {
			this.description = description;
		}
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
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public String getUserID() {
		return userID;
	}

	/**
	 * Sets the user id.
	 *
	 * @param userID the new user id
	 */
	public void setUserID(String userID) {
		this.userID = userID;
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
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return eventData.getStatus();
	}
	
	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status) {
		eventData.setStatus(status);
	}
	
	/**
	 * Gets the reason.
	 *
	 * @return the reason
	 */
	public String getReason() {
		return eventData.getReason();
	}
	
	/**
	 * Sets the reason.
	 *
	 * @param reason the new reason
	 */
	public void setReason(String reason) {
		eventData.setReason(reason);
	}
	
	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return eventData.getDescription();
	}
	
	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		eventData.setDescription(description);
	}
}
