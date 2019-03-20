package io.callstats.sdk;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import io.callstats.sdk.data.BridgeStatusInfo;
import io.callstats.sdk.data.ConferenceInfo;
import io.callstats.sdk.data.ConferenceStats;
import io.callstats.sdk.data.ServerInfo;
import io.callstats.sdk.data.UserInfo;
import io.callstats.sdk.httpclient.CallStatsHttp2Client;
import io.callstats.sdk.internal.BridgeStatusInfoQueue;
import io.callstats.sdk.internal.CallStatsAuthenticator;
import io.callstats.sdk.internal.CallStatsBridgeKeepAliveManager;
import io.callstats.sdk.internal.CallStatsBridgeKeepAliveStatusListener;
import io.callstats.sdk.internal.CallStatsConfigProvider;
import io.callstats.sdk.internal.CallStatsConst;
import io.callstats.sdk.internal.CallStatsResponseStatus;
import io.callstats.sdk.internal.TokenGeneratorHs256;
import io.callstats.sdk.internal.listeners.CallStatsHttp2ResponseListener;
import io.callstats.sdk.listeners.CallStatsInitListener;
import io.callstats.sdk.listeners.CallStatsStartConferenceListener;
import io.callstats.sdk.messages.BridgeStatusUpdateMessage;
import io.callstats.sdk.messages.BridgeStatusUpdateResponse;
import io.callstats.sdk.messages.CallStatsEventResponse;
import io.callstats.sdk.messages.ConferenceSetupEvent;
import io.callstats.sdk.messages.ConferenceStatsEvent;
import okhttp3.Response;

/**
 * The Class CallStats.
 *
 * @author Karthik Budigere
 */
public class CallStats {

  /** The http client. */
  private CallStatsHttp2Client httpClient;

  /** The app id. */
  private int appId;

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

  private Map<String, List<ConferenceStats>> conferenceStatsMap =
      new HashMap<String, List<ConferenceStats>>();

  /** The bridge keep alive manager. */
  private CallStatsBridgeKeepAliveManager bridgeKeepAliveManager;

  private ICallStatsTokenGenerator tokenGenerator;

