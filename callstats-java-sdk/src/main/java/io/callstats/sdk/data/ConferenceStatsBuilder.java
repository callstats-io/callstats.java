package io.callstats.sdk.data;

public class ConferenceStatsBuilder {
	
	String localUserID;
	String remoteUserID;
	String statsType; //inbound or outbound
	String ssrc;
	String fromUserID;
	String ucID;
	String confID;
	
	long packetsReceived;
	long packetsLost;
	long packetsDuplicated;
	long packetsDiscarded;
	long packetsRepaired;
	
	long bytesSent;
	long bytesReceived;
	long bytesDuplicated;
	long bytesDiscarded;
	long bytesRepaired;
	
	long burstPacketsLost;
	long burstLossIntervalCount;
	long burstPacketsDiscarded;
	long burstDiscardIntervalCount;
	
	double gapLossRate;
	double gapDiscardRate;
	
	double fractionalPacketLost;
	double fractionalPacketDiscarded;
	
	long framesSent;
	long framesReceived;
	long framesLost;
	long framesDropped;
	long framesCorrupted;
	
	int rtt;
	double jitter;
	
	double currentPlayoutDelay;
	double maxPlayoutDelay;
	double minPlayoutDelay;
	
	double currentJBDelay;
	double highWatermarkJBDelay;
	double lowWatermarkJBDelay;
	double maxJBDelay;
	double minJBDelay;
	double avsync;
		
	long packetsSent;
	public long getPacketsReceived() {
		return packetsReceived;
	}
	public long getPacketsLost() {
		return packetsLost;
	}
	public long getPacketsDuplicated() {
		return packetsDuplicated;
	}
	public long getPacketsDiscarded() {
		return packetsDiscarded;
	}
	public long getPacketsRepaired() {
		return packetsRepaired;
	}
	public long getBytesReceived() {
		return bytesReceived;
	}
	public long getBytesDuplicated() {
		return bytesDuplicated;
	}
	public long getBytesDiscarded() {
		return bytesDiscarded;
	}
	public long getBytesRepaired() {
		return bytesRepaired;
	}
	public long getBurstPacketsLost() {
		return burstPacketsLost;
	}
	public long getBurstLossIntervalCount() {
		return burstLossIntervalCount;
	}
	public long getBurstPacketsDiscarded() {
		return burstPacketsDiscarded;
	}
	public long getBurstDiscardIntervalCount() {
		return burstDiscardIntervalCount;
	}
	public double getGapLossRate() {
		return gapLossRate;
	}
	public double getGapDiscardRate() {
		return gapDiscardRate;
	}
	public double getFractionalPacketLost() {
		return fractionalPacketLost;
	}
	public double getFractionalPacketDiscarded() {
		return fractionalPacketDiscarded;
	}
	public long getFramesSent() {
		return framesSent;
	}
	public long getFramesReceived() {
		return framesReceived;
	}
	public long getFramesLost() {
		return framesLost;
	}
	public long getFramesDropped() {
		return framesDropped;
	}
	public long getFramesCorrupted() {
		return framesCorrupted;
	}
	public double getCurrentPlayoutDelay() {
		return currentPlayoutDelay;
	}
	public double getMaxPlayoutDelay() {
		return maxPlayoutDelay;
	}
	public double getMinPlayoutDelay() {
		return minPlayoutDelay;
	}
	public double getCurrentJBDelay() {
		return currentJBDelay;
	}
	public double getHighWatermarkJBDelay() {
		return highWatermarkJBDelay;
	}
	public double getLowWatermarkJBDelay() {
		return lowWatermarkJBDelay;
	}
	public double getMaxJBDelay() {
		return maxJBDelay;
	}
	public double getMinJBDelay() {
		return minJBDelay;
	}
	public double getAvsync() {
		return avsync;
	}
	
	public String getLocalUserID() {
		return localUserID;
	}
	public String getStatsType() {
		return statsType;
	}
	public String getSsrc() {
		return ssrc;
	}
	public String getFromUserID() {
		return fromUserID;
	}
	public int getRtt() {
		return rtt;
	}
	public long getPacketsSent() {
		return packetsSent;
	}
	public long getBytesSent() {
		return bytesSent;
	}
	public double getJitter() {
		return jitter;
	}
	public String getUcID() {
		return ucID;
	}
	public String getConfID() {
		return confID;
	}
	public String getRemoteUserID() {
		return remoteUserID;
	}
	
	public ConferenceStatsBuilder localUserID(String  localUserID) {
		this.localUserID = localUserID;
		return this;
	}
	
	public ConferenceStatsBuilder statsType(CallStatsStreamType  statsType) {
		this.statsType = statsType.getMessageType();
		return this;
	}
	
	public ConferenceStatsBuilder ssrc(String  ssrc) {
		this.ssrc = ssrc;
		return this;
	}
	
	public ConferenceStatsBuilder fromUserID(String  fromUserID) {
		this.fromUserID = fromUserID;
		return this;
	}
	
	public ConferenceStatsBuilder remoteUserID(String  remoteUserID) {
		this.remoteUserID = remoteUserID;
		return this;
	}
	
	public ConferenceStatsBuilder ucID(String  ucID) {
		this.ucID = ucID;
		return this;
	}
	
