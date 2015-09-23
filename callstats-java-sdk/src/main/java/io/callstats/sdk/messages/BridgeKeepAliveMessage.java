package io.callstats.sdk.messages;

public class BridgeKeepAliveMessage {
	private int appID;
	private String bridgeID;
	private String version;
	private String apiTS;
	private String token;
		
	public BridgeKeepAliveMessage(int appID, String bridgeID,
			String version, String apiTS, String token) {
		super();
		this.appID = appID;
		this.bridgeID = bridgeID;
		this.version = version;
		this.apiTS = apiTS;
		this.token = token;
	}
		
	public int getAppID() {
		return appID;
	}
	public void setAppID(int appID) {
		this.appID = appID;
	}
	public String getBridgeID() {
		return bridgeID;
	}
	public void setBridgeID(String bridgeID) {
		this.bridgeID = bridgeID;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getApiTS() {
		return apiTS;
	}
	public void setApiTS(String apiTS) {
		this.apiTS = apiTS;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

}