  private CallStatsHttp2Client authHttpClient;

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
   * Instantiates a new callstats.
   */
  public CallStats() {
    gson = new Gson();
    bridgeStatusInfoQueue = new BridgeStatusInfoQueue();
    if (System.getProperty("callstats.configurationFile") != null) {
      CallStatsConst.CallStatsJavaSDKPropertyFileName =
          System.getProperty("callstats.configurationFile");
    }

    logger.info("config file path is " + System.getProperty("callstats.configurationFile") + ":"
        + CallStatsConst.CallStatsJavaSDKPropertyFileName);
    CallStatsConst.CS_VERSION = getClass().getPackage().getImplementationVersion();
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

  public void initialize(final int appId, final String appSecret, final String bridgeId,
      final ServerInfo serverInfo, final CallStatsInitListener callStatsInitListener) {
    if (StringUtils.isBlank(appSecret)) {
      logger.error("intialize: Arguments cannot be null ");
      throw new IllegalArgumentException("intialize: Arguments cannot be null");
    }
    initialize(appId, new TokenGeneratorHs256(appSecret.toCharArray(), appId, bridgeId), bridgeId,
        serverInfo, callStatsInitListener);
  }

  /**
   * Initialize callstats.
   *
   * @param appId the app id
   * @param tokenGenerator token generator
   * @param bridgeId the bridge id
   * @param serverInfo the server info
   * @param callStatsInitListener the call stats init listener
   */

  public void initialize(final int appId, ICallStatsTokenGenerator tokenGenerator,
      final String bridgeId, final ServerInfo serverInfo,
      final CallStatsInitListener callStatsInitListener) {
    if (appId <= 0 || StringUtils.isBlank(bridgeId) || serverInfo == null
        || callStatsInitListener == null) {
      logger.error("intialize: Arguments cannot be null ");
      throw new IllegalArgumentException("intialize: Arguments cannot be null");
    }

    this.appId = appId;
    this.tokenGenerator = tokenGenerator;
    this.bridgeId = bridgeId;
    this.listener = callStatsInitListener;
    this.serverInfo = serverInfo;
    CallStatsConfigProvider.init();

    httpClient = new CallStatsHttp2Client(CallStatsConfigProvider.connectionTimeOut);
    authHttpClient = new CallStatsHttp2Client(CallStatsConfigProvider.connectionTimeOut);
    authenticator = new CallStatsAuthenticator(appId, this.tokenGenerator, bridgeId, authHttpClient,
        new CallStatsInitListener() {
          public void onInitialized(String msg) {
            setInitialized(true);
            logger.info("SDK Initialized " + msg);
            startKeepAliveThread();
            listener.onInitialized(msg);
          }

          public void onError(CallStatsErrors error, String errMsg) {
            logger.info("SDK Initialization Failed " + errMsg);
            listener.onError(error, errMsg);;
          }
        });
    authenticator.doAuthentication();
  }

  /**
   * Send call stats bridge status update.
   *
   * @param bridgeStatusInfo the bridge status info
   */
  public void sendCallStatsBridgeStatusUpdate(BridgeStatusInfo bridgeStatusInfo) {
    if (!isInitialized()) {
      bridgeStatusInfoQueue.push(bridgeStatusInfo);
      return;
    }

    long epoch = System.currentTimeMillis();
    String token = getToken();
    BridgeStatusUpdateMessage eventMessage =
        new BridgeStatusUpdateMessage(bridgeId, epoch, bridgeStatusInfo);
    String requestMessageString = gson.toJson(eventMessage);
    String url = "/" + appId + "/stats/bridge/status";
    httpClient.sendBridgeStatistics(url, token, requestMessageString,
        new CallStatsHttp2ResponseListener() {
          public void onResponse(Response response) {
            int responseStatus = response.code();
            BridgeStatusUpdateResponse eventResponseMessage;
            try {
              String responseString = response.body().string();
              eventResponseMessage =
                  gson.fromJson(responseString, BridgeStatusUpdateResponse.class);
            } catch (IOException e) {
              logger.error("IO Exception " + e.getMessage(), e);
              throw new RuntimeException(e);
            } catch (JsonSyntaxException e) {
              logger.error("Json Syntax Exception " + e.getMessage(), e);
              e.printStackTrace();
              throw new RuntimeException(e);
            }
            logger.debug("BridgeStatusUpdate Response " + eventResponseMessage.getStatus() + ":"
                + eventResponseMessage.getMsg());
            httpClient.setDisrupted(false);
            if (responseStatus == CallStatsResponseStatus.RESPONSE_STATUS_SUCCESS) {
              sendCallStatsBridgeStatusUpdateFromQueue();
            } else if (responseStatus == CallStatsResponseStatus.INVALID_AUTHENTICATION_TOKEN) {
              bridgeKeepAliveManager.stopKeepAliveSender();
              authenticator.doAuthentication();
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

  private synchronized void sendCallStatsBridgeStatusUpdateFromQueue() {
    if (bridgeStatusInfoQueue.getLength() < 1)
      return;

    while (bridgeStatusInfoQueue.getLength() > 0) {
      BridgeStatusInfo bridgeStatusInfo = bridgeStatusInfoQueue.pop();
      sendCallStatsBridgeStatusUpdate(bridgeStatusInfo);
    }
  }

  public synchronized void sendCallStatsConferenceEvent(CallStatsConferenceEvents eventType,
      ConferenceInfo conferenceInfo, final CallStatsStartConferenceListener listener) {
    if (eventType == null || conferenceInfo == null || listener == null) {
      logger.error("sendCallStatsConferenceEvent: Arguments cannot be null ");
      throw new IllegalArgumentException("sendCallStatsConferenceEvent: Arguments cannot be null");
    }

    long apiTS = System.currentTimeMillis();
    if (eventType == CallStatsConferenceEvents.CONFERENCE_SETUP) {
      ConferenceSetupEvent eventMessage =
          new ConferenceSetupEvent(bridgeId, conferenceInfo.getInitiatorID(), apiTS, serverInfo);
      String requestMessageString = gson.toJson(eventMessage);
      String url = "";
      try {
        url =
            "/" + appId + "/conferences/" + URLEncoder.encode(conferenceInfo.getConfID(), "utf-8");
      } catch (UnsupportedEncodingException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      sendCallStatsConferenceEventMessage(url, requestMessageString, listener);
    } else {
      listener.onError(CallStatsErrors.CS_PROTO_ERROR, "Invaid Message type");
    }
  }

  public synchronized void sendCallStatsConferenceEvent(CallStatsConferenceEvents eventType,
      UserInfo userInfo) {
    if (eventType == null || userInfo == null) {
      logger.error("sendCallStatsConferenceEvent: Arguments cannot be null ");
      throw new IllegalArgumentException("sendCallStatsConferenceEvent: Arguments cannot be null");
    }

    // long apiTS = System.currentTimeMillis();
    // String token = getToken();
    if (eventType == CallStatsConferenceEvents.AUDIO_MUTE
        || eventType == CallStatsConferenceEvents.AUDIO_UNMUTE
        || eventType == CallStatsConferenceEvents.VIDEO_PAUSE
        || eventType == CallStatsConferenceEvents.VIDEO_RESUME) {
      // TODO send media action event
    } else if (eventType == CallStatsConferenceEvents.USER_JOINED
        || eventType == CallStatsConferenceEvents.USER_LEFT) {
      // TODO send user action event
    } else if (eventType == CallStatsConferenceEvents.FABRIC_HOLD
        || eventType == CallStatsConferenceEvents.FABRIC_RESUME) {
      // TODO send fabric action event
    } else if (eventType == CallStatsConferenceEvents.CONFERENCE_SETUP_FAILED
        || eventType == CallStatsConferenceEvents.CONFERENCE_TERMINATED) {
      // TODO send conference action event
    }
    // sendCallStatsConferenceEventMessage(userInfo.getConfID(), requestMessageString, null);
  }

  private synchronized void sendCallStatsConferenceEventMessage(String url, String reqMsg,
      final CallStatsStartConferenceListener listener) {
    String token = getToken();
    if (token == null) {
      logger.error("sendCallStatsConferenceEvent: Not Initialized/Token Unavaialble");
      return;
    }

    httpClient.sendBridgeEvents(url, token, reqMsg, new CallStatsHttp2ResponseListener() {
      public void onResponse(Response response) {
        int responseStatus = response.code();
        String responseString = "";
        try {
          responseString = response.body().string();
        } catch (IOException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
        logger.debug("received response " + responseString);
        if (responseStatus == CallStatsResponseStatus.RESPONSE_STATUS_SUCCESS) {
          CallStatsEventResponse eventResponseMessage;
          try {
            eventResponseMessage = gson.fromJson(responseString, CallStatsEventResponse.class);
          } catch (JsonSyntaxException e) {
            logger.error("Json Syntax Exception " + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException(e);
          }
          logger.debug("conference event Response status is " + eventResponseMessage.getStatus()
              + ":" + eventResponseMessage.getUcID());
          if (listener != null) {
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

    String key = userID + ":" + confID;

    List<ConferenceStats> tempStats = conferenceStatsMap.get(key);
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
    String key = userID + ":" + confID;
    List<ConferenceStats> tempStats = conferenceStatsMap.get(key);
    long apiTS = System.currentTimeMillis();
    if (tempStats != null && tempStats.size() > 0) {
      ConferenceStats conferenceStats = tempStats.get(0);
      ConferenceStatsEvent conferenceStatsEvent =
          new ConferenceStatsEvent(bridgeId, conferenceStats.getRemoteUserID(),
              conferenceStats.getLocalUserID(), conferenceStats.getConfID(), apiTS);
      UserInfo info = new UserInfo(conferenceStats.getConfID(), conferenceStats.getRemoteUserID(),
          conferenceStats.getUcID());

      tempStats.forEach(new Consumer<ConferenceStats>() {
        public void accept(ConferenceStats stats) {
          conferenceStatsEvent.addStats(stats);
        }

      });

      String statsString = gson.toJson(conferenceStatsEvent);
      logger.debug("Stats string -" + statsString);
      sendCallStatsConferenceStats(statsString, info);
      conferenceStatsMap.remove(key);
    }
  }

  public synchronized void reportConferenceStats(String userID, ConferenceStats stats) {
    if (stats == null || userID == null) {
      logger.error("sendConferenceStats: Arguments cannot be null ");
      throw new IllegalArgumentException("sendConferenceStats: Arguments cannot be null");
    }
    String key = userID + ":" + stats.getConfID();
    List<ConferenceStats> tempStats = conferenceStatsMap.get(key);
    if (tempStats == null) {
      // tempStats = new ArrayList<ConferenceStats>();
      throw new IllegalStateException(
          "reportConferenceStats called without calling startStatsReportingForUser");
    } else {
      tempStats.add(stats);
      conferenceStatsMap.put(key, tempStats);
    }
  }

  private synchronized void startKeepAliveThread() {
    if (bridgeKeepAliveManager == null) {
      bridgeKeepAliveManager = new CallStatsBridgeKeepAliveManager(appId, bridgeId,
          authenticator.getToken(), httpClient, new CallStatsBridgeKeepAliveStatusListener() {
            public void onKeepAliveError(CallStatsErrors error, String errMsg) {
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
  }

  private synchronized void sendCallStatsConferenceStats(String stats, UserInfo userInfo) {
    if (stats == null || userInfo == null) {
      logger.error("sendCallStatsConferenceStats: Arguments cannot be null ");
      throw new IllegalArgumentException("sendCallStatsConferenceStats: Arguments cannot be null");
    }

    if (userInfo.getUcID() == null) {
      logger.error("sendCallStatsConferenceStats: UCID is null ");
      throw new IllegalArgumentException("sendCallStatsConferenceStats: UCID is null");
    }

    String token = getToken();
    if (token == null) {
      logger.error("sendCallStatsConferenceStats: Not Initialized/Token Unavaialble");
      return;
    }
    String url = "";
    try {
      url = "/" + appId + "/conferences/" + URLEncoder.encode(userInfo.getConfID(), "utf-8") + "/"
          + userInfo.getUcID() + "/stats";
    } catch (UnsupportedEncodingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    httpClient.sendBridgeStats(url, token, stats, new CallStatsHttp2ResponseListener() {
      public void onResponse(Response response) {
        int responseStatus = response.code();
        String responseString = "";
        try {
          responseString = response.body().string();
        } catch (IOException e1) {
          e1.printStackTrace();
        }
        logger.debug("sendBridgeStats : received response " + responseString);
        if (responseStatus == CallStatsResponseStatus.RESPONSE_STATUS_SUCCESS) {
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
