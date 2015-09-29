package io.callstats.sdk.messages;

/**
 * The Class BridgeKeepAliveMessage.
 */
public class BridgeKeepAliveMessage {
	
	/** The app id. */
	private int appID;
	
	/** The bridge id. */
	private String bridgeID;
	
	/** The version. */
	private String version;
	
	/** The api ts. */
	private String apiTS;
	
	/** The token. */
	private String token;
		
	/**
	 * Instantiates a new bridge keep alive message.
	 *
	 * @param appID the app id
	 * @param bridgeID the bridge id
	 * @param version the version
	 * @param apiTS the api ts
	 * @param token the token
	 */
	public BridgeKeepAliveMessage(int appID, String bridgeID,
			String version, String apiTS, String token) {
		super();
		this.appID = appID;
		this.bridgeID = bridgeID;
		this.version = version;
		this.apiTS = apiTS;
		this.token = token;
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
	 * Gets the bridge id.
	 *
	 * @return the bridge id
	 */
	public String getBridgeID() {
		return bridgeID;
	}
	
	/**
	 * Sets the bridge id.
	 *
	 * @param bridgeID the new bridge id
	 */
	public void setBridgeID(String bridgeID) {
		this.bridgeID = bridgeID;
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
	 * Gets the api ts.
	 *
	 * @return the api ts
	 */
	public String getApiTS() {
		return apiTS;
	}
	
	/**
	 * Sets the api ts.
	 *
	 * @param apiTS the new api ts
	 */
	public void setApiTS(String apiTS) {
		this.apiTS = apiTS;
	}
	
	/**
	 * Gets the token.
	 *
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	
	/**
	 * Sets the token.
	 *
	 * @param token the new token
	 */
	public void setToken(String token) {
		this.token = token;
	}

}
