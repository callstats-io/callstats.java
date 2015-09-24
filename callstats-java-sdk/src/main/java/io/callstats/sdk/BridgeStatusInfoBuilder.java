package io.callstats.sdk;

public class BridgeStatusInfoBuilder {
	private float cpuUsage;
	private float memoryUsage;
	private long sentBytes;
	private long receivedBytes;
	private float intervalLoss;
	private float totalLoss;
	private float avgIntervalRtt;
	private float avgIntervalJitter;
	
	public BridgeStatusInfoBuilder() {
		
	}
	
	public BridgeStatusInfoBuilder cpuUsage(float cpuUsage) {
		this.cpuUsage = cpuUsage;
		return this;
	}
	
	public BridgeStatusInfoBuilder memoryUsage(float memoryUsage) {
		this.memoryUsage = memoryUsage;
		return this;
	}
	
	public BridgeStatusInfoBuilder sentBytes(long sentBytes) {
		this.sentBytes = sentBytes;
		return this;
	}
	
	public BridgeStatusInfoBuilder receivedBytes(long receivedBytes) {
		this.receivedBytes = receivedBytes;
		return this;
	}
	
	public BridgeStatusInfoBuilder intervalLoss(float intervalLoss) {
		this.intervalLoss = intervalLoss;
		return this;
	}
	
	public BridgeStatusInfoBuilder totalLoss(float totalLoss) {
		this.totalLoss = totalLoss;
		return this;
	}
	
	public BridgeStatusInfoBuilder avgIntervalRtt(float avgIntervalRtt) {
		this.avgIntervalRtt = avgIntervalRtt;
		return this;
	}
	
	public BridgeStatusInfoBuilder avgIntervalJitter(float avgIntervalJitter) {
		this.avgIntervalJitter = avgIntervalJitter;
		return this;
	}
	
	public BridgeStatusInfo build() {
		return new BridgeStatusInfo(cpuUsage, memoryUsage, sentBytes, receivedBytes, intervalLoss, totalLoss, avgIntervalRtt, avgIntervalJitter);
	}		
}
