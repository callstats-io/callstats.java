package io.callstats.sdk.httpclient;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.callstats.sdk.internal.CallStatsConfigProvider;
import io.callstats.sdk.internal.NameValuePair;
import io.callstats.sdk.internal.listeners.CallStatsHttp2ResponseListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.ConnectionPool;
import okhttp3.ConnectionSpec;
import okhttp3.FormBody;
import okhttp3.FormBody.Builder;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.TlsVersion;

public class CallStatsHttp2Client {

  /** The Constant logger. */
  private static final Logger logger = LogManager.getLogger("CallStatsAsyncHttpClient");

  private OkHttpClient okHttpClientclient;
  private boolean isDisrupted;

  public boolean isDisrupted() {
    return isDisrupted;
  }

  public void setDisrupted(boolean isDisrupted) {
    this.isDisrupted = isDisrupted;
  }

  public CallStatsHttp2Client(int connectionTimeOut) {
    ConnectionSpec spec =
        new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS).tlsVersions(TlsVersion.TLS_1_2)
            .allEnabledCipherSuites().supportsTlsExtensions(true).build();

    okHttpClientclient = new OkHttpClient.Builder()
        .readTimeout(connectionTimeOut, TimeUnit.MILLISECONDS)
        .writeTimeout(connectionTimeOut, TimeUnit.MILLISECONDS).connectionPool(new ConnectionPool())
        .connectionSpecs(Collections.singletonList(spec)).build();
    loadConfigurations();
  }

  private void loadConfigurations() {

  }

  private Request buildRequest(String url, String token, String body) {
    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    RequestBody msg = RequestBody.create(JSON, body);
    Request request = new Request.Builder().url(url).addHeader("Accept", "application/json")
        .addHeader("Accept-Charset", "utf-8").addHeader("Authorization", "Bearer " + token)
        .post(msg).build();
    return request;
  }

  public void sendBridgeEvents(String url, String token, String body,
      final CallStatsHttp2ResponseListener listener) {
    if (CallStatsConfigProvider.eventsBaseUrl == null) {
      logger.error("sendBridgeEvents: URL Not Available");
      return;
    }
    logger.info("sending bridge events " + body);
    Request request = buildRequest(CallStatsConfigProvider.eventsBaseUrl + url, token, body);
    send(request, listener);
  }

  public void sendBridgeStats(String url, String token, String body,
      final CallStatsHttp2ResponseListener listener) {
    if (CallStatsConfigProvider.statsBaseUrl == null) {
      logger.error("sendBridgeStats: URL Not Available");
      return;
    }
    logger.info("sending stats " + CallStatsConfigProvider.statsBaseUrl + url);
    Request request = buildRequest(CallStatsConfigProvider.statsBaseUrl + url, token, body);
    send(request, listener);
  }

  public void sendBridgeAlive(String url, String token, String body,
      final CallStatsHttp2ResponseListener listener) {
    if (CallStatsConfigProvider.statsBaseUrl == null) {
      logger.error("sendBridgeAlive: URL Not Available");
      return;
    }
    Request request = buildRequest(CallStatsConfigProvider.statsBaseUrl + url, token, body);
    send(request, listener);
  }

  public void sendBridgeStatistics(String url, String token, String body,
      final CallStatsHttp2ResponseListener listener) {
    if (CallStatsConfigProvider.statsBaseUrl == null) {
      logger.error("sendBridgeStatistics: URL Not Available");
      return;
    }
    Request request = buildRequest(CallStatsConfigProvider.statsBaseUrl + url, token, body);
    send(request, listener);
  }

  public void sendAuthRequest(String url, List<NameValuePair> paramList,
      final CallStatsHttp2ResponseListener listener) {
    Builder builder = new FormBody.Builder();
    for (int i = 0; i < paramList.size(); i++) {
      NameValuePair nameValuePair = paramList.get(i);
      builder.addEncoded(nameValuePair.getName(), nameValuePair.getValue());
    }

    RequestBody msg = builder.build();
    Request request = new Request.Builder().url(CallStatsConfigProvider.authBaseUrl + url)
        .addHeader("Content-type", "application/x-www-form-urlencoded")
        .addHeader("Accept", "application/json").post(msg).build();

    send(request, listener);
  }
  
  public void sendConferenceAlive(String url, String token, String messageBody,
	      final CallStatsHttp2ResponseListener listener) {
	if (CallStatsConfigProvider.statsBaseUrl == null) {
	  logger.error("sendConferenceAlive: URL Not Available");
	  return;
	}
	Request request = buildRequest(CallStatsConfigProvider.eventsBaseUrl + url, token, messageBody);
	send(request, listener);
  }

  private void send(Request request, final CallStatsHttp2ResponseListener listener) {
    Call call = okHttpClientclient.newCall(request);
    call.enqueue(new Callback() {
      public void onFailure(Call arg0, IOException e) {
        isDisrupted = true;
        listener.onFailure(e);
      }

      public void onResponse(Call arg0, Response response) throws IOException {
        listener.onResponse(response);
      }
    });
  }
}
