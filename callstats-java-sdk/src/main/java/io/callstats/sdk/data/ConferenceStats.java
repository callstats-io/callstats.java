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
	public int getRtt() {
		return data.getRtt();
	}
	public void setRtt(int rtt) {
		data.setRtt(rtt);
	}
	public long getPacketsSent() {
		return data.getPacketsSent();
	}
	public void setPacketsSent(long packetsSent) {
		data.setPacketsSent(packetsSent);
	}
	public long getBytesSent() {
		return data.getBytesSent();
	}
	public void setBytesSent(long bytesSent) {
		data.setBytesSent(bytesSent);
	}
	public double getJitter() {
		return data.getJitter();
	}
	public void setJitter(double jitter) {
		data.setJitter(jitter);
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
		
		public ConferenceStatsInfo(ConferenceStatsBuilder builder) {
			this.rtt = builder.getRtt();
			this.packetsSent = builder.getPacketsSent();
			this.bytesSent = builder.getBytesSent();
			this.jitter = builder.getJitter();
		}
		
		public ConferenceStatsInfo() {
			
		}
	}


}
