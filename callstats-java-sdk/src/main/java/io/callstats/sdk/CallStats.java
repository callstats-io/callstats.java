package io.callstats.sdk;

import java.io.IOException;

import io.callstats.sdk.messages.BridgeEventMessage;
import io.callstats.sdk.messages.BridgeEventResponse;

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
	private int appId;
	private String appSecret;
	private String bridgeId;
	private CallStatsInitListener listener;
	private CallStatsAuthenticator authenticator;
	private static final Logger logger = LogManager.getLogger("CallStats");
	private Gson gson;
	private EndpointInfo endpointInfo;
	private boolean isInitialized;
			
	public boolean isInitialized() {
		return isInitialized;
	}

	private void setInitialized(boolean isInitialized) {
		this.isInitialized = isInitialized;
	}

	public CallStats() {
		gson = new Gson();
	}

	public void intialize(int appId, String appSecret,String bridgeId,EndpointInfo endpointInfo,CallStatsInitListener callStatsInitListener) {
		if(appId <= 0 || appSecret == null || bridgeId == null || endpointInfo == null) {
			logger.error("intialize: Arguments cannot be null");
			throw new IllegalArgumentException("intialize: Arguments cannot be null");
		}
		
		this.appId = appId;
		this.appSecret = appSecret;
		this.bridgeId = bridgeId;
		this.listener = callStatsInitListener;
		this.endpointInfo = endpointInfo;
		
		httpClient = new CallStatsHttpClient();
		authenticator = new CallStatsAuthenticator(new CallStatsInitListener() {
			
			public void onInitialized(String msg) {
				listener.onInitialized(msg);
				setInitialized(true);
			}
			
			public void onError(CallStatsErrors error, String errMsg) {
				listener.onError(error, errMsg);;				
			}
		});		
		authenticator.doAuthentication(appId, appSecret, bridgeId,httpClient);
		
//		CallStatsAsyncHttpClient httpClient1;
//		httpClient1 = new CallStatsAsyncHttpClient();	
//		authenticator.doAuthentication1(appId, appSecret, bridgeId,httpClient1);
	}
	
	
	public void sendCallStatsBridgeEvent(HealthStatusData healthStatusData,TrafficStatusData trafficStatusData) {	
		if(isInitialized())	{			 
			long epoch = System.currentTimeMillis()/1000;				
			BridgeEventMessage eventMessage = new BridgeEventMessage(appId, bridgeId, CallStatsConst.CS_VERSION, CallStatsConst.END_POINT_TYPE, ""+epoch, authenticator.getToken(), healthStatusData, trafficStatusData, endpointInfo);
			String requestMessageString = gson.toJson(eventMessage);
			httpClient.sendAsyncHttpRequest(CallStatsConst.bridgeEventUrl,CallStatsConst.httpPostMethod, requestMessageString,new CallStatsHttpResponseListener() {
				public void onResponse(HttpResponse response) {
					
					int responseStatus = response.getStatusLine().getStatusCode();
					logger.info("Response "+response.toString()+":"+responseStatus);
					BridgeEventResponse eventResponseMessage;
					try {
						String responseString = EntityUtils.toString(response.getEntity());
						eventResponseMessage  = gson.fromJson(responseString,BridgeEventResponse.class);	
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
		} else {
			// TODO retransmission queue
		}
	}
	
}
