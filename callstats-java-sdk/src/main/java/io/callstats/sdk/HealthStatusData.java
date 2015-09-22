package io.callstats.sdk;

public class HealthStatusData {
	private float cpuUsage;
	private float memoryUsage;
	
	public HealthStatusData() {
		
	}
	
	public float getMemoryUsage() {
		return memoryUsage;
	}
	public void setMemoryUsage(float memoryUsage) {
		this.memoryUsage = memoryUsage;
	}
	public float getCpuUsage() {
		return cpuUsage;
	}
	public void setCpuUsage(float cpuUsage) {
		this.cpuUsage = cpuUsage;
	}		
}
