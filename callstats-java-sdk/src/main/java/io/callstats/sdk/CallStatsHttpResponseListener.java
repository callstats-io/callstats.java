package io.callstats.sdk;

import org.apache.http.HttpResponse;

/**
*
* @author Karthik Budigere
*/
public interface CallStatsHttpResponseListener {
	void onResponse (HttpResponse response);
	void onFailure (Exception e);
}
