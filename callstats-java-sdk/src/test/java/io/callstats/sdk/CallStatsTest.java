package io.callstats.sdk;

import static org.junit.Assert.assertTrue;
import io.callstats.sdk.data.BridgeStatusInfo;
import io.callstats.sdk.data.BridgeStatusInfoBuilder;
import io.callstats.sdk.data.ConferenceInfo;
import io.callstats.sdk.data.ConferenceStats;
import io.callstats.sdk.data.ConferenceStatsBuilder;
import io.callstats.sdk.data.ConferenceStatsData;
import io.callstats.sdk.data.ServerInfo;
import io.callstats.sdk.data.StreamStats;
import io.callstats.sdk.data.StreamStatsData;
import io.callstats.sdk.data.UserInfo;
import io.callstats.sdk.listeners.CallStatsInitListener;
import io.callstats.sdk.listeners.CallStatsStartConferenceListener;

import java.util.Random;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;

import com.google.gson.Gson;

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
	public static int appId = 1234567; // appId from callstats.io's dashboard.
	
	/** The app secret. */
	public static String appSecret = "app_secret"; // app secret from callstats.io's dashboard.
	
	public static String bridgeId = "winteriscoming"; // unique bridge name
	
	private Gson gson;
	
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
		gson = new Gson();
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
		
		for (int i = 0; i < 2; i++) {
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
		}
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void chkJsonFormationForStats() {
		ConferenceStatsData conferenceStatsData = new ConferenceStatsData("3456");
		StreamStatsData data = new StreamStatsData(23, 200, 2345,3);
		StreamStats streamStats = new StreamStats("1234", "2345", "inbound", data);
		conferenceStatsData.addStreamStats("2345678", streamStats);
		data = new StreamStatsData(28, 201, 2342,4);
		streamStats = new StreamStats("1234", "2345", "outbound",data);
		conferenceStatsData.addStreamStats("2345679", streamStats);
		streamStats = new StreamStats("1234", "2345", "inbound", data);
		conferenceStatsData.addStreamStats("2345675", streamStats);
		streamStats = new StreamStats("1234", "2345", "inbound", data);
		conferenceStatsData.addStreamStats("2345676", streamStats);
		
		String jsonString = gson.toJson(conferenceStatsData);
		System.out.println("JSON is "+jsonString);
	}
	
	
	@Test
	public void initializeTestWihSendCallStatsConferenceStartEvent() {
		callstatslib.initialize(appId, appSecret, "jit.si.346",serverInfo,listener);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String msg = "SDK authentication successful";
		Mockito.verify(listener).onInitialized(msg);
		
		ConferenceInfo conferenceInfo = new ConferenceInfo("jackk", "2345");
		
		callstatslib.sendCallStatsConferenceEvent(CallStatsConferenceEvents.CONFERENCE_SETUP, conferenceInfo, new CallStatsStartConferenceListener() {
			
			public void onResponse(String  ucid) {
				// TODO Auto-generated method stub
				String userID = "2345";
				String confID = "jackk";
				System.out.println("UCID is "+ucid);
				UserInfo userInfo = new UserInfo(confID, userID , ucid);
				
				callstatslib.sendCallStatsConferenceEvent(CallStatsConferenceEvents.CONFERENCE_TERMINATED, userInfo);
							
				callstatslib.startStatsReportingForUser(userID,confID);
				ConferenceStats conferenceStats = new ConferenceStatsBuilder()
											.bytesSent(23456)
											.packetsSent(34556)
											.ssrc("34567898")
											.confID(confID)
											.fromUserID("2345")
											.localUserID("2345")
											.toUserID("1234")
											.statsType("inbound")
											.jitter(3)
											.rtt(34)
											.ucID(ucid)
											.build();
				callstatslib.sendConferenceStats(userID, conferenceStats);
				
				conferenceStats = new ConferenceStatsBuilder()
										.bytesSent(23456)
										.packetsSent(34556)
										.ssrc("34567899")
										.confID(confID)
										.fromUserID("2345")
										.localUserID("2345")
										.toUserID("1234")
										.statsType("inbound")
										.jitter(3)
										.rtt(34)
										.ucID(ucid)
										.build();
				callstatslib.sendConferenceStats(userID, conferenceStats);
				
				conferenceStats = new ConferenceStatsBuilder()
										.bytesSent(23456)
										.packetsSent(34556)
										.ssrc("34567890")
										.confID(confID)
										.fromUserID("2345")
										.localUserID("2345")
										.toUserID("1234")
										.statsType("outbound")
										.jitter(3)
										.rtt(34)
										.ucID(ucid)
										.build();
				callstatslib.sendConferenceStats(userID, conferenceStats);
				
				callstatslib.stopStatsReportingForUser(userID,confID);
				
			}
			
			public void onError(CallStatsErrors error, String errMsg) {
				// TODO Auto-generated method stub
				
			}
		});
		
			
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
