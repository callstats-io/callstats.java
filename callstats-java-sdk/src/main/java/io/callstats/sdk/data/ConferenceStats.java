package io.callstats.sdk.data;

import com.google.gson.annotations.SerializedName;

public class ConferenceStats {
	
	transient String localUserID;
	transient String remoteUserID;
	@SerializedName("streamType")
	String statsType; //inbound or outbound
	String ssrc;
	String fromUserID;
	transient String ucID;
	transient String confID;
	
	public ConferenceStatsInfo data;
	
	public ConferenceStats() {
		data = new ConferenceStatsInfo();
	}
	
	public ConferenceStats(ConferenceStatsBuilder builder) {
		this.localUserID = builder.getLocalUserID();
		this.statsType = builder.getStatsType();
		this.fromUserID = builder.getFromUserID();
		this.ssrc = builder.getSsrc();
		this.remoteUserID = builder.getRemoteUserID();
		this.ucID = builder.getUcID();
		this.confID = builder.getConfID();
		data = new ConferenceStatsInfo(builder);
	}
	
	public String getUcID() {
		return this.ucID;
	}
	public void setUcID(String ucID) {
		this.ucID = ucID;
	}
	public String getConfID() {
		return this.confID;
	}
	public void setConfID(String confID) {
		this.confID = confID;
	}
	public String getLocalUserID() {
		return this.localUserID;
	}
	public void setLocalUserID(String localUserID) {
		 this.localUserID = localUserID;
	}
	public String getSsrc() {
		return this.ssrc;
	}
	public void setSsrc(String ssrc) {
		this.ssrc = ssrc;
	}
	public String getFromUserID() {
		return this.fromUserID;
	}
	public void setFromUserID(String fromUserID) {
		this.fromUserID = fromUserID;
	}
		
	public String getStatsType() {
		return this.statsType;
	}
	public void setStatsType(CallStatsStreamType statsType) {
		this.statsType = statsType.getMessageType();
	}
	
	public String getRemoteUserID() {
		return this.remoteUserID;
	}
	public void setRemoteUserID(String remoteUserID) {
		this.remoteUserID = remoteUserID;
	}
	
	class ConferenceStatsInfo {
		Long packetsSent;
		Long packetsReceived;
		Long packetsLost;
		Long packetsDuplicated;
		Long packetsDiscarded;
		Long packetsRepaired;
		
		Long bytesSent;
		Long bytesReceived;
		Long bytesDuplicated;
		Long bytesDiscarded;
		Long bytesRepaired;
		
		Long burstPacketsLost;
		Long burstLossIntervalCount;
		Long burstPacketsDiscarded;
		Long burstDiscardIntervalCount;
		
		Double gapLossRate;
		Double gapDiscardRate;
		
		Double fractionalPacketLost;
		Double fractionalPacketDiscarded;
		
		Long framesSent;
		Long framesReceived;
		Long framesLost;
		Long framesDropped;
		Long framesCorrupted;
		
		Integer rtt;
		Double jitter;
		
		Double currentPlayoutDelay;
		Double maxPlayoutDelay;
		Double minPlayoutDelay;
		
		Double currentJBDelay;
		Double highWatermarkJBDelay;
		Double lowWatermarkJBDelay;
		Double maxJBDelay;
		Double minJBDelay;
		Double avsync;
		
		
		public ConferenceStatsInfo(ConferenceStatsBuilder builder) {
			
			this.packetsSent = builder.getPacketsSent();
			this.packetsReceived = builder.getPacketsReceived();
			this.packetsLost = builder.getPacketsLost();
			this.packetsDuplicated = builder.getPacketsDuplicated();
			this.packetsDiscarded = builder.getPacketsDiscarded();
			this.packetsRepaired = builder.getPacketsRepaired();
			
			this.bytesSent = builder.getBytesSent();
			this.bytesReceived = builder.getBytesReceived();
			this.bytesDuplicated = builder.getBytesDuplicated();
			this.bytesDiscarded = builder.getBytesDiscarded();
			this.bytesRepaired = builder.getBytesRepaired();
			
			this.burstPacketsLost = builder.getBurstPacketsLost();
			this.burstLossIntervalCount = builder.getBurstLossIntervalCount();
			this.burstPacketsDiscarded = builder.getBurstPacketsDiscarded();
			this.burstDiscardIntervalCount = builder.getBurstDiscardIntervalCount();
			
			this.gapLossRate = builder.getGapLossRate();
			this.gapDiscardRate = builder.getGapDiscardRate();
			
			this.fractionalPacketLost = builder.getFractionalPacketLost();
			this.fractionalPacketDiscarded = builder.getFractionalPacketDiscarded();
			
			this.framesSent = builder.getFramesSent();
			this.framesReceived = builder.getFramesReceived();
			this.framesLost = builder.getFramesLost();
			this.framesDropped = builder.getFramesDropped();
			this.framesCorrupted = builder.getFramesCorrupted();
			
			this.rtt = builder.getRtt();
			this.jitter = builder.getJitter();
			
			this.currentPlayoutDelay = builder.getCurrentPlayoutDelay();
			this.maxPlayoutDelay = builder.getMaxPlayoutDelay();
			this.minPlayoutDelay = builder.getMinPlayoutDelay();
			
			this.currentJBDelay = builder.getCurrentJBDelay();
			this.highWatermarkJBDelay = builder.getHighWatermarkJBDelay();
			this.lowWatermarkJBDelay = builder.getLowWatermarkJBDelay();
			this.maxJBDelay = builder.getMaxJBDelay();
			this.minJBDelay = builder.getMinJBDelay();
			this.avsync = builder.getAvsync();		
		}
		
		public ConferenceStatsInfo() {
			
		}
	}


}
