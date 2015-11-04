package io.callstats.sdk.data;

public class StreamStatsData {
	
	int rtt;
	long packetsSent;
	long bytesSent;
	double jitter;
	
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
	
	public StreamStatsData(int rtt, long packetsSent, long bytesSent, float jitter) {
		super();
		this.rtt = rtt;
		this.packetsSent = packetsSent;
		this.bytesSent = bytesSent;
		this.jitter = jitter;
	}	

}
