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
  public static String EVENTS_BASE_URL;
  public static String STATS_BASE_URL;
  public static String AUTH_BASE_URL;


  /** The connection time out. */
  public static int connectionTimeOut = CallStatsConst.CONNECTION_TIMEOUT;

  public static void init() {
    Properties prop = new Properties();
    InputStream input = null;

    try {
      input = new FileInputStream(CallStatsConst.CallStatsJavaSDKPropertyFileName);
      if (input != null) {
        prop.load(input);
        EVENTS_BASE_URL = prop.getProperty("CallStats.EventsBaseURL");
        STATS_BASE_URL = prop.getProperty("CallStats.StatsBaseURL");
        AUTH_BASE_URL = prop.getProperty("CallStats.AuthBaseURL");

        if (prop.getProperty("CallStats.ConnectionTimeOut") != null) {
          connectionTimeOut = Integer.parseInt(prop.getProperty("CallStats.ConnectionTimeOut"));
        }

        if (AUTH_BASE_URL == null) {
          AUTH_BASE_URL = CallStatsUrls.AUTH_BASE.getDefaultUrl();
        }
      }
      logger.info("URLs " + AUTH_BASE_URL + " " + EVENTS_BASE_URL + " " + STATS_BASE_URL);
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
      EVENTS_BASE_URL = CallStatsUrls.EVENTS_BASE.getDefaultUrl();
    } else {
      EVENTS_BASE_URL = eventsUrl;
    }

    if (statsUrl == null) {
      STATS_BASE_URL = CallStatsUrls.STATS_BASE.getDefaultUrl();
    } else {
      STATS_BASE_URL = statsUrl;
    }
    logger.info("URLs " + AUTH_BASE_URL + " " + EVENTS_BASE_URL + " " + STATS_BASE_URL);
  }
}
