package io.callstats.sdk.listeners;

import io.callstats.sdk.CallStatsErrors;

public interface CallStatsStartConferenceListener {

  public void onResponse(String ucid);

  void onError(CallStatsErrors error, String errMsg);

}
