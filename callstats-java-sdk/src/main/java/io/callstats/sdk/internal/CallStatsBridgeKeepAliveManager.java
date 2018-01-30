package io.callstats.sdk.internal;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import io.callstats.sdk.CallStatsErrors;
import io.callstats.sdk.httpclient.CallStatsHttp2Client;
import io.callstats.sdk.internal.listeners.CallStatsHttp2ResponseListener;
import io.callstats.sdk.messages.BridgeKeepAliveMessage;
import io.callstats.sdk.messages.BridgeKeepAliveResponse;
import okhttp3.Response;

/**
 * The Class CallStatsBridgeKeepAliveManager.
 */
public class CallStatsBridgeKeepAliveManager {

	/** The Constant keepAliveEventUrl. */
	private String keepAliveEventUrl = "/callStatsBridgeKeepAliveEvent";

	/** The app id. */
	private int appId;

	/** The bridge id. */
	private String bridgeId;

	/** The token. */
	private String token;

	/** The Constant scheduler. */
	private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

	/** The gson. */
	private Gson gson;

	/** The http client. */
	private CallStatsHttp2Client httpClient;

	private CallStatsBridgeKeepAliveStatusListener keepAliveStatusListener;

	Future<?> future;

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger("CallStatsBridgeKeepAliveManager");

	private int keepAliveInterval = CallStatsConst.KEEPALIVE_INTERVAL;

	/**
	 * Instantiates a new call stats bridge keep alive manager.
	 *
	 * @param appId
	 *            the app id
	 * @param bridgeId
	 *            the bridge id
	 * @param token
	 *            the token
	 * @param httpClient
	 *            the http client
	 * @param keepAliveStatusListener
	 *            listener
	 * 
	 */
	public CallStatsBridgeKeepAliveManager(int appId, String bridgeId, String token, final CallStatsHttp2Client httpClient,
			CallStatsBridgeKeepAliveStatusListener keepAliveStatusListener) {
		super();
		keepAliveEventUrl = "/" + appId + "/stats/bridge/alive";
		Properties prop = new Properties();
		InputStream input = null;

		// input = getClass().getClassLoader().getResourceAsStream(CallStatsConst.CallStatsJavaSDKPropertyFileName);
		try {
			input = new FileInputStream(CallStatsConst.CallStatsJavaSDKPropertyFileName);
			if (input != null) {
				prop.load(input);
				if (prop.getProperty("CallStats.keepAliveInterval") != null) {
					keepAliveInterval = Integer.parseInt(prop.getProperty("CallStats.keepAliveInterval"));
				}
			}
		} catch (FileNotFoundException e) {
			logger.error("Configuration file not found", e);
			throw new RuntimeException("Configuration file not found");
		} catch (IOException e) {
			logger.error("Configuration file read IO exception", e);
			throw new RuntimeException("Configuration file read IO exception");
		}

		this.appId = appId;
		this.bridgeId = bridgeId;
		this.token = token;
		this.httpClient = httpClient;
		this.keepAliveStatusListener = keepAliveStatusListener;
		gson = new Gson();

	}

	/**
	 * Stop keep alive sender.
	 */
	public void stopKeepAliveSender() {
		if (future != null) {
			logger.info("Stoping keepAlive Sender");
			future.cancel(true);
		}
	}

	/**
	 * Shuts keep alive sender.
	 */
	public void shutDownKeepAliveSender() {
		logger.info("Shutting down keepAlive Sender");
		scheduler.shutdownNow();
	}

	/**
	 * Start keep alive sender.
	 * 
	 * @param authToken
	 *            authentication token
	 */
	public void startKeepAliveSender(String authToken) {
		this.token = authToken;
		stopKeepAliveSender();

		logger.info("Starting keepAlive Sender");
		future = scheduler.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				sendKeepAliveBridgeMessage(appId, bridgeId, token, httpClient);
			}
		}, 0, keepAliveInterval, TimeUnit.MILLISECONDS);
	}

	/**
	 * Send keep alive bridge message.
	 *
	 * @param appId
	 *            the app id
	 * @param bridgeId
	 *            the bridge id
	 * @param token
	 *            the token
	 * @param httpClient
	 *            the http client
	 * 
	 */
	private void sendKeepAliveBridgeMessage(int appId, String bridgeId, String token, final CallStatsHttp2Client httpClient) {
		long apiTS = System.currentTimeMillis();
		BridgeKeepAliveMessage message = new BridgeKeepAliveMessage(bridgeId, apiTS);
		String requestMessageString = gson.toJson(message);
		httpClient.sendBridgeAlive(keepAliveEventUrl, token, requestMessageString, new CallStatsHttp2ResponseListener() {
			@Override
			public void onResponse(Response response) {
				int responseStatus = response.code();
				BridgeKeepAliveResponse keepAliveResponse;
				try {
					String responseString = response.body().string();
					keepAliveResponse = gson.fromJson(responseString, BridgeKeepAliveResponse.class);
				} catch (IOException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				} catch (JsonSyntaxException e) {
					logger.error("Json Syntax Exception " + e.getMessage(), e);
					e.printStackTrace();
					throw new RuntimeException(e);
				}
				httpClient.setDisrupted(false);
				if (responseStatus == CallStatsResponseStatus.RESPONSE_STATUS_SUCCESS) {
					keepAliveStatusListener.onSuccess();

				} else if (responseStatus == CallStatsResponseStatus.INVALID_AUTHENTICATION_TOKEN) {
					stopKeepAliveSender();
					keepAliveStatusListener.onKeepAliveError(CallStatsErrors.AUTH_ERROR, keepAliveResponse.getMsg());
				} else {
					httpClient.setDisrupted(true);
				}
			}

			@Override
			public void onFailure(Exception e) {
				logger.info("Response exception" + e.toString());
				httpClient.setDisrupted(true);
			}

		});
	}
}