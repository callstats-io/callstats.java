package io.callstats.sdk;



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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CallStatsAsyncHttpClient {
	
	private String BASE_URL;
	private int connectionTimeOut;
	private static final Logger logger = LogManager.getLogger("CallStatsAsyncHttpClient");
	private AsyncHttpClient httpClient;

	public CallStatsAsyncHttpClient() {
		Properties prop = new Properties();
		InputStream input = null;
	     
    
		input = getClass().getClassLoader().getResourceAsStream(CallStatsConst.CallStatsJavaSDKPropertyFileName);
		if(input !=null){
			try {
				prop.load(input);
				BASE_URL = prop.getProperty("CallStats.BaseURL");
				connectionTimeOut = Integer.parseInt(prop.getProperty("CallStats.ConnectionTimeOut"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		
		Builder builder = new AsyncHttpClientConfig.Builder();
	    builder.setCompressionEnforced(true)
	        .setAllowPoolingConnections(true)
	        .setRequestTimeout(connectionTimeOut)
	        .setAllowPoolingSslConnections(true)
	        .build();
	    httpClient = new AsyncHttpClient(builder.build());
	}
	
	public void sendAsyncHttpRequest(String url, String httpMethodType, String body,final CallStatsAsyncHttpResponseListener listener) {
		if(url == null || httpMethodType == null || body == null || listener == null) {
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
				// TODO Auto-generated method stub
				logger.info("Response is "+response.getResponseBody());
				listener.onResponse(response);
				return null;
			}
			
			public void onThrowable(Throwable e) {
				// TODO Auto-generated method stub
				listener.onFailure((Exception)e);
			}
		});
	}

}
