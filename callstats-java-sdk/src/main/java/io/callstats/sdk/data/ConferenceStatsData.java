package io.callstats.sdk.data;

import java.util.HashMap;
import java.util.Map;

public class ConferenceStatsData {
	
	String localUserID;
	Map<String, StreamStats> stats;
	
	public ConferenceStatsData(String localUserID) {
		this.localUserID = localUserID;
		stats = new HashMap<String, StreamStats>();
	}
	
	public void addStreamStats(String ssrc, StreamStats streamStats) {
		stats.put(ssrc, streamStats);
	}
	
	public void removeStreamStats(String ssrc) {
		stats.remove(ssrc);
	}
}
