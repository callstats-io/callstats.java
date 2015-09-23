package io.callstats.sdk.messages;

/**
*
* @author Karthik Budigere
*/
public class AuthorizeRequest {
	private int appID;
	private String userID;
	private String version;
	private String endpointType;
	
	public AuthorizeRequest(int appID, String userID, String version,
			String endpointType) {
		super();
		this.appID = appID;
		this.userID = userID;
		this.version = version;
		this.endpointType = endpointType;
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
}
