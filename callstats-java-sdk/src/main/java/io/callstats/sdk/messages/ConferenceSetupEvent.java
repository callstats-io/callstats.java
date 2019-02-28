package io.callstats.sdk.messages;

import io.callstats.sdk.data.ServerInfo;

public class ConferenceSetupEvent {

  String localID;
  String originID;
  String deviceID;
  long timestamp;
  ServerInfo endpointInfo;

  public String getLocalID() {
    return localID;
  }

  public void setLocalID(String localID) {
    this.localID = localID;
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

  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public ServerInfo getEndpointInfo() {
    return endpointInfo;
  }

  public void setEndpointInfo(ServerInfo endpointInfo) {
    this.endpointInfo = endpointInfo;
  }

  public ConferenceSetupEvent(String localID, String originID, long timestamp,
      ServerInfo serverInfo) {
    this.localID = localID;
    this.originID = originID;
    this.timestamp = timestamp;
    this.endpointInfo = serverInfo;
    this.deviceID = this.localID;
  }
}
