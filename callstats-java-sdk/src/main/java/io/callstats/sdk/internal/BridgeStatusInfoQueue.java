package io.callstats.sdk.internal;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.callstats.sdk.data.BridgeStatusInfo;

public class BridgeStatusInfoQueue {

  private List<BridgeStatusInfo> bridgeStatusInfoList;
  private int MAX_QUEUE_LENGTH = 5000;
  /** The logger. */
  private static final Logger logger = LogManager.getLogger("CallStats");

  public BridgeStatusInfoQueue() {
    bridgeStatusInfoList = new ArrayList<BridgeStatusInfo>();
  }

  public int getLength() {
    return bridgeStatusInfoList.size();
  }

  public void push(BridgeStatusInfo bridgeStatusInfo) {
    if (getLength() > MAX_QUEUE_LENGTH) {
      logger.warn("Queue is full, removing old element");
      pop();
    }

    bridgeStatusInfoList.add(bridgeStatusInfo);
  }

  public void clear() {
    bridgeStatusInfoList.clear();
  }

  public BridgeStatusInfo pop() {
    BridgeStatusInfo bridgeStatusInfo = null;
    if (bridgeStatusInfoList.size() > 0) {
      bridgeStatusInfo = bridgeStatusInfoList.get(0);
      bridgeStatusInfoList.remove(0);
    }
    return bridgeStatusInfo;
  }
}
