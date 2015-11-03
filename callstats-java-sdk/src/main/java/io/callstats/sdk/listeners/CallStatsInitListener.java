package io.callstats.sdk.listeners;

import io.callstats.sdk.CallStatsErrors;

/**
 * The listener interface for receiving callStatsInitialization events.
 * 
 * @author Karthik Budigere
 */
public interface CallStatsInitListener {
	
	/**
	 * On intilization error.
	 *
	 * @param error the error
	 * @param errMsg the err msg
	 */
	void onError(CallStatsErrors error, String errMsg);
	
	/**
	 * On initialized.
	 *
	 * @param msg the msg
	 */
	void onInitialized(String msg);
}
