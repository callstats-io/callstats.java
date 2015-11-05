package io.callstats.sdk.data;

import com.google.gson.annotations.SerializedName;


public class StreamStats {
	String fromUserID;
	String mappedSSRC;
	
	@SerializedName("inbound")
	StreamStatsData inboundStatsData;
	
	@SerializedName("outbound")
	StreamStatsData outboundStatsData;
	
	public String getFromUserID() {
		return fromUserID;
	}
	public void setFromUserID(String fromUserID) {
		this.fromUserID = fromUserID;
	}
	
	public String getMappedSSRC() {
		return mappedSSRC;
	}
	public void setMappedSSRC(String mappedSSRC) {
		this.mappedSSRC = mappedSSRC;
	}
	public StreamStatsData getInboundStatsData() {
		return inboundStatsData;
	}
	public void setInboundStatsData(StreamStatsData inboundStatsData) {
		this.inboundStatsData = inboundStatsData;
	}
	public StreamStatsData getOutboundStatsData() {
		return outboundStatsData;
	}
	public void setOutboundStatsData(StreamStatsData outboundStatsData) {
		this.outboundStatsData = outboundStatsData;
	}
			
	public StreamStats(String fromUserID, CallStatsStreamType type, StreamStatsData data) {
		super();
		this.fromUserID = fromUserID;	
		
		if(type == CallStatsStreamType.INBOUND) {
			setInboundStatsData(data);
		} else {
			setOutboundStatsData(data);
		}
	}	
}
