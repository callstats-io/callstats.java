package io.callstats.sdk;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

/**
*
* @author Karthik Budigere
*/
public class CallStatsTest{
	CallStats callstatslib;
	CallStatsInitListener listener;
	EndpointInfo endpointInfo;
	int appId = 497346896;
	String appSecret = "usKVYjMm3JoqGhILunSSgfJynYs=";
	
	@Before
	public void setUp() {	
		endpointInfo = new EndpointInfo();
		endpointInfo.setEndpointType(CallStatsConst.END_POINT_TYPE);
		endpointInfo.setName("JitSi");
		endpointInfo.setOs("LINUX");
		endpointInfo.setVer("4.4");		
		System.out.println("Setup completed");
		callstatslib = new CallStats();
		listener = mock(CallStatsInitListener.class);
	}
	
	@Test
	public void intializeTest() {
		callstatslib.intialize(497346896, appSecret, "jit.si.345",endpointInfo,listener);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String msg = "SDK authentication successful";
		verify(listener).onInitialized(msg);
	}
	
	@Test
	public void intializeWithInvalidAppIdTest() {
		CallStatsErrors error = CallStatsErrors.HTTP_ERROR;
		String errMsg = "SDK Authentication Error";
		callstatslib.intialize(175240363, appSecret, "jit.si.345",endpointInfo,listener);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		verify(listener).onError(error, errMsg);
	}
	
	@Test
	public void intializeWithNullArgsTest() {
		Throwable e = null;

		try {
		callstatslib.intialize(0, appSecret, "jit.si.345",endpointInfo,listener);
		} catch (Throwable e1) {
			e = e1;
		}
		assertTrue(e instanceof IllegalArgumentException);
		
		try {
			callstatslib.intialize(175240363, null, "jit.si.345",endpointInfo,listener);
		} catch (Throwable e1) {
				e = e1;
		}
		assertTrue(e instanceof IllegalArgumentException);
		
		try {
			callstatslib.intialize(175240363, appSecret, null,endpointInfo,listener);
		} catch (Throwable e1) {
				e = e1;
		}
		assertTrue(e instanceof IllegalArgumentException);
	}
	
	
	
	@Test
	public void intializeTestWithSendCallStatsEvent() {
		callstatslib.intialize(497346896, appSecret, "jit.si.345",endpointInfo,listener);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String msg = "SDK authentication successful";
		verify(listener).onInitialized(msg);
		
		for (int i=0;i<1000;i++) {
			HealthStatusData healthStatusData = new HealthStatusData();
			TrafficStatusData trafficStatusData = new TrafficStatusData();
			trafficStatusData.setIntervalLoss(i);
			callstatslib.sendCallStatsBridgeEvent(healthStatusData,trafficStatusData);
		}
		try {
			Thread.sleep(100000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
