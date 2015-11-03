package io.callstats.sdk.internal.listeners;

import org.apache.http.HttpResponse;

/**
 * The listener interface for receiving callStatsHttpResponse events.
 *
 * @author Karthik Budigere
 */
public interface CallStatsHttpResponseListener {
	
	/**
	 * On response.
	 *
	 * @param response the response
	 */
	void onResponse (HttpResponse response);
	
	/**
	 * On failure.
	 *
	 * @param e the e
	 */
	void onFailure (Exception e);
}
