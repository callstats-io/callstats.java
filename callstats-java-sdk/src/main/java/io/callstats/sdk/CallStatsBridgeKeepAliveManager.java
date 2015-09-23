package io.callstats.sdk;

import io.callstats.sdk.messages.BridgeKeepAliveMessage; 
import io.callstats.sdk.messages.BridgeKeepAliveResponse;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

public class CallStatsBridgeKeepAliveManager {
	private static final String keepAliveEventUrl = "/o/callStatsKeepAliveEvent";
	private int appId;
	private String bridgeId;
	private String token;
	private static final ScheduledExecutorService scheduler = 
			  Executors.newSingleThreadScheduledExecutor();
	private Gson gson;
	private static final Logger logger = LogManager.getLogger("CallStatsBridgeKeepAliveManager");
	
	public CallStatsBridgeKeepAliveManager(int appId,
			String bridgeId, String token,final CallStatsHttpClient httpClient) {
		super();
		this.appId = appId;
		this.bridgeId = bridgeId;
		this.token = token;
		gson = new Gson();
		startKeepAliveSender(httpClient);
	}
	
	private void startKeepAliveSender(final CallStatsHttpClient httpClient) {
		scheduler.scheduleAtFixedRate(new Runnable() {			
			public void run() {
				sendKeepAliveBridgeMessage(appId,bridgeId,token,httpClient);
			}
		}, 0,1000,TimeUnit.MILLISECONDS);
	}
	
	
	private void sendKeepAliveBridgeMessage(int appId,String bridgeId,String token,CallStatsHttpClient httpClient) {
		long epoch = System.currentTimeMillis()/1000;	
		String apiTS = ""+epoch;
		BridgeKeepAliveMessage message = new BridgeKeepAliveMessage(appId, bridgeId, CallStatsConst.CS_VERSION, apiTS, token);
		String requestMessageString = gson.toJson(message);
		httpClient.sendAsyncHttpRequest(keepAliveEventUrl,CallStatsConst.httpPostMethod, requestMessageString,new CallStatsHttpResponseListener() {
			public void onResponse(HttpResponse response) {
				
				int responseStatus = response.getStatusLine().getStatusCode();
				logger.info("Response "+response.toString()+":"+responseStatus);
				BridgeKeepAliveResponse keepAliveResponse;
				try {
					String responseString = EntityUtils.toString(response.getEntity());
					keepAliveResponse  = gson.fromJson(responseString,BridgeKeepAliveResponse.class);	
				} catch (ParseException e) {						
					e.printStackTrace();
					throw new RuntimeException(e);
				} catch (IOException e) {
					e.printStackTrace();
					throw new RuntimeException(e);					
				}
				if(responseStatus == 200) {
					logger.info("Response status is "+keepAliveResponse.getStatus()+":"+keepAliveResponse.getReason());
				}
			}
			
			public void onFailure(Exception e) {
				logger.info("Response exception"+e.toString());
			}
			
		});	
	}
}