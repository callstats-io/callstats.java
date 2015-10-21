package io.callstats.sdk;

public interface CallStatsBridgeKeepAliveStatusListener {
	
	/**
	 * On intilization error.
	 *
	 * @param error the error
	 * @param errMsg the err msg
	 */
	void onKeepAliveError(CallStatsErrors error, String errMsg);
	
	
	void onSuccess();

}
