package io.callstats.sdk.httpclient;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
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

import io.callstats.sdk.internal.CallStatsConst;
import io.callstats.sdk.internal.CallStatsUrls;
import io.callstats.sdk.internal.listeners.CallStatsHttpResponseListener;

/**
 * The Class CallStatsHttpClient.
 *
 * @author Karthik Budigere
 */
public class CallStatsHttpClient {	
	
	/** The base url. */
	//private static String BASE_URL = "https://c1-as-jc.callstats.io";
	private String BASE_URL;
	
	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger("CallStatsHttpClient");

	
	/** The http async client. */
	private HttpAsyncClient httpAsyncClient;
	
	/** The connection time out. */
	private int connectionTimeOut = CallStatsConst.CONNECTION_TIMEOUT;
	
	/** The so time out. */
	private int soTimeOut = CallStatsConst.SO_TIMEOUT;
	
	private boolean isDisrupted;
	
	private  RequestConfig config;
	private  CredentialsProvider credentialsProvider;
	
	private boolean isProxyEnabled = false;
	
	private String proxyHost;
	
	private int proxyPort;
	
	private boolean isProxySecure;
	
	private String proxyUserName;
	
	private String proxyPassword; 
	
	public boolean isDisrupted() {
		return isDisrupted;
	}

	public void setDisrupted(boolean isDisrupted) {
		this.isDisrupted = isDisrupted;
	}	
	
	/**
	 * Gets the base url.
	 *
	 * @return the base url
	 */
	public String getBaseUrl() {
		return BASE_URL;
	}
	
	/**
	 * Backwards compatibility
	 */
	
