package io.callstats.sdk.internal;


import io.callstats.sdk.CallStatsErrors;

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
