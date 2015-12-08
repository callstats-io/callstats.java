package io.callstats.sdk;

import io.callstats.sdk.data.BridgeStatusInfo;
import io.callstats.sdk.data.ConferenceInfo;
import io.callstats.sdk.data.ConferenceStats;
import io.callstats.sdk.data.ConferenceStatsData;
import io.callstats.sdk.data.ServerInfo;
import io.callstats.sdk.data.StreamStats;
import io.callstats.sdk.data.StreamStatsData;
import io.callstats.sdk.data.UserInfo;
import io.callstats.sdk.httpclient.CallStatsHttpClient;
import io.callstats.sdk.internal.BridgeStatusInfoQueue;
import io.callstats.sdk.internal.CallStatsAuthenticator;
import io.callstats.sdk.internal.CallStatsBridgeKeepAliveManager;
import io.callstats.sdk.internal.CallStatsBridgeKeepAliveStatusListener;
import io.callstats.sdk.internal.CallStatsConst;
import io.callstats.sdk.internal.CallStatsResponseStatus;
import io.callstats.sdk.internal.listeners.CallStatsHttpResponseListener;
import io.callstats.sdk.listeners.CallStatsInitListener;
import io.callstats.sdk.listeners.CallStatsStartConferenceListener;
import io.callstats.sdk.messages.BridgeStatusUpdateMessage;
import io.callstats.sdk.messages.BridgeStatusUpdateResponse;
import io.callstats.sdk.messages.CallStatsEventMessage;
import io.callstats.sdk.messages.CallStatsEventResponseMessage;
import io.callstats.sdk.messages.EventInfo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * The Class CallStats.
 *
 * @author Karthik Budigere
 */
public class CallStats {	
	
	/** The http client. */
	private CallStatsHttpClient httpClient;
	
	/** The app id. */
	private int appId;
	
	/** The app secret. */
	private String appSecret;
	
	/** The bridge id. */
	private String bridgeId;
	
	/** The listener. */
	private CallStatsInitListener listener;
	
	/** The authenticator. */
	private CallStatsAuthenticator authenticator;
	
	/** The logger. */
	private static final Logger logger = LogManager.getLogger("CallStats");
	
	/** The gson. */
	private Gson gson;
	
	/** The server info. */
	private ServerInfo serverInfo;
	
	/** The is initialized. */
	private boolean isInitialized;
	
	private BridgeStatusInfoQueue bridgeStatusInfoQueue;
	
	
	private Map<String, List<ConferenceStats>> conferenceStatsMap = new HashMap<String,List<ConferenceStats>>();
			
	
	/** The bridge keep alive manager. */
	private CallStatsBridgeKeepAliveManager bridgeKeepAliveManager;
	
	/**
	 * Checks if is initialized.
	 *
	 * @return true, if is initialized
	 */
	public boolean isInitialized() {
	    return isInitialized;
	}

	/**
	 * Sets the initialized.
	 *
	 * @param isInitialized the new initialized
	 */
	private void setInitialized(boolean isInitialized) {
		this.isInitialized = isInitialized;
	}

	/**
	 * Instantiates a new call stats.
	 */
	public CallStats() {
		gson = new Gson();
		bridgeStatusInfoQueue = new BridgeStatusInfoQueue();
	}
	
	private String getToken() {
		return authenticator.getToken();
	}
	
	/**
	 * Initialize callstats.
	 *
	 * @param appId the app id
	 * @param appSecret the app secret
	 * @param bridgeId the bridge id
	 * @param serverInfo the server info
	 * @param callStatsInitListener the call stats init listener
	 */

