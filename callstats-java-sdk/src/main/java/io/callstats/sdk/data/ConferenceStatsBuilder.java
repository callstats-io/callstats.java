package io.callstats.sdk.data;

public class ConferenceStatsBuilder {

  String localUserID;
  String remoteUserID;
  String statsType; // inbound or outbound
  String ssrc;
  String fromUserID;
  String ucID;
  String confID;
  String mediaType;

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

  Long packetsSent;

  public Long getPacketsReceived() {
    return packetsReceived;
  }

  public Long getPacketsLost() {
    return packetsLost;
  }

  public Long getPacketsDuplicated() {
    return packetsDuplicated;
  }

  public Long getPacketsDiscarded() {
    return packetsDiscarded;
  }

  public Long getPacketsRepaired() {
    return packetsRepaired;
  }

  public Long getBytesReceived() {
    return bytesReceived;
  }

  public Long getBytesDuplicated() {
    return bytesDuplicated;
  }

  public Long getBytesDiscarded() {
    return bytesDiscarded;
  }

  public Long getBytesRepaired() {
    return bytesRepaired;
  }

  public Long getBurstPacketsLost() {
    return burstPacketsLost;
  }

  public Long getBurstLossIntervalCount() {
    return burstLossIntervalCount;
  }

  public Long getBurstPacketsDiscarded() {
    return burstPacketsDiscarded;
  }

  public Long getBurstDiscardIntervalCount() {
    return burstDiscardIntervalCount;
  }

  public Double getGapLossRate() {
    return gapLossRate;
  }

  public Double getGapDiscardRate() {
    return gapDiscardRate;
  }

  public Double getFractionalPacketLost() {
    return fractionalPacketLost;
  }

  public Double getFractionalPacketDiscarded() {
    return fractionalPacketDiscarded;
  }

  public Long getFramesSent() {
    return framesSent;
  }

  public Long getFramesReceived() {
    return framesReceived;
  }

  public Long getFramesLost() {
    return framesLost;
  }

  public Long getFramesDropped() {
    return framesDropped;
  }

  public Long getFramesCorrupted() {
    return framesCorrupted;
  }

  public Double getCurrentPlayoutDelay() {
    return currentPlayoutDelay;
  }

  public Double getMaxPlayoutDelay() {
    return maxPlayoutDelay;
  }

  public Double getMinPlayoutDelay() {
    return minPlayoutDelay;
  }

  public Double getCurrentJBDelay() {
    return currentJBDelay;
  }

  public Double getHighWatermarkJBDelay() {
    return highWatermarkJBDelay;
  }

  public Double getLowWatermarkJBDelay() {
    return lowWatermarkJBDelay;
  }

  public Double getMaxJBDelay() {
    return maxJBDelay;
  }

  public Double getMinJBDelay() {
    return minJBDelay;
  }

  public Double getAvsync() {
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

  public Integer getRtt() {
    return rtt;
  }

  public Long getPacketsSent() {
    return packetsSent;
  }

  public Long getBytesSent() {
    return bytesSent;
  }

  public Double getJitter() {
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
  
  public String getMediaType() {
	return mediaType;
  }
  
  
  public ConferenceStatsBuilder localUserID(String localUserID) {
    this.localUserID = localUserID;
    return this;
  }

  public ConferenceStatsBuilder statsType(CallStatsStreamType statsType) {
    this.statsType = statsType.getMessageType();
    return this;
  }
  
  public ConferenceStatsBuilder mediaType(String mediaType) {	
	this.mediaType = mediaType;
	return this;
  }

  public ConferenceStatsBuilder ssrc(String ssrc) {
    this.ssrc = ssrc;
    return this;
  }

  public ConferenceStatsBuilder fromUserID(String fromUserID) {
    this.fromUserID = fromUserID;
    return this;
  }

  public ConferenceStatsBuilder remoteUserID(String remoteUserID) {
    this.remoteUserID = remoteUserID;
    return this;
  }

  public ConferenceStatsBuilder ucID(String ucID) {
    this.ucID = ucID;
    return this;
  }

  public ConferenceStatsBuilder confID(String confID) {
    this.confID = confID;
    return this;
  }


  public ConferenceStatsBuilder packetsSent(long packetsSent) {
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

  public ConferenceStatsBuilder minJBDelay(double minJBDelay) {
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
