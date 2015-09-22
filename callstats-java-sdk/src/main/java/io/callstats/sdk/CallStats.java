package io.callstats.sdk;

import java.io.IOException;

import io.callstats.sdk.messages.CallStatsEventMessage;
import io.callstats.sdk.messages.CallStatsEventResponse;


import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

/**
 *
 * @author Karthik Budigere
 */
public class CallStats {	
	private CallStatsHttpClient httpClient;
	public static final String CallStatsJavaSDKPropertyFileName = "callstats-java-sdk.properties";
	private int appId;
	private String appSecret;
	private String bridgeId;
	private CallStatsInitListener listener;
	private CallStatsAuthenticator authenticator;
	private static final Logger logger = LogManager.getLogger("CallStats");
	private Gson gson;
	private EndpointInfo endpointInfo;
	
	public static final String CS_VERSION = "0.1.0";	
	public static final String END_POINT_TYPE = "VideoBridge";
	public static final String bridgeEventsUrl = "/o/callStatsBridgeEvent";
	public static final String httpPostMethod = "POST";
	
	public CallStatsHttpClient getHttpClient() {
		return httpClient;
	}

	public void setHttpClient(CallStatsHttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getBridgeId() {
		return bridgeId;
	}

	public void setBridgeId(String bridgeId) {
		this.bridgeId = bridgeId;
	}

	public CallStats() {
		gson = new Gson();
	}

	public void intialize(int appId, String appSecret,String bridgeId,EndpointInfo endpointInfo,CallStatsInitListener listener) {
		if(appId <= 0 || appSecret == null || bridgeId == null || endpointInfo == null) {
			logger.error("intialize: Arguments cannot be null");
			throw new IllegalArgumentException("intialize: Arguments cannot be null");
		}
		
		this.appId = appId;
		this.appSecret = appSecret;
		this.bridgeId = bridgeId;
		this.listener = listener;
		this.endpointInfo = endpointInfo;
		
		httpClient = new CallStatsHttpClient();
		authenticator = new CallStatsAuthenticator(listener);		
		authenticator.doAuthentication(appId, appSecret, bridgeId,httpClient);
		
//		CallStatsAsyncHttpClient httpClient1;
//		httpClient1 = new CallStatsAsyncHttpClient();	
//		authenticator.doAuthentication1(appId, appSecret, bridgeId,httpClient1);
	}
	
	
	public void sendCallStatsEvents(HealthStatusData healthStatusData,TrafficStatusData trafficStatusData) {
	
		long epoch = System.currentTimeMillis()/1000;
		
		logger.info("Token is "+authenticator.getToken());
			
		CallStatsEventMessage eventMessage = new CallStatsEventMessage(appId, bridgeId, CS_VERSION, END_POINT_TYPE, ""+epoch, authenticator.getToken(), healthStatusData, trafficStatusData, endpointInfo);
		String requestMessageString = gson.toJson(eventMessage);
		httpClient.sendAsyncHttpRequest(bridgeEventsUrl,httpPostMethod, requestMessageString,new CallStatsHttpResponseListener() {
			public void onResponse(HttpResponse response) {
				
				int responseStatus = response.getStatusLine().getStatusCode();
				logger.info("Response "+response.toString()+":"+responseStatus);
				CallStatsEventResponse eventResponseMessage;
				try {
					String responseString = EntityUtils.toString(response.getEntity());
					eventResponseMessage  = gson.fromJson(responseString,CallStatsEventResponse.class);	
				} catch (ParseException e) {						
					e.printStackTrace();
					throw new RuntimeException(e);
				} catch (IOException e) {
					e.printStackTrace();
					throw new RuntimeException(e);					
				}
				if(responseStatus == 200) {
					logger.info("Response status is "+eventResponseMessage.getStatus()+":"+eventResponseMessage.getReason());
				}
			}
			
			public void onFailure(Exception e) {
				logger.info("Response exception"+e.toString());
			}
			
		});	
	}
}
