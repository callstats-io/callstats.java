package io.callstats.sdk.messages;

public class EndPointInfo {

	private String endpointType;
	private EndPointData endpointData;
		
	public EndPointInfo(String endpointType, EndPointData endpointData) {
		super();
		this.endpointType = endpointType;
		this.endpointData = endpointData;
	}
	
	public String getEndpointType() {
		return endpointType;
	}
	public void setEndpointType(String endpointType) {
		this.endpointType = endpointType;
	}
	public EndPointData getEndpointData() {
		return endpointData;
	}
	public void setEndpointData(EndPointData endpointData) {
		this.endpointData = endpointData;
	}
	
}
