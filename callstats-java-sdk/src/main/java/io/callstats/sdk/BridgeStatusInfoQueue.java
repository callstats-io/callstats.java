package io.callstats.sdk;

import java.util.ArrayList;
import java.util.List;



public class BridgeStatusInfoQueue {
	
	private List<BridgeStatusInfo> bridgeStatusInfoList;
	
	public BridgeStatusInfoQueue() {
		bridgeStatusInfoList = new ArrayList<BridgeStatusInfo>();		
	}

	public int getLength() {
		return bridgeStatusInfoList.size();
	}
	
	public void push(BridgeStatusInfo bridgeStatusInfo) {
		bridgeStatusInfoList.add(bridgeStatusInfo);
	}
	
	public void clear() {
		bridgeStatusInfoList.clear();
	}
	
	public BridgeStatusInfo pop() {
		BridgeStatusInfo bridgeStatusInfo = null;
		if (bridgeStatusInfoList.size() >0) {			
			bridgeStatusInfo = bridgeStatusInfoList.get(0);
			bridgeStatusInfoList.remove(0);
		}
		return bridgeStatusInfo;
	}
}
