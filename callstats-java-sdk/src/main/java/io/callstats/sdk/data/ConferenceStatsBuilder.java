package io.callstats.sdk.data;

public class ConferenceStatsBuilder {
	
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
	
	public String getLocalUserID() {
		return localUserID;
	}
	public CallStatsStreamType getStatsType() {
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
		this.statsType = statsType;
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
	
	public ConferenceStatsBuilder packetsSent(long  packetsSent) {
		this.packetsSent = packetsSent;
		return this;
	}
	
	public ConferenceStatsBuilder bytesSent(long  bytesSent) {
		this.bytesSent = bytesSent;
		return this;
	}
	
	public ConferenceStatsBuilder rtt(int  rtt) {
		this.rtt = rtt;
		return this;
	}
	
	public ConferenceStatsBuilder jitter(double  jitter) {
		this.jitter = jitter;
		return this;
	}
	
	public ConferenceStats build() {
		return new ConferenceStats(this);
	}
}
