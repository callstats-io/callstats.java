package io.callstats.sdk.messages;

public class AuthenticateRequest {

	private String appID;

	private String userID;
	private String version;
	private String endpointType;
	private String token;

	public AuthenticateRequest(int appID, String userID, String token, String version,
			String endpointType) {
		super();
		this.appID = Integer.toString(appID);
		this.userID = userID;
		this.token = token;
		this.version = version;
		this.endpointType = endpointType;
	}
	
	public String getAppID() {
		return appID;
	}

	public void setAppID(int appID) {
		this.appID = Integer.toString(appID);
	}
	
	public void setAppID(String appID) {
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
