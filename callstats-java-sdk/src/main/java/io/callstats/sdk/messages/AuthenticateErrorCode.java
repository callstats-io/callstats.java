package io.callstats.sdk.messages;

public enum AuthenticateErrorCode {
  INTERNAL_ERROR,

  INVALID_APPID,

  INVALID_ENDPOINT,

  USERID_MISMATCH,

  ENDPOINT_MISMATCH,

  ORIGIN_MISMATCH,

  INVALID_TOKEN,

  PROTOCOL_ERROR
}
