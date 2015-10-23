package io.callstats.sdk.messages;

public class EndPointData {
	
	private String name;
	private String os;
	private String ver;
		
	public EndPointData(String name, String os, String ver) {
		super();
		this.name = name;
		this.os = os;
		this.ver = ver;
	}
	
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