	public CallStatsHttpClient() {
		this(CallStatsUrls.STATS_SUBMIT_BASE);
	}
	
	
	/**
	 * Instantiates a new call stats http client.
	 *  @param url url
	 */
	public CallStatsHttpClient(CallStatsUrls url) {
		super();
		Properties prop = new Properties();
		InputStream input = null;

		try {
			input = new FileInputStream(CallStatsConst.CallStatsJavaSDKPropertyFileName);
			if (input != null) {
				prop.load(input);
				BASE_URL = prop.getProperty(url.toString());
				
				if (prop.getProperty("CallStats.ConnectionTimeOut") != null) {
					connectionTimeOut = Integer.parseInt(prop.getProperty("CallStats.ConnectionTimeOut"));
				}
				
				if (prop.getProperty("CallStats.SOTimeOut") != null) { 
					soTimeOut = Integer.parseInt(prop.getProperty("CallStats.SOTimeOut"));	
				}
				
				if (prop.getProperty("CallStats.HttpProxyUsed") != null) { 
					isProxyEnabled = Boolean.parseBoolean(prop.getProperty("CallStats.HttpProxyUsed"));	
				}
				
				if (prop.getProperty("CallStats.ProxyHost") != null) { 
					proxyHost = prop.getProperty("CallStats.ProxyHost");	
				}
				
				if (prop.getProperty("CallStats.ProxyPort") != null) { 
					proxyPort = Integer.parseInt(prop.getProperty("CallStats.ProxyPort"));	
				}
				
				if (prop.getProperty("CallStats.IsProxySecure") != null) { 
					isProxySecure = Boolean.parseBoolean(prop.getProperty("CallStats.IsProxySecure"));	
				}
				
				if (prop.getProperty("CallStats.ProxyUserName") != null) { 
					proxyUserName = prop.getProperty("CallStats.ProxyUserName");	
				}
				
				if (prop.getProperty("CallStats.ProxyPassword") != null) { 
					proxyPassword = prop.getProperty("CallStats.ProxyPassword");	
				}
				
				if (BASE_URL == null) {
					BASE_URL = url.getDefaultUrl();
				}
			}									
		}  catch (FileNotFoundException e ) {
			logger.error("Configuration file not found", e);
			throw new RuntimeException("Configuration file not found");
		} catch (IOException e ) {
			logger.error("Configuration file read IO exception", e);
			throw new RuntimeException("Configuration file read IO exception");
		}
		
		logger.info("Base URL is " + BASE_URL);
		// Create I/O reactor configuration
		IOReactorConfig ioReactorConfig = IOReactorConfig.custom().setIoThreadCount(Runtime.getRuntime().availableProcessors())
				.setConnectTimeout(connectionTimeOut).setSoTimeout(soTimeOut).build();

		// Create a custom I/O reactort
		ConnectingIOReactor ioReactor;
		try {
			ioReactor = new DefaultConnectingIOReactor(ioReactorConfig);
		} catch (IOReactorException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		PoolingNHttpClientConnectionManager asyncMgr = new PoolingNHttpClientConnectionManager(ioReactor);
		
		if (isProxyEnabled) {
			logger.info("Proxy Enabled " + proxyHost +":"+ proxyPort);
			HttpHost proxy = new HttpHost(proxyHost, proxyPort);
	        config = RequestConfig.custom()
	                .setProxy(proxy)
	                .build();
	        if (isProxySecure) {
	        		credentialsProvider = new BasicCredentialsProvider();
	        		credentialsProvider.setCredentials(new AuthScope(proxyHost, proxyPort),
		                new UsernamePasswordCredentials(proxyUserName, proxyPassword));
	        }
		}
		if (isProxySecure) {
			logger.info("Proxy is secure "+proxyUserName+":"+proxyPassword);
			httpAsyncClient = HttpAsyncClients.custom()
					.setDefaultCredentialsProvider(credentialsProvider)
					.setConnectionManager(asyncMgr).build();
		} else {
			httpAsyncClient = HttpAsyncClients.custom().setConnectionManager(asyncMgr).build();
		}
	}
	
	/**
	 * Generate http post request.
	 *
	 * @param url the url
	 * @param body the body
	 * @return the http post
	 */
	private HttpPost generateHttpPostRequest(String url, String body) {
		URI uri = generateURI(url);
		StringEntity entity = generateEntity(body);

		HttpPost post = new HttpPost(uri);
		post.setHeader("Content-type", "application/json");
		post.setHeader("Accept", "application/json");
		post.setEntity(entity);
		if (isProxyEnabled) {
			post.setConfig(config);
		}
		return post;
	}
	
	/**
	 * Generate http post request from form
	 *
	 * @param url the url
	 * @param body the body
	 * @return the http post
	 */
	private HttpPost generateHttpPostRequest(String url, UrlEncodedFormEntity form) {
		URI uri = generateURI(url);

		HttpPost post = new HttpPost(uri);
		post.setHeader("Content-type", "application/x-www-form-urlencoded");
	    post.setHeader("Accept", "application/json");
		post.setEntity(form);
		if (isProxyEnabled) {
			post.setConfig(config);
		}
		return post;
	}
	
	/**
	 * Generate uri.
	 *
	 * @param url the url
	 * @return the uri
	 */
	private URI generateURI(String url) {
		if (!url.startsWith("http")) {
			String apiUrl = url.toLowerCase();
			StringBuilder sb = new StringBuilder();
	
			sb.append(getBaseUrl());
	
			if (!apiUrl.startsWith("/")) {
				sb.append("/");
			}
			sb.append(url);
	
			url = sb.toString();
		}
		
		URI uri;
		try {
			uri = new URI(url);
		} catch (URISyntaxException e) {
			e.printStackTrace();
			throw new IllegalStateException("Invalid URL:" + url, e);
		}
		return uri;
	}
	
	/**
	 * Generate entity.
	 *
	 * @param body the body
	 * @return the string entity
	 */
	private StringEntity generateEntity(String body) {
		StringEntity entity;
		try {
			entity = new StringEntity(body);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		entity.setContentType("application/json");
		return entity;
	}
		
	/**
	 * Send async http request.
	 *
	 * @param url the url
	 * @param httpMethodType the http method type
	 * @param body the body
	 * @param listener the listener
	 */
	public void sendAsyncHttpRequest(String url, String httpMethodType, String body, final CallStatsHttpResponseListener listener) {
		if (StringUtils.isBlank(url) || StringUtils.isBlank(httpMethodType) || StringUtils.isBlank(body) || listener == null) {
			throw new IllegalArgumentException("sendHttpRequest: Arguments cannot be null");
		}
		
		HttpPost request = null;
		if (httpMethodType.equalsIgnoreCase(CallStatsConst.httpPostMethod)) {
			request = generateHttpPostRequest(url, body);
		}
		sendAsyncHttpRequest(url, httpMethodType, request, listener);
	}
	
	protected void sendAsyncHttpRequest(String url, String httpMethodType, HttpUriRequest request, final CallStatsHttpResponseListener listener) {
		String apiUrl = url.toLowerCase();
		StringBuilder sb = new StringBuilder();

		sb.append(getBaseUrl());

		if (!apiUrl.startsWith("/")) {
			sb.append("/");
		}
		sb.append(url);

		url = sb.toString();

		if (request != null) {
			request.addHeader(new BasicHeader("Accept", "application/json"));
			request.addHeader(new BasicHeader("Accept-Charset", "utf-8"));
			((CloseableHttpAsyncClient) httpAsyncClient).start();
			httpAsyncClient.execute(request, new FutureCallback<HttpResponse>() {

				public void failed(Exception e) {
					logger.info("failed " + e.toString());
					isDisrupted = true;
					listener.onFailure(e);
				}

				public void completed(HttpResponse response) {
					logger.info("HTTP Req Completed, received response ");
					listener.onResponse(response);
				}

				public void cancelled() {
					Exception e = new Exception("http request execute cancelled");
					logger.info("cancelled ");
					isDisrupted = true;
					listener.onFailure(e);
				}
			});
		}
	}
	
	/**
	 * Send async http request.
	 *
	 * @param url the url
	 * @param httpMethodType the http method type
	 * @param paramList the parameter list
	 * @param listener the listener
	 */
	public void sendAsyncHttpRequest(String url, String httpMethodType, List<NameValuePair> paramList , final CallStatsHttpResponseListener listener) {
		if (StringUtils.isBlank(url) || StringUtils.isBlank(httpMethodType) || paramList == null || paramList.size() == 0 || listener == null) {
			throw new IllegalArgumentException("sendHttpRequest: Arguments cannot be null");
		}

		HttpUriRequest request = null;

		if (httpMethodType.equalsIgnoreCase(CallStatsConst.httpPostMethod)) {
			try {
				request = generateHttpPostRequest(url, new UrlEncodedFormEntity(paramList));
			} catch (UnsupportedEncodingException e) {
				throw new IllegalArgumentException("sendHttpRequest: Unsupported encoding", e);
			}
		}
		sendAsyncHttpRequest(url, httpMethodType, request, listener);
	}
	
}
