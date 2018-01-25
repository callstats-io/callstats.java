package io.callstats.sdk.messages;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import io.callstats.sdk.data.ConferenceStats;

public class ConferenceStatsEvent {

	String localID;
	String originID;
	String deviceID;
	String remoteID;
	String connectionID;
	long timestamp;
	@SerializedName("stats")
	List<ConferenceStats> conferenceStatsList = new ArrayList<ConferenceStats>();

	public String getLocalID() {
		return localID;
	}
	public void setLocalID(String localID) {
		this.localID = localID;
	}
	public String getOriginID() {
		return originID;
	}
	public void setOriginID(String originID) {
		this.originID = originID;
	}
	public String getDeviceID() {
		return deviceID;
	}
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public void addStats(ConferenceStats conferenceStats) {
		conferenceStatsList.add(conferenceStats);
	}

	public ConferenceStatsEvent(String localID, String remoteID, String originID, String connectionID, long timestamp) {
		this.localID = localID;
		this.originID = originID;
		this.timestamp = timestamp;
		this.deviceID = this.localID;
		this.remoteID = remoteID;
		this.connectionID = connectionID;
	}
}