	public void initialize(final int appId, final String appSecret,
			final String bridgeId, final ServerInfo serverInfo,
			final CallStatsInitListener callStatsInitListener) {
		if (appId <= 0 || StringUtils.isBlank(appSecret)
				|| StringUtils.isBlank(bridgeId) || serverInfo == null
				|| callStatsInitListener == null) {
			logger.error("intialize: Arguments cannot be null ");
			throw new IllegalArgumentException("intialize: Arguments cannot be null");
		}

		this.appId = appId;
		this.appSecret = appSecret;
		this.bridgeId = bridgeId;
		this.listener = callStatsInitListener;
		this.serverInfo = serverInfo;
		
		httpClient = new CallStatsHttpClient();
		authenticator = new CallStatsAuthenticator(appId, this.appSecret, bridgeId, httpClient, new CallStatsInitListener() {
			public void onInitialized(String msg) {
				setInitialized(true);
				logger.info("SDK Initialized " + msg);
				if (bridgeKeepAliveManager == null) {
					bridgeKeepAliveManager = new CallStatsBridgeKeepAliveManager(appId, bridgeId, authenticator.getToken(), httpClient, new CallStatsBridgeKeepAliveStatusListener() {
						public void onKeepAliveError(
								CallStatsErrors error,
								String errMsg) {
							if (error == CallStatsErrors.AUTH_ERROR) {
								authenticator.doAuthentication();
							}
						}

						public void onSuccess() {
							sendCallStatsBridgeStatusUpdateFromQueue();
						}
					});
				}
				bridgeKeepAliveManager.startKeepAliveSender(authenticator.getToken());
				listener.onInitialized(msg);
			}

			public void onError(CallStatsErrors error, String errMsg) {
				logger.info("SDK Initialization Failed " + errMsg);
				listener.onError(error, errMsg);;
			}
		});
		authenticator.doAuthentication();
		
//		CallStatsAsyncHttpClient httpClient1;
//		httpClient1 = new CallStatsAsyncHttpClient();	
//		authenticator.doAuthentication1(appId, appSecret, bridgeId,httpClient1);
	}
	
	

	/**
	 * Send call stats bridge status update.
	 *
	 * @param bridgeStatusInfo the bridge status info
	 */
	public void sendCallStatsBridgeStatusUpdate(BridgeStatusInfo bridgeStatusInfo) {
		if (isInitialized()) {
			long epoch = System.currentTimeMillis() / 1000;
			String token = getToken();
			BridgeStatusUpdateMessage eventMessage = new BridgeStatusUpdateMessage(
					appId, bridgeId, CallStatsConst.CS_VERSION,
					CallStatsConst.END_POINT_TYPE, "" + epoch, token,
					bridgeStatusInfo, serverInfo);
			String requestMessageString = gson.toJson(eventMessage);
			httpClient.sendAsyncHttpRequest(CallStatsConst.bridgeEventUrl, CallStatsConst.httpPostMethod, requestMessageString, new CallStatsHttpResponseListener() {
				public void onResponse(HttpResponse response) {
					int responseStatus = response.getStatusLine().getStatusCode();
					logger.debug("Response " + response.toString() + ":" + responseStatus);
					if (responseStatus == CallStatsResponseStatus.RESPONSE_STATUS_SUCCESS) {
						BridgeStatusUpdateResponse eventResponseMessage;
						try {
							String responseString = EntityUtils.toString(response.getEntity());
							eventResponseMessage = gson.fromJson(responseString, BridgeStatusUpdateResponse.class);
						} catch (ParseException e) {
							logger.error("ParseException " + e.getMessage(), e);
							throw new RuntimeException(e);
						} catch (IOException e) {
							logger.error("IO Exception " + e.getMessage(), e);
							throw new RuntimeException(e);
						} catch (JsonSyntaxException e) {
							logger.error("Json Syntax Exception " + e.getMessage(), e);
							e.printStackTrace();
							throw new RuntimeException(e);
						}
						logger.debug("Response status is " + eventResponseMessage.getStatus()
								+ ":" + eventResponseMessage.getReason());
						if (eventResponseMessage.getStatus().equals(CallStatsConst.ERROR)
								&& eventResponseMessage.getReason().contains(CallStatsErrors.INVALID_TOKEN_ERROR.getReason())) {
							// logger.debug("Response status is "+eventResponseMessage.getStatus()+":"+eventResponseMessage.getReason());
							bridgeKeepAliveManager.stopKeepAliveSender();
							authenticator.doAuthentication();
						}
						httpClient.setDisrupted(false);
						sendCallStatsBridgeStatusUpdateFromQueue();
					} else {
						httpClient.setDisrupted(true);
					}
				}

				public void onFailure(Exception e) {
					logger.error("Response exception" + e.getMessage(), e);
					httpClient.setDisrupted(true);
				}

			});
		} else {
			// TODO retransmission queue
			// throw new
			// UnsupportedOperationException("queueing not implemented yet");
			bridgeStatusInfoQueue.push(bridgeStatusInfo);
		}
	}
	
	
	public synchronized void sendCallStatsBridgeStatusUpdateFromQueue() {
		if (bridgeStatusInfoQueue.getLength() < 1)
			return;

		while (bridgeStatusInfoQueue.getLength() > 0) {
			BridgeStatusInfo bridgeStatusInfo = bridgeStatusInfoQueue.pop();
			sendCallStatsBridgeStatusUpdate(bridgeStatusInfo);
		}
	}

