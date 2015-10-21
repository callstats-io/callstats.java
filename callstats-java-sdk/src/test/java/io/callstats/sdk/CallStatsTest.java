package io.callstats.sdk;

import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;

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
	public static int appId = 1234567;
	
	/** The app secret. */
	public static String appSecret = "app secret=";
	
	public static String bridgeId = "jit.si.345";
	
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
		callstatslib = PowerMockito.spy(new CallStats());
		listener = Mockito.mock(CallStatsInitListener.class);
	}
	
	/**
	 * Initialize test.
	 */
	@Test
	public void initializeTest() {

		callstatslib.initialize(appId, appSecret, "jit.si.345",serverInfo,listener);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String msg = "SDK authentication successful";
		Mockito.verify(listener).onInitialized(msg);
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
		Mockito.verify(listener).onError(error, errMsg);
	}
	
	/**
	 * Initialize with null args test.
	 */
	@Test
	public void initializeWithNullArgsTest() {
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
		Random randomGenerator = new Random();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String msg = "SDK authentication successful";
		Mockito.verify(listener).onInitialized(msg);
		
		//for (int i = 0; i < 50; i++) {
			BridgeStatusInfoBuilder bridgeStatusInfoBuilder = new BridgeStatusInfoBuilder();
			BridgeStatusInfo bridgeStatusInfo = bridgeStatusInfoBuilder				
					.cpuUsage(randomGenerator.nextInt(100))
					.threadCount(randomGenerator.nextInt(1000))
					.memoryUsage(randomGenerator.nextInt(5000))
					.intervalRtpFractionLoss(randomGenerator.nextFloat())
					.avgIntervalJitter(randomGenerator.nextFloat())
					.avgIntervalRtt(randomGenerator.nextFloat())
					.conferenceCount(randomGenerator.nextInt(100))
					.participantsCount(randomGenerator.nextInt(5000))
					.videoFabricCount(randomGenerator.nextInt(1000))
					.audioFabricCount(randomGenerator.nextInt(1000))
					.intervalDownloadBitRate(randomGenerator.nextInt(100))
					.intervalUploadBitRate(randomGenerator.nextInt(100))
					.totalLoss(randomGenerator.nextInt(100))
					.intervalSentBytes(randomGenerator.nextInt(10000))
					.intervalReceivedBytes(randomGenerator.nextInt(10000))
					.build();
			callstatslib.sendCallStatsBridgeStatusUpdate(bridgeStatusInfo);
		//}
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
