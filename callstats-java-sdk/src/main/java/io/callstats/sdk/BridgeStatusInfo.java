package io.callstats.sdk;

/**
 * The Class BridgeStatusInfo.
 */
public class BridgeStatusInfo {
	
	/** The cpu usage. */
	private float cpuUsage;
	
	/** The memory usage. */
	private float memoryUsage;
	
	/** The sent bytes. */
	private long sentBytes;
	
	/** The received bytes. */
	private long receivedBytes;
	
	/** The interval loss. */
	private float intervalLoss;
	
	/** The total loss. */
	private float totalLoss;
	
	/** The avg interval rtt. */
	private float avgIntervalRtt;
	
	/** The avg interval jitter. */
	private float avgIntervalJitter;
	
	/**
	 * Instantiates a new bridge status info.
	 *
	 * @param cpuUsage the cpu usage
	 * @param memoryUsage the memory usage
	 * @param sentBytes the sent bytes
	 * @param receivedBytes the received bytes
	 * @param intervalLoss the interval loss
	 * @param totalLoss the total loss
	 * @param avgIntervalRtt the avg interval rtt
	 * @param avgIntervalJitter the avg interval jitter
	 */
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
	
	/**
	 * Gets the memory usage.
	 *
	 * @return the memory usage
	 */
	public float getMemoryUsage() {
		return memoryUsage;
	}
	
	/**
	 * Sets the memory usage.
	 *
	 * @param memoryUsage the new memory usage
	 */
	public void setMemoryUsage(float memoryUsage) {
		this.memoryUsage = memoryUsage;
	}
	
	/**
	 * Gets the cpu usage.
	 *
	 * @return the cpu usage
	 */
	public float getCpuUsage() {
		return cpuUsage;
	}
	
	/**
	 * Sets the cpu usage.
	 *
	 * @param cpuUsage the new cpu usage
	 */
	public void setCpuUsage(float cpuUsage) {
		this.cpuUsage = cpuUsage;
	}		
	
	/**
	 * Gets the sent bytes.
	 *
	 * @return the sent bytes
	 */
	public long getSentBytes() {
		return sentBytes;
	}
	
	/**
	 * Sets the sent bytes.
	 *
	 * @param sentBytes the new sent bytes
	 */
	public void setSentBytes(long sentBytes) {
		this.sentBytes = sentBytes;
	}
	
	/**
	 * Gets the received bytes.
	 *
	 * @return the received bytes
	 */
	public long getReceivedBytes() {
		return receivedBytes;
	}
	
	/**
	 * Sets the received bytes.
	 *
	 * @param receivedBytes the new received bytes
	 */
	public void setReceivedBytes(long receivedBytes) {
		this.receivedBytes = receivedBytes;
	}
	
	/**
	 * Gets the interval loss.
	 *
	 * @return the interval loss
	 */
	public float getIntervalLoss() {
		return intervalLoss;
	}
	
	/**
	 * Sets the interval loss.
	 *
	 * @param intervalLoss the new interval loss
	 */
	public void setIntervalLoss(float intervalLoss) {
		this.intervalLoss = intervalLoss;
	}
	
	/**
	 * Gets the total loss.
	 *
	 * @return the total loss
	 */
	public float getTotalLoss() {
		return totalLoss;
	}
	
	/**
	 * Sets the total loss.
	 *
	 * @param totalLoss the new total loss
	 */
	public void setTotalLoss(float totalLoss) {
		this.totalLoss = totalLoss;
	}
	
	/**
	 * Gets the avg interval rtt.
	 *
	 * @return the avg interval rtt
	 */
	public float getAvgIntervalRtt() {
		return avgIntervalRtt;
	}
	
	/**
	 * Sets the avg interval rtt.
	 *
	 * @param avgIntervalRtt the new avg interval rtt
	 */
	public void setAvgIntervalRtt(float avgIntervalRtt) {
		this.avgIntervalRtt = avgIntervalRtt;
	}
	
	/**
	 * Gets the avg interval jitter.
	 *
	 * @return the avg interval jitter
	 */
	public float getAvgIntervalJitter() {
		return avgIntervalJitter;
	}
	
	/**
	 * Sets the avg interval jitter.
	 *
	 * @param avgIntervalJitter the new avg interval jitter
	 */
	public void setAvgIntervalJitter(float avgIntervalJitter) {
		this.avgIntervalJitter = avgIntervalJitter;
	}	
}
