package io.callstats.sdk;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import io.callstats.sdk.httpclient.CallStatsHttp2Client;
import io.callstats.sdk.internal.CallStatsAuthenticator;
import io.callstats.sdk.internal.CallStatsConst;
import io.callstats.sdk.listeners.CallStatsInitListener;

public class CallStatsAuthenticatorTest {

  /** The listener. */
  CallStatsInitListener listener;
  CallStatsHttp2Client httpClient;
  CallStatsAuthenticator authenticator;

  /** The app id. */
  int appId = CallStatsTest.appId;

  /** The app secret. */
  String appSecret = CallStatsTest.appSecret;

  String bridgeId = CallStatsTest.bridgeId;

  @Before
  public void setUp() {
    listener = Mockito.mock(CallStatsInitListener.class);
    httpClient = new CallStatsHttp2Client(CallStatsConst.CONNECTION_TIMEOUT);
    authenticator = new CallStatsAuthenticator(appId, appSecret, bridgeId, httpClient, listener);
  }

  @Test
  public void doAuthenticationTest() {
    authenticator.doAuthentication();

    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    String msg = "SDK authentication successful";
    Mockito.verify(listener).onInitialized(msg);
  }

  @Test
  public void doAuthenticationTestInvalidAppId() {
    authenticator =
        new CallStatsAuthenticator(appId + 1, appSecret, bridgeId, httpClient, listener);
    authenticator.doAuthentication();
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    String errMsg = "SDK Authentication Error";
    Mockito.verify(listener).onError(CallStatsErrors.HTTP_ERROR, errMsg);
  }
}
