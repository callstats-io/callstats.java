package io.callstats.sdk;


import com.ning.http.client.Response;

/**
*
* @author Karthik Budigere
*/
public interface CallStatsAsyncHttpResponseListener {
	void onResponse (Response response);
	void onFailure (Exception e);
}
