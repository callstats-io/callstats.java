package io.callstats.sdk.internal;

/**
 * The Class CallStatsConst.
 */
public class CallStatsConst {
	
	/** The Constant CallStatsJavaSDKPropertyFileName. */
	public static final String CallStatsJavaSDKPropertyFileName = "config/callstats-java-sdk.properties";
	
	/** The Constant CS_VERSION. */
	public static final String CS_VERSION = "0.1.0";	
	
	/** The Constant END_POINT_TYPE. */
	public static final String END_POINT_TYPE = "VideoBridge";
	
	public static final String AUTH_TYPE = "BasicAuth";
	
	/** The Constant bridgeEventUrl. */
	public static final String bridgeEventUrl = "/callStatsBridgeEvent";

	public static final String conferenceEventUrl = "/callStatsBridgeConfereceEvents";
	
	/** The Constant httpPostMethod. */
	public static final String httpPostMethod = "POST";
	
	public static final String ERROR = "Error";
	
	public static final String SUCCESS = "OK";
	
	public static final String INVALID_TOKEN = "Invalid client token";
	
	public static final int CONNECTION_TIMEOUT = 30000;
			
	public static final int SO_TIMEOUT = 30000;
	
	public static final int KEEPALIVE_INTERVAL = 1000;
}
