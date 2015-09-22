package io.callstats.sdk;

public class TrafficStatusData {
	long sentBytes;
	long receivedBytes;
	float intervalLoss;
	float totalLoss;
	float avgIntervalRtt;
	float avgIntervalJitter;
	
	public TrafficStatusData() {
		
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