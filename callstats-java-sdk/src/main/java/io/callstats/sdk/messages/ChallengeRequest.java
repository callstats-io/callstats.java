package io.callstats.sdk.messages;

/**
*
* @author Karthik Budigere
*/
public class ChallengeRequest {
	private int appID;
	private String userID;
	private String version;
	private String endpointType;
	private ChallengeRequestBody challenge;

	class ChallengeRequestBody {
		String response;

		public String getResponse() {
			return response;
		}

		public void setResponse(String response) {
			this.response = response;
		}
	}
	
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

	public String getResponse() {
		return challenge.getResponse();
	}

	public void setResponse(String response) {
		challenge.setResponse(response);
	}
}
