package io.callstats.sdk.messages;

public class AuthenticateRetryActionParams implements IAuthenticateErrorActionParams {
  private int timeout;

  public int getTimeout() {
    return timeout;
  }

  public void setTimeout(int timeout) {
    this.timeout = timeout;
  }
}
