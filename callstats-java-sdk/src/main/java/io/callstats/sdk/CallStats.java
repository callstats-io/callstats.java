package io.callstats.sdk;

import java.io.IOException;

import io.callstats.sdk.messages.BridgeStatusUpdateMessage;
import io.callstats.sdk.messages.BridgeStatusUpdateResponse;

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
							logger.error("IO Execption " + e.getMessage(), e);
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
	
}
