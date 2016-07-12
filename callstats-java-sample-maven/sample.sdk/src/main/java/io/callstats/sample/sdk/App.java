package io.callstats.sample.sdk;

import io.callstats.sdk.CallStats;
import io.callstats.sdk.CallStatsConferenceEvents;
import io.callstats.sdk.CallStatsErrors;
import io.callstats.sdk.ICallStatsTokenGenerator;
import io.callstats.sdk.data.BridgeStatusInfo;
import io.callstats.sdk.data.BridgeStatusInfoBuilder;
import io.callstats.sdk.data.CallStatsStreamType;
import io.callstats.sdk.data.ConferenceInfo;
import io.callstats.sdk.data.ConferenceStats;
import io.callstats.sdk.data.ConferenceStatsBuilder;
import io.callstats.sdk.data.ServerInfo;
import io.callstats.sdk.data.UserInfo;
import io.callstats.sdk.internal.CallStatsConst;
import io.callstats.sdk.listeners.CallStatsInitListener;
import io.callstats.sdk.listeners.CallStatsStartConferenceListener;

import java.util.Random;

public class App {

	// CallStats Library class
	private static CallStats callStatslib;

	private static int appId = 476165271;
	private static String keyId = "8a780f897771199253";
	private static String appSecret = "appSecret";
	//lUdrzU0L97cM
	private static String bridgeId = "bridgeId";
	// Use generated token rather than app secret
	private static boolean tokenauth = true;
	private static String ECDSAPrivateKeyPath = "/tmp/key2.pem";

	public static void main(String[] args) {

		// EndpointInfo sets the information related to the endpoint such as
		// name, os, version etc.
		ServerInfo endpointInfo = new ServerInfo();

		endpointInfo.setName("Name");
		endpointInfo.setOs("LINUX");
		endpointInfo.setVer("4.4");

		// Instantiate the callStats object
		callStatslib = new CallStats();
		CallStatsConst.CS_VERSION = "3.12.0";
		
		CallStatsInitListener callback = new CallStatsInitListener() {

			public void onInitialized(String msg) {
				// TODO Auto-generated method stub
				System.out.println("Initialized " + msg);
				// After the library is initialized, video bridge can start sending
				// the status information.
				sendBridgeStatusInfoMessage();
			}

			public void onError(CallStatsErrors error, String errMsg) {
				// TODO Auto-generated method stub
				System.out.println("Initialization Error " + errMsg);
			}
		};
		// Initialize the callStats library using appId, appSecret/token generator and listener
		if (tokenauth) {
			ICallStatsTokenGenerator tokenGenerator = new LocalTokenGenerator(appId, keyId, bridgeId, ECDSAPrivateKeyPath);
			callStatslib.initialize(appId, tokenGenerator, bridgeId, endpointInfo, callback);
		} else
			callStatslib.initialize(appId, appSecret, bridgeId, endpointInfo, callback);

	}

	public static void sendBridgeStatusInfoMessage() {
		// Building the video bridge status info.
		Random randomGenerator = new Random();
		BridgeStatusInfoBuilder bridgeStatusInfoBuilder = new BridgeStatusInfoBuilder();
		BridgeStatusInfo bridgeStatusInfo = bridgeStatusInfoBuilder.avgIntervalJitter(randomGenerator.nextFloat())
				.cpuUsage(randomGenerator.nextInt(100)).intervalRtpFractionLoss(randomGenerator.nextFloat()).build();
		System.out.println("Sending bridge status info ");

		// Sending the video bridge status info.
		callStatslib.sendCallStatsBridgeStatusUpdate(bridgeStatusInfo);
	}

	public static void sendBridgeConferenceEvents() {
		String confID = "winter_conference";
		String intiatorID = "2345";

		ConferenceInfo conferenceInfo = new ConferenceInfo(confID, intiatorID);

		// The first event for a conference to be sent should be
		// CONFERENCE_SETUP, the conferencelistener will give you back the ucid,
		// this ucid has to used in the further conference events and conference
		// stats submission
		callStatslib.sendCallStatsConferenceEvent(CallStatsConferenceEvents.CONFERENCE_SETUP, conferenceInfo,
				new CallStatsStartConferenceListener() {

					public void onResponse(String ucid) {
						sendConferenceStats(ucid);
					}

					public void onError(CallStatsErrors error, String errMsg) {
						// TODO Auto-generated method stub

					}
				});
	}

	public static void sendConferenceStats(String ucid) {
		String userID = "2345";
		String confID = "jackk";
		System.out.println("UCID is " + ucid);
		UserInfo userInfo = new UserInfo(confID, userID, ucid);

		callStatslib.sendCallStatsConferenceEvent(CallStatsConferenceEvents.CONFERENCE_TERMINATED, userInfo);

		callStatslib.startStatsReportingForUser(userID, confID);
		ConferenceStats conferenceStats = new ConferenceStatsBuilder().bytesSent(23456).packetsSent(34556)
				.ssrc("34567898").confID(confID).localUserID("2345").remoteUserID("1234")
				.statsType(CallStatsStreamType.INBOUND).jitter(0.7).rtt(2).ucID(ucid).build();
		callStatslib.reportConferenceStats(userID, conferenceStats);

		conferenceStats = new ConferenceStatsBuilder().bytesSent(23460).packetsSent(34560).ssrc("34567899")
				.confID(confID).localUserID("2345").remoteUserID("1234").statsType(CallStatsStreamType.INBOUND)
				.jitter(1).rtt(4).ucID(ucid).build();
		callStatslib.reportConferenceStats(userID, conferenceStats);

		conferenceStats = new ConferenceStatsBuilder().bytesSent(23470).packetsSent(34570).ssrc("34567890")
				.confID(confID).fromUserID("2345").localUserID("2345").remoteUserID("1234")
				.statsType(CallStatsStreamType.OUTBOUND).jitter(0.9).rtt(3).ucID(ucid).build();
		callStatslib.reportConferenceStats(userID, conferenceStats);

		callStatslib.stopStatsReportingForUser(userID, confID);
	}
}