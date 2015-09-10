package io.callstats.sdk;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Karthik Budigere
 */
public class CallStats {	
	private CallStatsHttpClient httpClient;
	private int appId;
	private String appSecret;
	private String bridgeId;
	private CallStatsInitListener listener;
	private CallStatsAuthenticator authenticator;
	private static final Logger logger = LogManager.getLogger("CallStats");
	
	public CallStatsHttpClient getHttpClient() {
		return httpClient;
	}

	public void setHttpClient(CallStatsHttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getBridgeId() {
		return bridgeId;
	}

	public void setBridgeId(String bridgeId) {
		this.bridgeId = bridgeId;
	}

	public CallStats() {
		
	}

	public void intialize(int appId, String appSecret, String bridgeId,CallStatsInitListener listener) {
		if(appId <= 0 || appSecret == null || bridgeId == null) {
			logger.error("intialize: Arguments cannot be null");
			throw new IllegalArgumentException("intialize: Arguments cannot be null");
		}
		
		this.appId = appId;
		this.appSecret = appSecret;
		this.bridgeId = bridgeId;
		this.listener = listener;
		
		httpClient = new CallStatsHttpClient();
		authenticator = new CallStatsAuthenticator(listener);
		authenticator.doAuthentication(appId, appSecret, bridgeId,httpClient);
	}
}