	public synchronized void sendCallStatsConferenceEvent(CallStatsConferenceEvents eventType, ConferenceInfo conferenceInfo,
			final CallStatsStartConferenceListener listener) {
		if (eventType == null || conferenceInfo == null || listener == null) {
			logger.error("sendCallStatsConferenceEvent: Arguments cannot be null ");
			throw new IllegalArgumentException("sendCallStatsConferenceEvent: Arguments cannot be null");
		}

		long epoch = System.currentTimeMillis() / 1000;
		String apiTS = "" + epoch;
		String token = getToken();
		EventInfo event = new EventInfo(eventType.getMessageType(), "{}");
		if (eventType == CallStatsConferenceEvents.CONFERENCE_SETUP) {

			CallStatsEventMessage eventMessage = new CallStatsEventMessage(CallStatsConst.CS_VERSION, appId, CallStatsConst.END_POINT_TYPE,
					conferenceInfo.getConfID(), apiTS, token, bridgeId, conferenceInfo.getInitiatorID(), event);
			sendCallStatsConferenceEventMessage(eventMessage, listener);
		} else {
			listener.onError(CallStatsErrors.CS_PROTO_ERROR, "Invaid Message type");
		}
	}

	public synchronized void sendCallStatsConferenceEvent(CallStatsConferenceEvents eventType, UserInfo userInfo) {
		if (eventType == null || userInfo == null) {
			logger.error("sendCallStatsConferenceEvent: Arguments cannot be null ");
			throw new IllegalArgumentException("sendCallStatsConferenceEvent: Arguments cannot be null");
		}		
		
		long epoch = System.currentTimeMillis() / 1000;
		String apiTS = "" + epoch;
		String token = getToken();
		EventInfo event = new EventInfo(eventType.getMessageType(), "{}");

		CallStatsEventMessage eventMessage = new CallStatsEventMessage(CallStatsConst.CS_VERSION, appId, CallStatsConst.END_POINT_TYPE,
				userInfo.getConfID(), apiTS, token, bridgeId, userInfo.getUserID(), userInfo.getUcID(), event);

		sendCallStatsConferenceEventMessage(eventMessage, null);
	}

	private synchronized void sendCallStatsConferenceEventMessage(CallStatsEventMessage eventMessage,final CallStatsStartConferenceListener listener ) {
		String requestMessageString = gson.toJson(eventMessage);
		httpClient.sendAsyncHttpRequest(CallStatsConst.conferenceEventUrl, CallStatsConst.httpPostMethod, requestMessageString, new CallStatsHttpResponseListener() {
			public void onResponse(HttpResponse response) {
				int responseStatus = response.getStatusLine().getStatusCode();
				if (responseStatus == CallStatsResponseStatus.RESPONSE_STATUS_SUCCESS) {
					CallStatsEventResponseMessage eventResponseMessage;
					try {
						String responseString = EntityUtils.toString(response.getEntity());
						eventResponseMessage = gson.fromJson(responseString, CallStatsEventResponseMessage.class);
					} catch (ParseException e) {
						logger.error("ParseException " + e.getMessage(), e);
						throw new RuntimeException(e);
					} catch (IOException e) {
						logger.error("IO Exception " + e.getMessage(), e);
						throw new RuntimeException(e);
					} catch (JsonSyntaxException e) {
						logger.error("Json Syntax Exception " + e.getMessage(), e);
						e.printStackTrace();
						throw new RuntimeException(e);
					}
					logger.debug("conference event Response status is " + eventResponseMessage.getStatus() + ":"
							+ eventResponseMessage.getConferenceID() + ":" + eventResponseMessage.getUcID());
					if(listener != null) {
						listener.onResponse(eventResponseMessage.getUcID());
					}					
					httpClient.setDisrupted(false);
				} else {
					httpClient.setDisrupted(true);
				}
			}

			public void onFailure(Exception e) {
				logger.error("Response exception" + e.getMessage(), e);
				httpClient.setDisrupted(true);
			}

		});
	}
	
	
	public synchronized void startStatsReportingForUser(String userID, String confID) {
		if (userID == null || confID == null) {
			logger.error("startStatsReportingForUser: Arguments cannot be null ");
			throw new IllegalArgumentException("startStatsReportingForUser: Arguments cannot be null");
		}
		
		String key = userID+":"+confID;
		
		List<ConferenceStats> tempStats = conferenceStatsMap.get(userID);
		if (tempStats == null) {
			tempStats = new ArrayList<ConferenceStats>();
			conferenceStatsMap.put(key, tempStats);
		}
	}

