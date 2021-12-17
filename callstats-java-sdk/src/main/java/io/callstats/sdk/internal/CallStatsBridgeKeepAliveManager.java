package io.callstats.sdk.internal;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

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
  private static final ScheduledExecutorService scheduler =
      Executors.newSingleThreadScheduledExecutor();

  /** The gson. */
  private Gson gson;

  /** The http client. */
  private CallStatsHttp2Client httpClient;

  private CallStatsBridgeKeepAliveStatusListener keepAliveStatusListener;

  Future<?> future;

  /** The Constant logger. */
  private static final Logger logger = Logger.getLogger("CallStatsBridgeKeepAliveManager");

  private int keepAliveInterval;

  /**
   * Instantiates a new call stats bridge keep alive manager.
   *
   * @param appId the app id
   * @param bridgeId the bridge id
   * @param token the token
   * @param httpClient the http client
   * @param keepAliveStatusListener listener
   * 
   */
  public CallStatsBridgeKeepAliveManager(int appId, String bridgeId, String token,
      final CallStatsHttp2Client httpClient,
      CallStatsBridgeKeepAliveStatusListener keepAliveStatusListener) {
    super();
    keepAliveEventUrl = "/" + appId + "/stats/bridge/alive";
    this.keepAliveInterval = CallStatsConfigProvider.keepAliveInterval;
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
   * @param authToken authentication token
   */
  public void startKeepAliveSender(String authToken) {
    this.token = authToken;
    stopKeepAliveSender();

    logger.info("Starting keepAlive Sender");
    future = scheduler.scheduleAtFixedRate(new Runnable() {
      public void run() {
        sendKeepAliveBridgeMessage(appId, bridgeId, token, httpClient);
      }
    }, 0, keepAliveInterval, TimeUnit.MILLISECONDS);
  }

  /**
   * Send keep alive bridge message.
   *
   * @param appId the app id
   * @param bridgeId the bridge id
   * @param token the token
   * @param httpClient the http client
   * 
   */
  private void sendKeepAliveBridgeMessage(int appId, String bridgeId, String token,
      final CallStatsHttp2Client httpClient) {
    long apiTS = System.currentTimeMillis();
    BridgeKeepAliveMessage message = new BridgeKeepAliveMessage(bridgeId, apiTS);
    String requestMessageString = gson.toJson(message);
    httpClient.sendBridgeAlive(keepAliveEventUrl, token, requestMessageString,
        new CallStatsHttp2ResponseListener() {
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
              logger.log(Level.SEVERE, "Json Syntax Exception " + e.getMessage(), e);
              e.printStackTrace();
              throw new RuntimeException(e);
            }
            httpClient.setDisrupted(false);
            if (responseStatus == CallStatsResponseStatus.RESPONSE_STATUS_SUCCESS) {
              keepAliveStatusListener.onSuccess();

            } else if (responseStatus == CallStatsResponseStatus.INVALID_AUTHENTICATION_TOKEN) {
              stopKeepAliveSender();
              keepAliveStatusListener.onKeepAliveError(CallStatsErrors.AUTH_ERROR,
                  keepAliveResponse.getMsg());
            } else {
              httpClient.setDisrupted(true);
            }
          }

          public void onFailure(Exception e) {
            logger.info("Response exception " + e.toString());
            httpClient.setDisrupted(true);
          }

        });
  }
}
