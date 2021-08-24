package io.callstats.sdk.messages;

import java.util.ArrayList;
import java.util.List;

import io.callstats.sdk.data.ServerInfo;

class IceCandidate {
	String id;
	String type;
	String ip;
	int port;
	String candidateType;
	String transport;
	String networkType;
}

class IceCandidatePair {
	String id;
	String localCandidateId;
	String remoteCandidateId;
	String state;
	int    priority;
	Boolean nominated;
	String googActiveConnection;
}

public class FabricSetupEvent {
	long timestamp;
	String localID;
	String originID;
	String deviceID;
	String remoteID;
	String connectionID;
	List<IceCandidate> localIceCandidates = new ArrayList<IceCandidate>();
	List<IceCandidate> remoteIceCandidates = new ArrayList<IceCandidate>();
	List<IceCandidatePair> iceCandidatePairs = new ArrayList<IceCandidatePair>();
	  
	public FabricSetupEvent(String localID, String originID,  String remoteID, long timestamp) {
		this.localID = localID;
	    this.originID = originID;
	    this.timestamp = timestamp;
	    this.deviceID = this.localID;
	    this.connectionID = remoteID;
	    this.remoteID = remoteID;
	}
}
