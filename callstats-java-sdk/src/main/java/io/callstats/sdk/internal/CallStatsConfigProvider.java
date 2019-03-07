package io.callstats.sdk.internal;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CallStatsConfigProvider {

  /** The Constant logger. */
  private static final Logger logger = LogManager.getLogger("CallStatsConfigProvider");


  /** The base url. */
  public static String eventsBaseUrl;
  public static String statsBaseUrl;
  public static String authBaseUrl;
  public static int keepAliveInterval = CallStatsConst.KEEPALIVE_INTERVAL;


  /** The connection time out. */
  public static int connectionTimeOut = CallStatsConst.CONNECTION_TIMEOUT;

  public static void init() {
    Properties prop = new Properties();
    InputStream input = null;

    try {
      input = new FileInputStream(CallStatsConst.CallStatsJavaSDKPropertyFileName);
      if (input != null) {
        prop.load(input);
        eventsBaseUrl = prop.getProperty("CallStats.EventsBaseURL");
        statsBaseUrl = prop.getProperty("CallStats.StatsBaseURL");
        authBaseUrl = prop.getProperty("CallStats.AuthBaseURL");
        keepAliveInterval = Integer.parseInt(prop.getProperty("CallStats.keepAliveInterval"));

        if (prop.getProperty("CallStats.ConnectionTimeOut") != null) {
          connectionTimeOut = Integer.parseInt(prop.getProperty("CallStats.ConnectionTimeOut"));
        }

        if (authBaseUrl == null) {
          authBaseUrl = CallStatsUrls.AUTH_BASE.getDefaultUrl();
        }
      }
    } catch (FileNotFoundException e) {
      logger.error("Configuration file not found", e);
      throw new RuntimeException("Configuration file not found");
    } catch (IOException e) {
      logger.error("Configuration file read IO exception", e);
      throw new RuntimeException("Configuration file read IO exception");
    }
  }

  public static void setURLs(String eventsUrl, String statsUrl) {
    if (eventsUrl == null) {
      eventsBaseUrl = CallStatsUrls.EVENTS_BASE.getDefaultUrl();
    } else {
      eventsBaseUrl = eventsUrl;
    }

    if (statsUrl == null) {
      statsBaseUrl = CallStatsUrls.STATS_BASE.getDefaultUrl();
    } else {
      statsBaseUrl = statsUrl;
    }
    logger.info("URLs " + authBaseUrl + " " + eventsBaseUrl + " " + statsBaseUrl);
  }
}
