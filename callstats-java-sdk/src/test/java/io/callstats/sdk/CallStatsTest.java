package io.callstats.sdk;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class CallStatsTest.
 *
 * @author Karthik Budigere
 */
public class CallStatsTest{
	
	/** The callstatslib. */
	CallStats callstatslib;
	
	/** The listener. */
	CallStatsInitListener listener;
	
	/** The endpoint info. */
	ServerInfo serverInfo;
	
	/** The app id. */
	int appId = 1234567;
	
	/** The app secret. */
	String appSecret = "appSecret";
	
	/**
	 * Sets the up.
	 */
	@Before
	public void setUp() {	
		serverInfo = new ServerInfo();
		serverInfo.setName("JitSi");
		serverInfo.setOs("LINUX");
		serverInfo.setVer("4.4");		
		System.out.println("Setup completed");
		callstatslib = new CallStats();
		listener = mock(CallStatsInitListener.class);
	}
	
	/**
	 * Initialize test.
	 */
	@Test
	public void initializeTest() {
		callstatslib.initialize(appId, appSecret, "jit.si.346",serverInfo,listener);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String msg = "SDK authentication successful";
		verify(listener).onInitialized(msg);
	}
	
	/**
	 * Initialize with invalid app id test.
	 */
	@Test
	public void initializeWithInvalidAppIdTest() {
		CallStatsErrors error = CallStatsErrors.HTTP_ERROR;
		String errMsg = "SDK Authentication Error";
		callstatslib.initialize(appId+1, appSecret, "jit.si.345",serverInfo,listener);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		verify(listener).onError(error, errMsg);
	}
	
	/**
	 * Initialize with null args test.
	 */
	@Test
	public void intializeWithNullArgsTest() {
		Throwable e = null;

		try {
		callstatslib.initialize(0, appSecret, "jit.si.345",serverInfo,listener);
		} catch (Throwable e1) {
			e = e1;
		}
		assertTrue(e instanceof IllegalArgumentException);
		
		try {
			callstatslib.initialize(appId, null, "jit.si.345",serverInfo,listener);
		} catch (Throwable e1) {
				e = e1;
		}
		assertTrue(e instanceof IllegalArgumentException);
		
		try {
			callstatslib.initialize(appId, appSecret, null,serverInfo,listener);
		} catch (Throwable e1) {
				e = e1;
		}
		assertTrue(e instanceof IllegalArgumentException);
		
		try {
			callstatslib.initialize(appId, "", "jit.si.345",serverInfo,listener);
		} catch (Throwable e1) {
				e = e1;
		}
		assertTrue(e instanceof IllegalArgumentException);
		
		try {
			callstatslib.initialize(appId, appSecret, "",serverInfo,listener);
		} catch (Throwable e1) {
				e = e1;
		}
		assertTrue(e instanceof IllegalArgumentException);
	}
		
	
	/**
	 * Initialize test with send call stats event.
	 */
	@Test
	public void initializeTestWithSendCallStatsEvent() {
		callstatslib.initialize(appId, appSecret, "jit.si.346",serverInfo,listener);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String msg = "SDK authentication successful";
		verify(listener).onInitialized(msg);
		
		//for (int i=0;i<1000;i++) {
			BridgeStatusInfoBuilder bridgeStatusInfoBuilder = new BridgeStatusInfoBuilder();
			BridgeStatusInfo bridgeStatusInfo= bridgeStatusInfoBuilder
												.avgIntervalJitter(2)
												.cpuUsage(33)
												.intervalLoss(2)
												.build();						
			callstatslib.sendCallStatsBridgeEvent(bridgeStatusInfo);
		//}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
