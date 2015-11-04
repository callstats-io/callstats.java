package io.callstats.sdk.data;

public class ConferenceStatsBuilder {
	
	String localUserID;
	String statsType; //inbound or outbound
	String ssrc;
	String fromUserID;
	String toUserID;
	int rtt;
	long packetsSent;
	long bytesSent;
	float jitter;
	String ucID;
	String confID;
	
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
	public String getToUserID() {
		return toUserID;
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
	public float getJitter() {
		return jitter;
	}
	public String getUcID() {
		return ucID;
	}
	public String getConfID() {
		return confID;
	}
	
	public ConferenceStatsBuilder localUserID(String  localUserID) {
		this.localUserID = localUserID;
		return this;
	}
	
	public ConferenceStatsBuilder statsType(String  statsType) {
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
	
	public ConferenceStatsBuilder toUserID(String  toUserID) {
		this.toUserID = toUserID;
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
	
	public ConferenceStatsBuilder jitter(float  jitter) {
		this.jitter = jitter;
		return this;
	}
	
	public ConferenceStats build() {
		return new ConferenceStats(this);
	}
}
