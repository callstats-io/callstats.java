package io.callstats.sdk.data;

import java.util.HashMap;
import java.util.Map;

public class StreamStats {
	String fromUserID;
	String toUserID;
	Map<String, StreamStatsData> type = new HashMap<String,StreamStatsData>();

	
	public String getFromUserID() {
		return fromUserID;
	}
	public void setFromUserID(String fromUserID) {
		this.fromUserID = fromUserID;
	}
	public String getToUserID() {
		return toUserID;
	}
	public void setToUserID(String toUserID) {
		this.toUserID = toUserID;
	}
		
	public StreamStats(String fromUserID, String toUserID, String streamType, StreamStatsData data) {
		super();
		this.fromUserID = fromUserID;
		this.toUserID = toUserID;	
		type.put(streamType, data);
	}	
}
