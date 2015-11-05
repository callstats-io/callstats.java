package io.callstats.sdk.data;

public class ConferenceStats {
	String localUserID;
	String remoteUserID;
	CallStatsStreamType statsType; //inbound or outbound
	String ssrc;
	String fromUserID;
	int rtt;
	long packetsSent;
	long bytesSent;
	double jitter;
	String ucID;
	String confID;
	
	public String getUcID() {
		return ucID;
	}
	public void setUcID(String ucID) {
		this.ucID = ucID;
	}
	public String getConfID() {
		return confID;
	}
	public void setConfID(String confID) {
		this.confID = confID;
	}
	public String getLocalUserID() {
		return localUserID;
	}
	public void setLocalUserID(String localUserID) {
		this.localUserID = localUserID;
	}
	public String getSsrc() {
		return ssrc;
	}
	public void setSsrc(String ssrc) {
		this.ssrc = ssrc;
	}
	public String getFromUserID() {
		return fromUserID;
	}
	public void setFromUserID(String fromUserID) {
		this.fromUserID = fromUserID;
	}
	public int getRtt() {
		return rtt;
	}
	public void setRtt(int rtt) {
		this.rtt = rtt;
	}
	public long getPacketsSent() {
		return packetsSent;
	}
	public void setPacketsSent(long packetsSent) {
		this.packetsSent = packetsSent;
	}
	public long getBytesSent() {
		return bytesSent;
	}
	public void setBytesSent(long bytesSent) {
		this.bytesSent = bytesSent;
	}
	public double getJitter() {
		return jitter;
	}
	public void setJitter(double jitter) {
		this.jitter = jitter;
	}	
	
	public CallStatsStreamType getStatsType() {
		return statsType;
	}
	public void setStatsType(CallStatsStreamType statsType) {
		this.statsType = statsType;
	}
	
	public String getRemoteUserID() {
		return remoteUserID;
	}
	public void setRemoteUserID(String remoteUserID) {
		this.remoteUserID = remoteUserID;
	}
	
	
	public ConferenceStats(ConferenceStatsBuilder builder) {
		this.localUserID = builder.getLocalUserID();
		this.statsType = builder.getStatsType();
		this.fromUserID = builder.getFromUserID();
		this.rtt = builder.getRtt();
		this.packetsSent = builder.getPacketsSent();
		this.bytesSent = builder.getBytesSent();
		this.jitter = builder.getJitter();
		this.ucID = builder.getUcID();
		this.confID = builder.getConfID();
		this.ssrc = builder.getSsrc();
		this.remoteUserID = builder.getRemoteUserID();
	}
}
