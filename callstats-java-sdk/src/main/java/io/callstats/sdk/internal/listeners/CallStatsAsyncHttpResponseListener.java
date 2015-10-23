package io.callstats.sdk.internal.listeners;


import com.ning.http.client.Response;

/**
 * The listener interface for receiving callStatsAsyncHttpResponse events.
 *
 * @author Karthik Budigere
 */
public interface CallStatsAsyncHttpResponseListener {
	
	/**
	 * On response.
	 *
	 * @param response the response
	 */
	void onResponse (Response response);
	
	/**
	 * On failure.
	 *
	 * @param e the e
	 */
	void onFailure (Exception e);
}
