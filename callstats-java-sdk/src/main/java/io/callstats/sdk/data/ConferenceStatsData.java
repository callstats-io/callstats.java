package io.callstats.sdk.data;

import java.util.HashMap;
import java.util.Map;

public class ConferenceStatsData {
	
	String localID;
	String remoteID;
	
	Map<String, StreamStats> stats;
	
	public ConferenceStatsData(String localUserID,String remoteUserid) {
		this.localID = localUserID;
		this.remoteID = remoteUserid;
		stats = new HashMap<String, StreamStats>();
	}
	
	public void addStreamStats(String ssrc, StreamStats streamStats) {
		stats.put(ssrc, streamStats);
	}
	
	public void removeStreamStats(String ssrc) {
		stats.remove(ssrc);
	}
}
