package io.callstats.sdk.messages;

import io.callstats.sdk.internal.CallStatsConst;

/**
 * The Class ChallengeRequest.
 *
 * @author Karthik Budigere
 */
public class ChallengeRequest {
	
	/** The app id. */
	private int appID;
	
	/** The user id. */
	private String userID;
	
	/** The version. */
	private String version;
	
	/** The endpoint type. */
	private String endpointType;
	
	private String authType = CallStatsConst.AUTH_TYPE;
	
	/** The challenge. */
	private ChallengeRequestBody challenge;

	/**
	 * The Class ChallengeRequestBody.
	 */
	class ChallengeRequestBody {
		
		/** The response. */
		String response;

		/**
		 * Gets the response.
		 *
		 * @return the response
		 */
		public String getResponse() {
			return response;
		}

		/**
		 * Sets the response.
		 *
		 * @param response the new response
		 */
		public void setResponse(String response) {
			this.response = response;
		}
	}
	
	/**
	 * Instantiates a new challenge request.
	 *
	 * @param appID the app id
	 * @param userID the user id
	 * @param version the version
	 * @param endpointType the endpoint type
	 * @param response the response
	 */
	public ChallengeRequest(int appID, String userID, String version,
			String endpointType, String response) {
		super();
		this.appID = appID;
		this.userID = userID;
		this.version = version;
		this.endpointType = endpointType;
		challenge = new ChallengeRequestBody();
		challenge.setResponse(response);
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
	 * Gets the response.
	 *
	 * @return the response
	 */
	public String getResponse() {
		return challenge.getResponse();
	}

	/**
	 * Sets the response.
	 *
	 * @param response the new response
	 */
	public void setResponse(String response) {
		challenge.setResponse(response);
	}
	
	public String getAuthType() {
		return authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}
}
