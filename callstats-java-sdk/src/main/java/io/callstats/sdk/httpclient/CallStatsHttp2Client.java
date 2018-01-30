package io.callstats.sdk.httpclient;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import io.callstats.sdk.internal.CallStatsConst;
import io.callstats.sdk.internal.NameValuePair;
import io.callstats.sdk.internal.listeners.CallStatsHttp2ResponseListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.CipherSuite;
import okhttp3.ConnectionPool;
import okhttp3.ConnectionSpec;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.FormBody.Builder;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.TlsVersion;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CallStatsHttp2Client {
	
	/** The base url. */
	private String EVENTS_BASE_URL;
	private String STATS_BASE_URL;
	private String AUTH_BASE_URL;
	
	/** The connection time out. */
	private int connectionTimeOut = CallStatsConst.CONNECTION_TIMEOUT;
	
	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger("CallStatsAsyncHttpClient");
	
	private OkHttpClient okHttpClientclient;
	private boolean isDisrupted;
	
	public boolean isDisrupted() {
		return isDisrupted;
	}

	public void setDisrupted(boolean isDisrupted) {
		this.isDisrupted = isDisrupted;
	}
	
	public CallStatsHttp2Client() {
		ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)  
	        .tlsVersions(TlsVersion.TLS_1_2)
	        .allEnabledCipherSuites()
	        .supportsTlsExtensions(true)
	        .build();

		okHttpClientclient = new OkHttpClient.Builder()
			.readTimeout(connectionTimeOut, TimeUnit.MILLISECONDS)
			.writeTimeout(connectionTimeOut, TimeUnit.MILLISECONDS)
			.connectionPool(new ConnectionPool())
			.connectionSpecs(Collections.singletonList(spec))
			.build();
		loadConfigurations();
	}
	
	private void loadConfigurations() {
		Properties prop = new Properties();
		InputStream input = null;
		
		try {
			input = new FileInputStream(CallStatsConst.CallStatsJavaSDKPropertyFileName);
			if (input !=null){
				prop.load(input);
				EVENTS_BASE_URL = prop.getProperty("CallStats.EventsBaseURL");
				STATS_BASE_URL = prop.getProperty("CallStats.StatsBaseURL");
				AUTH_BASE_URL = prop.getProperty("CallStats.AuthBaseURL");
				
				if (prop.getProperty("CallStats.ConnectionTimeOut") != null) {
					connectionTimeOut = Integer.parseInt(prop.getProperty("CallStats.ConnectionTimeOut"));
				}
				
				if (EVENTS_BASE_URL == null || STATS_BASE_URL == null) {
					logger.error("Callstats BASE URL can not be null");
					throw new RuntimeException("Callstats Events and Stats BASE URL can not be null");
				}
			}
			logger.info("Base URL is " + EVENTS_BASE_URL);
		}  catch (FileNotFoundException e ) {
			logger.error("Configuration file not found", e);
			throw new RuntimeException("Configuration file not found");
		} catch (IOException e ) {
			logger.error("Configuration file read IO exception", e);
			throw new RuntimeException("Configuration file read IO exception");
		}
	}
	
	private Request buildRequest(String url, String token, String body) {
		MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	    RequestBody msg = RequestBody.create(JSON, body);
	    Request request = new Request.Builder()
        .url(url)
        .addHeader("Accept", "application/json")
	    .addHeader("Accept-Charset", "utf-8")
	    .addHeader("Authorization", "Bearer "+token)
        .post(msg)
        .build();
	    return request;
	}
	
	public void sendBridgeEvents(String url, String token, String body, final CallStatsHttp2ResponseListener listener) {
		Request request = buildRequest(EVENTS_BASE_URL+url, token, body);
	    send(request, listener);
	}
	
	public void sendBridgeStats(String url, String token, String body, final CallStatsHttp2ResponseListener listener) {
		Request request = buildRequest(STATS_BASE_URL+url, token, body);
	    send(request, listener);
	}
	
	public void sendBridgeAlive(String url, String token, String body, final CallStatsHttp2ResponseListener listener) {
		Request request = buildRequest(STATS_BASE_URL+url, token, body);
		logger.info("sending to "+STATS_BASE_URL+url);
	    send(request, listener);
	}
	
	public void sendBridgeStatistics(String url, String token, String body, final CallStatsHttp2ResponseListener listener) {
		Request request = buildRequest(STATS_BASE_URL+url, token, body);
	    send(request, listener);
	}
	
	public void sendAuthRequest(String url, List<NameValuePair> paramList, final CallStatsHttp2ResponseListener listener) {
	    Builder builder = new FormBody.Builder();
	    for (int i=0; i<paramList.size(); i++) {
	    	NameValuePair nameValuePair = paramList.get(i);
	    	builder.addEncoded(nameValuePair.getName(), nameValuePair.getValue());
	    }
	    
	    RequestBody msg = builder.build();
	    Request request = new Request.Builder()
        .url(AUTH_BASE_URL+url)
        .addHeader("Content-type", "application/x-www-form-urlencoded")
	    .addHeader("Accept", "application/json")
        .post(msg)
        .build();
	    
	    send(request, listener);
	}
	
	private void send(Request request, final CallStatsHttp2ResponseListener listener) {
	    Call call = okHttpClientclient.newCall(request);
	    call.enqueue(new Callback() {
			public void onFailure(Call arg0, IOException e) {
				logger.info("failed " + e.toString());
				isDisrupted = true;
				listener.onFailure(e);
			}

			public void onResponse(Call arg0, Response response) throws IOException {
				logger.info("HTTP Req Completed, received response "+response.code());
				listener.onResponse(response);
			}
	    });
	}
}
