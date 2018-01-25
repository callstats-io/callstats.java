package io.callstats.sdk.internal.listeners;

import okhttp3.Response;


public interface CallStatsHttp2ResponseListener {
	
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
