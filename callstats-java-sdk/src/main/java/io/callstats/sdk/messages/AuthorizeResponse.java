package io.callstats.sdk.messages;

/**
 * The Class AuthorizeResponse.
 *
 * @author Karthik Budigere
 */
public class AuthorizeResponse {
	
	/** The app id. */
	private int appID;
	
	/** The user id. */
	private String userID;
	
	/** The version. */
	private String version;
	
	/** The endpoint type. */
	private String endpointType;
	
	/** The authorize. */
	private AuthorizeResponseBody authorize;
	
	/**
	 * Instantiates a new authorize response.
	 */
	public AuthorizeResponse() {
		authorize = new AuthorizeResponseBody();
	}

	/**
	 * The Class AuthorizeResponseBody.
	 */
	class AuthorizeResponseBody {
		
		/** The status. */
		String status;
		
		/** The challenge. */
		String challenge;
		
		/** The reason. */
		String reason;
		
		/** The description. */
		String description;
		
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
		 * Gets the challenge.
		 *
		 * @return the challenge
		 */
		public String getChallenge() {
			return challenge;
		}
		
		/**
		 * Sets the challenge.
		 *
		 * @param challenge the new challenge
		 */
		public void setChallenge(String challenge) {
			this.challenge = challenge;
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
	}
	
	
	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return authorize.getDescription();
	}
	
	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		authorize.setDescription(description);
	}
		
	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return authorize.getStatus();
	}
	
	/**
	 * Gets the challenge.
	 *
	 * @return the challenge
	 */
	public String getChallenge() {
		return authorize.getChallenge();
	}
	
	/**
	 * Gets the reason.
	 *
	 * @return the reason
	 */
	public String getReason() {
		return authorize.getReason();
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
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status) {
		authorize.setStatus(status);
	}
	
	/**
	 * Sets the challenge.
	 *
	 * @param challenge the new challenge
	 */
	public void setChallenge(String challenge) {
		authorize.setChallenge(challenge);
	}
	
	/**
	 * Sets the reason.
	 *
	 * @param reason the new reason
	 */
	public void setReason(String reason) {
		authorize.setReason(reason);
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
}
