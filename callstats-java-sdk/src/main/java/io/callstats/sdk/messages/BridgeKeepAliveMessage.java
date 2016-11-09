package io.callstats.sdk.messages;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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
	private long apiTS;
	
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
	 * @throws UnsupportedEncodingException 
	 */
	public BridgeKeepAliveMessage(int appID, String bridgeID,
			String version, long apiTS, String token) throws UnsupportedEncodingException {
		super();
		this.appID = appID;
		this.bridgeID = URLEncoder.encode(bridgeID, "UTF-8");
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
	 * @throws UnsupportedEncodingException 
	 */
	public void setBridgeID(String bridgeID) throws UnsupportedEncodingException {
		this.bridgeID = URLEncoder.encode(bridgeID, "UTF-8");
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
	public long getApiTS() {
		return apiTS;
	}
	
	/**
	 * Sets the api ts.
	 *
	 * @param apiTS the new api ts
	 */
	public void setApiTS(long apiTS) {
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