	public synchronized void stopStatsReportingForUser(String userID, String confID) {
		if (userID == null || confID == null) {
			logger.error("stopStatsReportingForUser: Arguments cannot be null ");
			throw new IllegalArgumentException("stopStatsReportingForUser: Arguments cannot be null");
		}
		String key = userID+":"+confID;
		List<ConferenceStats> tempStats = conferenceStatsMap.get(key);
		
		if (tempStats != null && tempStats.size() > 0) {
			StreamStats streamStats;
			StreamStatsData streamStatsData;
			ConferenceStats conferenceStats = tempStats.get(0);
			ConferenceStatsData conferenceStatsData = new ConferenceStatsData(conferenceStats.getLocalUserID(),conferenceStats.getRemoteUserID());
			UserInfo info = new UserInfo(conferenceStats.getConfID(), conferenceStats.getLocalUserID(), conferenceStats.getUcID());
			for (ConferenceStats stats : tempStats) {
				streamStatsData = new StreamStatsData(stats.getRtt(), stats.getPacketsSent(), stats.getBytesSent(), stats.getJitter());
				streamStats = new StreamStats(stats.getFromUserID(), stats.getStatsType(), streamStatsData);
				conferenceStatsData.addStreamStats(stats.getSsrc(), streamStats);
			}

			String statsString = gson.toJson(conferenceStatsData);
			logger.debug("Stats string is " + statsString);
			sendCallStatsConferenceStats(statsString, info);
		}
	}

	public synchronized void reportConferenceStats(String userID, ConferenceStats stats) {
		if (stats == null || userID == null) {
			logger.error("sendConferenceStats: Arguments cannot be null ");
			throw new IllegalArgumentException("sendConferenceStats: Arguments cannot be null");
		}
		String key = userID+":"+stats.getConfID();
		List<ConferenceStats> tempStats = conferenceStatsMap.get(key);
		if (tempStats == null) {
			//tempStats = new ArrayList<ConferenceStats>();
			throw new IllegalStateException("reportConferenceStats called without calling startStatsReportingForUser");
		} else {
			tempStats.add(stats);
			conferenceStatsMap.put(key, tempStats);
		}	
	}
	
	
	private synchronized void sendCallStatsConferenceStats(String stats, UserInfo userInfo) {
		if (stats == null || userInfo == null) {
			logger.error("sendCallStatsConferenceStats: Arguments cannot be null ");
			throw new IllegalArgumentException("sendCallStatsConferenceStats: Arguments cannot be null");
		}
		
		long epoch = System.currentTimeMillis() / 1000;
		String apiTS = "" + epoch;
		String token = getToken();
		EventInfo event = new EventInfo(CallStatsConferenceEvents.CONFERENCE_STATS.getMessageType(), stats);
		CallStatsEventMessage eventMessage = new CallStatsEventMessage(CallStatsConst.CS_VERSION, appId, CallStatsConst.END_POINT_TYPE,
				userInfo.getConfID(), apiTS, token, bridgeId, userInfo.getUserID(), userInfo.getUcID(), event);
		String requestMessageString = gson.toJson(eventMessage);
		httpClient.sendAsyncHttpRequest(CallStatsConst.conferenceEventUrl, CallStatsConst.httpPostMethod, requestMessageString, new CallStatsHttpResponseListener() {
			public void onResponse(HttpResponse response) {
				int responseStatus = response.getStatusLine().getStatusCode();
				if (responseStatus == CallStatsResponseStatus.RESPONSE_STATUS_SUCCESS) {
					CallStatsEventResponseMessage eventResponseMessage;
					try {
						String responseString = EntityUtils.toString(response.getEntity());
						eventResponseMessage = gson.fromJson(responseString, CallStatsEventResponseMessage.class);
					} catch (ParseException e) {
						logger.error("ParseException " + e.getMessage(), e);
						throw new RuntimeException(e);
					} catch (IOException e) {
						logger.error("IO Execption " + e.getMessage(), e);
						throw new RuntimeException(e);
					} catch (JsonSyntaxException e) {
						logger.error("Json Syntax Exception " + e.getMessage(), e);
						e.printStackTrace();
						throw new RuntimeException(e);
					}
					logger.debug("conference event Response status is " + eventResponseMessage.getStatus() + ":"
							+ eventResponseMessage.getConferenceID() + ":" + eventResponseMessage.getUcID());
					httpClient.setDisrupted(false);
				} else {
					httpClient.setDisrupted(true);
				}
			}
	
			public void onFailure(Exception e) {
				logger.error("Response exception" + e.getMessage(), e);
				httpClient.setDisrupted(true);
			}
	
		});

	}
	
}
