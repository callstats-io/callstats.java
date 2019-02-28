package io.callstats.sdk.data;

import io.callstats.sdk.internal.CallStatsConst;

/**
 * The Class ServerInfo.
 */
public class ServerInfo {

  /** The name. */
  String buildName;

  String buildVersion;
  /** The os. */
  String os;

  /** The ver. */
  String osVersion;

  String appVersion;

  String callstatsVersion;

  String type = CallStatsConst.END_POINT_TYPE;

  /**
   * Instantiates a new ServerInfo info.
   */
  public ServerInfo() {
    callstatsVersion = CallStatsConst.CS_VERSION;
  }

  /**
   * Gets the name.
   *
   * @return the name
   */
  public String getName() {
    return buildName;
  }

  /**
   * Sets the name.
   *
   * @param name the new name
   */
  public void setName(String name) {
    this.buildName = name;
  }

  /**
   * Gets the os.
   *
   * @return the os
   */
  public String getOs() {
    return os;
  }

  /**
   * Sets the os.
   *
   * @param os the new os
   */
  public void setOs(String os) {
    this.os = os;
  }

  /**
   * Gets the ver.
   *
   * @return the ver
   */
  public String getVer() {
    return osVersion;
  }

  /**
   * Sets the ver.
   *
   * @param ver the new ver
   */
  public void setVer(String ver) {
    this.osVersion = ver;
  }

  public String getEndpointType() {
    return type;
  }

  public void setEndpointType(String endpointType) {
    this.type = endpointType;
  }

}
