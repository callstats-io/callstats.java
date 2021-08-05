package io.callstats.sdk.internal;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
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
 * The Class CallStatsConferenceAliveManager.
 */
public class CallStatsConferenceAliveManager {

  /** The Constant keepAliveEventUrl. */
  private String conferenceAliveEventUrl = "/v1/apps";

  /** The app id. */
  private int appId;
  
  /** The bridge id. */
  private String bridgeID;

  /** The token. */
  private String token;

  private Map<String, ScheduledExecutorService> schedulerMap =
	      new HashMap<String, ScheduledExecutorService>();
  
  
  private Map<String, Future<?>> futureMap =
	      new HashMap<String, Future<?>>();
  

  /** The gson. */
  private Gson gson;

  /** The http client. */
  private CallStatsHttp2Client httpClient;

  private CallStatsBridgeKeepAliveStatusListener keepAliveStatusListener;


  /** The Constant logger. */
  private static final Logger logger = LogManager.getLogger("CallStatsBridgeKeepAliveManager");

  private int keepAliveInterval;

  /**
   * Instantiates a new call stats bridge keep alive manager.
   *
   * @param appId the app id
   * @param ucID the bridge id
   * @param token the token
   * @param httpClient the http client
   * @param keepAliveStatusListener listener
   * 
   */
  public CallStatsConferenceAliveManager(int appId, String bridgeID, String token,
      final CallStatsHttp2Client httpClient,
      CallStatsBridgeKeepAliveStatusListener keepAliveStatusListener) {
    super();
    this.keepAliveInterval = 15000;
    this.appId = appId;
    this.token = token;
    this.httpClient = httpClient;
    this.keepAliveStatusListener = keepAliveStatusListener;
    this.bridgeID = bridgeID;
    gson = new Gson();

  }

  /**
   * Stop conference alive sender.
   * @param ucID conference unique identifier
   */
  public void stopConferenceAliveSender(String ucID) {
	Future<?> future = futureMap.get(ucID);
    if (future != null) {
      logger.info("Stoping conferenceAlive Sender for "+ucID);
      future.cancel(true);
      futureMap.remove(ucID);
    }
    
    ScheduledExecutorService scheduler = schedulerMap.get(ucID);
    if (scheduler != null) {
      logger.info("Shutting down conferenceAlive Sender for "+ucID);
      scheduler.shutdownNow();
      schedulerMap.remove(ucID);
    }
  }


  /**
   * Start conference alive sender.
   * 
   * @param confID conference identifier
   * @param ucID conference unique identifier
   * @param authToken authentication token
   */
  public void startConferenceAliveSender(String confID, String ucID, String authToken) {
    this.token = authToken;
    stopConferenceAliveSender(ucID);

    logger.info("Starting conference Alive Sender for "+ucID);
    final ScheduledExecutorService scheduler =
    	      Executors.newSingleThreadScheduledExecutor();
    Future<?> future = scheduler.scheduleAtFixedRate(new Runnable() {
      public void run() {
        sendKeepAliveBridgeMessage(appId, confID, ucID, token, httpClient);
      }
    }, 0, keepAliveInterval, TimeUnit.MILLISECONDS);
    schedulerMap.put(ucID, scheduler);
    futureMap.put(ucID, future);
  }

  /**
   * Send conference alive message.
   *
   * @param appId the app id
   * @param confID conference identifier
   * @param ucID conference unique identifier
   * @param token the token
   * @param httpClient the http client
   * 
   */
  private void sendKeepAliveBridgeMessage(int appId, String confID, String ucID, String token,
      final CallStatsHttp2Client httpClient) {
    try {
      conferenceAliveEventUrl = "/" + appId + "/conferences/" + URLEncoder.encode(confID, "utf-8") + "/" + ucID + "/events/user/alive";
    } catch (UnsupportedEncodingException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    long apiTS = System.currentTimeMillis();
    BridgeKeepAliveMessage message = new BridgeKeepAliveMessage(this.bridgeID, apiTS);
    String requestMessageString = gson.toJson(message);
    httpClient.sendConferenceAlive(conferenceAliveEventUrl, token, requestMessageString, new CallStatsHttp2ResponseListener() {
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
