package io.callstats.sdk;

public class EndpointInfo {
	String endpointType;
	EndPointData endpointData;
	
	public EndpointInfo() {
		super();
		endpointData = new EndPointData();
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
	
	public String getName() {
		return endpointData.getName();
	}
	public void setName(String name) {
		endpointData.setName(name);
	}
	public String getOs() {
		return endpointData.getOs();
	}
	public void setOs(String os) {
		endpointData.setOs(os);
	}
	public String getVer() {
		return endpointData.getVer();
	}
	public void setVer(String ver) {
		endpointData.setVer(ver);
	}
	
	private class EndPointData {
		String name;
		String  os;		
		String ver;
		
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getOs() {
			return os;
		}
		public void setOs(String os) {
			this.os = os;
		}
		public String getVer() {
			return ver;
		}
		public void setVer(String ver) {
			this.ver = ver;
		}
	}
			
}