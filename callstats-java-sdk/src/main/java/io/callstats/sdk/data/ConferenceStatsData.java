package io.callstats.sdk.data;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class ConferenceStatsData {
	
	transient String localID;
	transient String remoteID;
	transient String ucID;
	transient String confID;
	
	@SerializedName("streams")
	List<ConferenceStats> conferenceStatsList = new ArrayList<ConferenceStats>();
	
	public ConferenceStatsData(String localUserID,String remoteUserid, String ucID, String confID) {
		this.localID = localUserID;
		this.remoteID = remoteUserid;
		this.ucID = ucID;
		this.confID = confID;
	}
	
	public void addStats(ConferenceStats stats) {
		conferenceStatsList.add(stats);
	}

}
