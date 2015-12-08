package io.callstats.sdk.httpclient;
import io.callstats.sdk.internal.CallStatsConst;
import io.callstats.sdk.internal.listeners.CallStatsAsyncHttpResponseListener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import com.ning.http.client.AsyncHttpClientConfig.Builder;
import com.ning.http.client.Request;
import com.ning.http.client.RequestBuilder;
import com.ning.http.client.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The Class CallStatsAsyncHttpClient.
 */
public class CallStatsAsyncHttpClient {
	
	/** The base url. */
	private String BASE_URL;
	
	/** The connection time out. */
	private int connectionTimeOut;
	
	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger("CallStatsAsyncHttpClient");
	
	/** The http client. */
	private AsyncHttpClient httpClient;

	/**
	 * Instantiates a new call stats async http client.
	 */
	public CallStatsAsyncHttpClient() {
		Properties prop = new Properties();
		InputStream input = null;
	     
		//input = getClass().getClassLoader().getResourceAsStream(CallStatsConst.CallStatsJavaSDKPropertyFileName);
		
		try {
			input = new FileInputStream(CallStatsConst.CallStatsJavaSDKPropertyFileName);
			if (input !=null){
				prop.load(input);
				BASE_URL = prop.getProperty("CallStats.BaseURL");
				connectionTimeOut = Integer.parseInt(prop.getProperty("CallStats.ConnectionTimeOut"));
			}
		}  catch (FileNotFoundException e ) {
			logger.error("Configuration file not found", e);
			throw new RuntimeException("Configuration file not found");
		} catch (IOException e ) {
			logger.error("Configuration file read IO exception", e);
			throw new RuntimeException("Configuration file read IO exception");
		}
			
		Builder builder = new AsyncHttpClientConfig.Builder();
	    builder.setCompressionEnforced(true)
	        .setAllowPoolingConnections(true)
	        .setRequestTimeout(connectionTimeOut)
	        .setAllowPoolingSslConnections(true)
	        .build();
	    httpClient = new AsyncHttpClient(builder.build());
	}
	
	/**
	 * Send async http request.
	 *
	 * @param url the url
	 * @param httpMethodType the http method type
	 * @param body the body
	 * @param listener the listener
	 */
	public void sendAsyncHttpRequest(String url, String httpMethodType, String body, final CallStatsAsyncHttpResponseListener listener) {
		if (StringUtils.isNotBlank(url) || StringUtils.isNotBlank(httpMethodType) || StringUtils.isNotBlank(body) || listener == null) {
			throw new IllegalArgumentException("sendAsyncHttpRequest: Arguments cannot be null");
		}
		
		RequestBuilder builder = new RequestBuilder("POST");
	    Request request = builder.setUrl(BASE_URL+url)
	     .addHeader("Accept", "application/json")
	     .addHeader("Accept-Charset", "utf-8")
	     .setBody(body)
	     .build();
	    httpClient.executeRequest(request, new AsyncCompletionHandler<Response>() {

			public Response onCompleted(Response response) throws Exception {
				logger.info("Response is " + response.getResponseBody());
				listener.onResponse(response);
				return null;
			}
			
			public void onThrowable(Throwable e) {
				listener.onFailure((Exception)e);
			}
		});
	}

}
