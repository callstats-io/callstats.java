package io.callstats.sdk.messages;

/**
 * The Class BridgeKeepAliveResponse.
 */
public class BridgeKeepAliveResponse {

  /** The user id. */
  private String status;

  /** The version. */
  private String msg;

  /**
   * Instantiates a new bridge keep alive response.
   */
  public BridgeKeepAliveResponse() {

  }


  public String getStatus() {
    return status;
  }


  public void setStatus(String status) {
    this.status = status;
  }


  public String getMsg() {
    return msg;
  }


  public void setMsg(String msg) {
    this.msg = msg;
  }

}
