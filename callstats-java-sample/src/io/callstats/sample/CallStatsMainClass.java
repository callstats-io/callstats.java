package io.callstats.sample;

import java.util.Random;

import io.callstats.sdk.BridgeStatusInfo;
import io.callstats.sdk.BridgeStatusInfoBuilder;
import io.callstats.sdk.CallStats;
import io.callstats.sdk.CallStatsConst;
import io.callstats.sdk.CallStatsErrors;
import io.callstats.sdk.CallStatsInitListener;
import io.callstats.sdk.ServerInfo;

public class CallStatsMainClass {
	
	//CallStats Library class
	private static CallStats callStatslib;
	
	private static int appId = 0000000;
	private static String appSecret = "appSecret";
	private static String bridgeId = "bridgeId";
	

	
	public static void main(String [ ] args)
	{
		//EndpointInfo sets the inforamtion realted to the endpoint such as name, os, version etc.
		ServerInfo endpointInfo = new ServerInfo();
		endpointInfo.setEndpointType(CallStatsConst.END_POINT_TYPE);
		endpointInfo.setName("Name");
		endpointInfo.setOs("LINUX");
		endpointInfo.setVer("4.4");	
		
		//Instantiate the callStats object
		callStatslib = new CallStats();
		
		//Intialize the callStats library using appId, appSecret and listener
		callStatslib.intialize(appId, appSecret, bridgeId, endpointInfo, new CallStatsInitListener() {
			
			@Override
			public void onInitialized(String msg) {
				// TODO Auto-generated method stub
				System.out.println("Initialized "+msg);
				//After the librar is intilized, video bridge can start sending the satus information.
				sendBridgeStatusInfoMessage();
			}
			
			@Override
			public void onError(CallStatsErrors error, String errMsg) {
				// TODO Auto-generated method stub
				System.out.println("Initialization Error "+errMsg);
			}
		});
				
	}
	
	public static void sendBridgeStatusInfoMessage() {
		//Building the video bridge status info.
		Random randomGenerator = new Random();
		BridgeStatusInfoBuilder bridgeStatusInfoBuilder = new BridgeStatusInfoBuilder();
		BridgeStatusInfo bridgeStatusInfo= bridgeStatusInfoBuilder
											.avgIntervalJitter(randomGenerator.nextFloat())
											.cpuUsage(randomGenerator.nextInt(100))
											.intervalLoss(randomGenerator.nextFloat())
											.build();	
		System.out.println("Sending bridge status info ");
		
		//Sending the video bridge status info.
		callStatslib.sendCallStatsBridgeEvent(bridgeStatusInfo);
	}

}
