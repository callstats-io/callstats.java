package io.callstats.sdk.internal;

public class CallStatsResponseStatus {

  /** The Constant RESPONSE_STATUS_SUCCESS. */
  public static final int RESPONSE_STATUS_SUCCESS = 200;
  public static final int INVALID_REQUEST = 400;
  public static final int INVALID_AUTHENTICATION_TOKEN = 401;
  public static final int INVALID_REQUEST_BODY = 422;
  public static final int SERVER_ERROR = 500;
  public static final int SERVICE_UNAVAILABLE = 503;
  public static final int GATEWAY_ERROR = 502;

}
