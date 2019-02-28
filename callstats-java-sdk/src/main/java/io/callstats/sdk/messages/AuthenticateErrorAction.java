package io.callstats.sdk.messages;

public class AuthenticateErrorAction {
  private AuthenticateErrorActionType action;
  private IAuthenticateErrorActionParams params;

  public AuthenticateErrorActionType getAction() {
    return action;
  }

  public void setAction(AuthenticateErrorActionType action) {
    this.action = action;
  }

  public IAuthenticateErrorActionParams getParams() {
    return params;
  }

  public void setParams(IAuthenticateErrorActionParams params) {
    this.params = params;
  }
}
