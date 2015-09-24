package io.callstats.sdk;

public class BridgeStatusInfo {
	private float cpuUsage;
	private float memoryUsage;
	private long sentBytes;
	private long receivedBytes;
	private float intervalLoss;
	private float totalLoss;
	private float avgIntervalRtt;
	private float avgIntervalJitter;
	
	public BridgeStatusInfo(float cpuUsage, float memoryUsage, long sentBytes,
			long receivedBytes, float intervalLoss, float totalLoss,
			float avgIntervalRtt, float avgIntervalJitter) {
		super();
		this.cpuUsage = cpuUsage;
		this.memoryUsage = memoryUsage;
		this.sentBytes = sentBytes;
		this.receivedBytes = receivedBytes;
		this.intervalLoss = intervalLoss;
		this.totalLoss = totalLoss;
		this.avgIntervalRtt = avgIntervalRtt;
		this.avgIntervalJitter = avgIntervalJitter;
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
	public long getSentBytes() {
		return sentBytes;
	}
	public void setSentBytes(long sentBytes) {
		this.sentBytes = sentBytes;
	}
	public long getReceivedBytes() {
		return receivedBytes;
	}
	public void setReceivedBytes(long receivedBytes) {
		this.receivedBytes = receivedBytes;
	}
	public float getIntervalLoss() {
		return intervalLoss;
	}
	public void setIntervalLoss(float intervalLoss) {
		this.intervalLoss = intervalLoss;
	}
	public float getTotalLoss() {
		return totalLoss;
	}
	public void setTotalLoss(float totalLoss) {
		this.totalLoss = totalLoss;
	}
	public float getAvgIntervalRtt() {
		return avgIntervalRtt;
	}
	public void setAvgIntervalRtt(float avgIntervalRtt) {
		this.avgIntervalRtt = avgIntervalRtt;
	}
	public float getAvgIntervalJitter() {
		return avgIntervalJitter;
	}
	public void setAvgIntervalJitter(float avgIntervalJitter) {
		this.avgIntervalJitter = avgIntervalJitter;
	}	
}
