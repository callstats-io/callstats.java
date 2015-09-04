package io.callstats.sdk;

/**
*
* @author Karthik Budigere
*/
public interface CallStatsInitListener {
	void onError(CallStatsErrors error, String errMsg);
	void onInitialized(String msg);
}
