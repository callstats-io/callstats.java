package io.callstats.sdk.messages;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Class BridgeKeepAliveMessage.
 */
public class BridgeKeepAliveMessage {
  /** The bridge id. */
  private String localID;

  /** The timestamp. */
  private long timestamp;

  /** The origin id. */
  private String originID;

  /** The device id. */
  private String deviceID;

  /** The logger. */
  private static final Logger logger = Logger.getLogger("CallStats");

  /**
   * Instantiates a new bridge keep alive message.
   *
   * @param bridgeID the bridge id
   * @param timestamp the api ts
   * 
   */
  public BridgeKeepAliveMessage(String bridgeID, long timestamp) {
    this.timestamp = timestamp;
    try {
      this.localID = URLEncoder.encode(bridgeID, "UTF-8");
      this.deviceID = this.localID;
      this.originID = this.localID;
    } catch (UnsupportedEncodingException e) {
      logger.log(Level.SEVERE, "UnsupportedEncodingException " + e.getMessage(), e);
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  public String getLocalID() {
    return localID;
  }

  public void setLocalID(String localID) {
    try {
      this.localID = URLEncoder.encode(localID, "UTF-8");
      this.deviceID = this.localID;
      this.originID = this.localID;
    } catch (UnsupportedEncodingException e) {
      logger.log(Level.SEVERE, "UnsupportedEncodingException " + e.getMessage(), e);
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public String getOriginID() {
    return originID;
  }

  public void setOriginID(String originID) {
    this.originID = originID;
  }

  public String getDeviceID() {
    return deviceID;
  }

  public void setDeviceID(String deviceID) {
    this.deviceID = deviceID;
  }
}