	public ConferenceStatsBuilder confID(String  confID) {
		this.confID = confID;
		return this;
	}
	
		
	public ConferenceStatsBuilder  packetsSent(long  packetsSent) {
		this.packetsSent = packetsSent;
		return this;
	}
	public ConferenceStatsBuilder packetsReceived(long packetsReceived) {
		this.packetsReceived = packetsReceived;
		return this;
	}
	public ConferenceStatsBuilder packetsLost(long packetsLost) {
		this.packetsLost = packetsLost;
		return this;
	}
	public ConferenceStatsBuilder packetsDuplicated(long packetsDuplicated) {
		this.packetsDuplicated = packetsDuplicated;
		return this;
	}
	public ConferenceStatsBuilder packetsDiscarded(long packetsDiscarded) {
		this.packetsDiscarded = packetsDiscarded;
		return this;
	}
	public ConferenceStatsBuilder packetsRepaired(long packetsRepaired) {
		this.packetsRepaired = packetsRepaired;
		return this;
	}
	
	public ConferenceStatsBuilder bytesSent(long bytesSent) {
		this.bytesSent = bytesSent;
		return this;
	}
	public ConferenceStatsBuilder bytesReceived(long bytesReceived) {
		this.bytesReceived = bytesReceived;
		return this;
	}
	public ConferenceStatsBuilder bytesDuplicated(long bytesDuplicated) {
		this.bytesDuplicated = bytesDuplicated;
		return this;
	}
	public ConferenceStatsBuilder bytesDiscarded(long bytesDiscarded) {
		this.bytesDiscarded = bytesDiscarded;
		return this;
	}
	public ConferenceStatsBuilder bytesRepaired(long bytesRepaired) {
		this.bytesRepaired = bytesRepaired;
		return this;
	}
	
	public ConferenceStatsBuilder burstPacketsLost(long burstPacketsLost) {
		this.burstPacketsLost = burstPacketsLost;
		return this;
	}
	public ConferenceStatsBuilder burstLossIntervalCount(long burstLossIntervalCount) {
		this.burstLossIntervalCount = burstLossIntervalCount;
		return this;
	}
	public ConferenceStatsBuilder burstPacketsDiscarded(long burstPacketsDiscarded) {
		this.burstPacketsDiscarded = burstPacketsDiscarded;
		return this;
	}
	public ConferenceStatsBuilder burstDiscardIntervalCount(long burstDiscardIntervalCount) {
		this.burstDiscardIntervalCount = burstDiscardIntervalCount;
		return this;
	}
	
	public ConferenceStatsBuilder gapLossRate(double gapLossRate) {
		this.gapLossRate = gapLossRate;
		return this;
	}
	public ConferenceStatsBuilder gapDiscardRate(double gapDiscardRate) {
		this.gapDiscardRate = gapDiscardRate;
		return this;
	}
	
	public ConferenceStatsBuilder fractionalPacketLost(double fractionalPacketLost) {
		this.fractionalPacketLost = fractionalPacketLost;
		return this;
	}
	public ConferenceStatsBuilder fractionalPacketDiscarded(double fractionalPacketDiscarded) {
		this.fractionalPacketDiscarded = fractionalPacketDiscarded;
		return this;
	}
	
	public ConferenceStatsBuilder framesSent(long framesSent) {
		this.framesSent = framesSent;
		return this;
	}
	public ConferenceStatsBuilder framesReceived(long framesReceived) {
		this.framesReceived = framesReceived;
		return this;
	}
	public ConferenceStatsBuilder framesLost(long framesLost) {
		this.framesLost = framesLost;
		return this;
	}
	public ConferenceStatsBuilder framesDropped(long framesDropped) {
		this.framesDropped = framesDropped;
		return this;
	}
	public ConferenceStatsBuilder framesCorrupted(long framesCorrupted) {
		this.framesCorrupted = framesCorrupted;
		return this;
	}
	
	public ConferenceStatsBuilder rtt(int rtt) {
		this.rtt = rtt;
		return this;
	}
	public ConferenceStatsBuilder jitter(double jitter) {
		this.jitter = jitter;
		return this;
	}
	
	public ConferenceStatsBuilder currentPlayoutDelay(double currentPlayoutDelay) {
		this.currentPlayoutDelay = currentPlayoutDelay;
		return this;
	}
	public ConferenceStatsBuilder maxPlayoutDelay(double maxPlayoutDelay) {
		this.maxPlayoutDelay = maxPlayoutDelay;
		return this;
	}
	public ConferenceStatsBuilder minPlayoutDelay(double minPlayoutDelay) {
		this.minPlayoutDelay = minPlayoutDelay;
		return this;
	}
	
	public ConferenceStatsBuilder currentJBDelay(double currentJBDelay) {
		this.currentJBDelay = currentJBDelay;
		return this;
	}
	public ConferenceStatsBuilder highWatermarkJBDelay(double highWatermarkJBDelay) {
		this.highWatermarkJBDelay = highWatermarkJBDelay;
		return this;
	}
	public ConferenceStatsBuilder lowWatermarkJBDelay(double lowWatermarkJBDelay) {
		this.lowWatermarkJBDelay = lowWatermarkJBDelay;
		return this;
	}
	public ConferenceStatsBuilder maxJBDelay(double maxJBDelay) {
		this.maxJBDelay = maxJBDelay;
		return this;
	}
	public ConferenceStatsBuilder minJBDelay( double minJBDelay) {
		this.minJBDelay = minJBDelay;
		return this;
	}
	public ConferenceStatsBuilder avsync(double avsync) {
		this.avsync = avsync;
		return this;
	}
	
	public ConferenceStats build() {
		return new ConferenceStats(this);
	}
}
