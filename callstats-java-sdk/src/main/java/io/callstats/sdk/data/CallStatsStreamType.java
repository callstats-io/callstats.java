package io.callstats.sdk.data;

public enum CallStatsStreamType {

  INBOUND(0, "inbound"), OUTBOUND(1, "outbound");

  private final int typeCode;
  private final String type;

  private CallStatsStreamType(int code, String msgType) {
    this.typeCode = code;
    this.type = msgType;
  }

  public String getMessageType() {
    return type;
  }

  public int getMessageCode() {
    return typeCode;
  }

  @Override
  public String toString() {
    return type + ": " + type;
  }
}
