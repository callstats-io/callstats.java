package io.callstats.sdk;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.message.BasicHeader;
import org.apache.http.nio.client.HttpAsyncClient;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
*
* @author Karthik Budigere
*/
public class CallStatsHttpClient {
	
	private static String BASE_URL = "https://c1-as-jitsi.callstats.io";
	private static final Logger logger = LogManager.getLogger("CallStatsHttpClient");
	
	private String appId;
	private String authToken;
	private int numRetries = 3;
	private HttpClient httpclient;
	private HttpAsyncClient httpAsyncClient;
	
	public HttpClient getHttpclient() {
		return httpclient;
	}
	
	public void setHttpclient(HttpClient httpclient) {
		this.httpclient = httpclient;
	}

	public int getNumRetries() {
		return numRetries;
	}
	
	public void setNumRetries(int numRetries) {
		this.numRetries = numRetries;
	}
	
	public String getAppId() {
		return appId;
	}
	
	public String getAuthToken() {
		return authToken;
	}
	
	public static String getBaseUrl() {
		return BASE_URL;
	}
	
	public CallStatsHttpClient() {
		super();  
		
        // Create I/O reactor configuration
        IOReactorConfig ioReactorConfig = IOReactorConfig.custom()
                .setIoThreadCount(Runtime.getRuntime().availableProcessors())
                .setConnectTimeout(30000)
                .setSoTimeout(30000)
                .build();

        // Create a custom I/O reactort
        ConnectingIOReactor ioReactor;
		try {
			ioReactor = new DefaultConnectingIOReactor(ioReactorConfig);
		} catch (IOReactorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
        
        
		PoolingHttpClientConnectionManager mgr;
		mgr = new PoolingHttpClientConnectionManager();
		((PoolingHttpClientConnectionManager) mgr).setDefaultMaxPerRoute(10);
		httpclient = HttpClients.custom()
	            .setConnectionManager(mgr)
	            .build();
		
		PoolingNHttpClientConnectionManager asyncMgr;
		asyncMgr = new PoolingNHttpClientConnectionManager(ioReactor);
		httpAsyncClient = HttpAsyncClients.custom()
				.setConnectionManager(asyncMgr)
	            .build();
	}
	
	private HttpPost generateHttpPostRequest(String url, String body) {
		URI uri = generateURI(url);
		StringEntity entity = generateEntity(body);

		HttpPost post = new HttpPost(uri);
		post.setHeader("Content-type", "application/json");
	    post.setHeader("Accept", "application/json");
		post.setEntity(entity);

		return post;
	}
	
	private URI generateURI(String url) {
		URI uri;
		try {
			uri = new URI(url);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IllegalStateException("Invalid URL:"+url, e);
		}
		return uri;
	}
	
	private StringEntity generateEntity(String body) {
		StringEntity entity;
		try {
			entity = new StringEntity(body);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		entity.setContentType("application/json");		
		return entity;
	}
		
	public HttpResponse sendHttpRequest(String url, String httpMethodType, String body) {
		
		if(url == null || httpMethodType == null || body == null) {
			throw new IllegalArgumentException("sendHttpRequest: Arguments cannot be null");
		}
	
		HttpUriRequest request = null;
		String apiUrl = url.toLowerCase();
		StringBuilder sb = new StringBuilder();

		sb.append(getBaseUrl());

		if (!apiUrl.startsWith("/")) {
			sb.append("/");
		}
		sb.append(url);
		
		url = sb.toString();
		if (httpMethodType.equalsIgnoreCase("POST")) {
			request = generateHttpPostRequest(url,body);
		}
		
		if(request != null) {
			request.addHeader(new BasicHeader("Accept", "application/json"));
			request.addHeader(new BasicHeader("Accept-Charset", "utf-8"));
			HttpResponse response;
			try {
				response = httpclient.execute(request);
				logger.info("Response is "+response);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
		return null;
	}
	
	
	public void sendAsyncHttpRequest(String url, String httpMethodType, String body,final CallStatsHttpResponseListener listener) {
		if(url == null || httpMethodType == null || body == null || listener == null) {
			throw new IllegalArgumentException("sendAsyncHttpRequest: Arguments cannot be null");
		}
		
		HttpUriRequest request = null;
		String apiUrl = url.toLowerCase();
		StringBuilder sb = new StringBuilder();

		sb.append(getBaseUrl());

		if (!apiUrl.startsWith("/")) {
			sb.append("/");
		}
		sb.append(url);
		
		url = sb.toString();
		if (httpMethodType.equalsIgnoreCase("POST")) {
			request = generateHttpPostRequest(url,body);
		}
		
		if(request != null) {
			request.addHeader(new BasicHeader("Accept", "application/json"));
			request.addHeader(new BasicHeader("Accept-Charset", "utf-8"));
			((CloseableHttpAsyncClient) httpAsyncClient).start();
			httpAsyncClient.execute(request, new FutureCallback<HttpResponse>() {
				
				public void failed(Exception e) {
					listener.onFailure(e);
					logger.info("failed "+e.toString());
				}
				
				public void completed(HttpResponse response) {
					logger.info("HTTP Req Completed, received response ");
					listener.onResponse(response);
				}
				
				public void cancelled() {
					Exception e = new Exception();
					listener.onFailure(e);
					logger.info("cancelled ");					
				}
			});
		}
	}
	
}
