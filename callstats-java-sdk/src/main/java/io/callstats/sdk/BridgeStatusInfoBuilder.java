package io.callstats.sdk;

/**
 * The Class BridgeStatusInfoBuilder.
 */
public class BridgeStatusInfoBuilder {
	
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
	 * Instantiates a new bridge status info builder.
	 */
	public BridgeStatusInfoBuilder() {
		
	}
	
	/**
	 * Cpu usage.
	 *
	 * @param cpuUsage the cpu usage
	 * @return the bridge status info builder
	 */
	public BridgeStatusInfoBuilder cpuUsage(float cpuUsage) {
		this.cpuUsage = cpuUsage;
		return this;
	}
	
	/**
	 * Memory usage.
	 *
	 * @param memoryUsage the memory usage
	 * @return the bridge status info builder
	 */
	public BridgeStatusInfoBuilder memoryUsage(float memoryUsage) {
		this.memoryUsage = memoryUsage;
		return this;
	}
	
	/**
	 * Sent bytes.
	 *
	 * @param sentBytes the sent bytes
	 * @return the bridge status info builder
	 */
	public BridgeStatusInfoBuilder sentBytes(long sentBytes) {
		this.sentBytes = sentBytes;
		return this;
	}
	
	/**
	 * Received bytes.
	 *
	 * @param receivedBytes the received bytes
	 * @return the bridge status info builder
	 */
	public BridgeStatusInfoBuilder receivedBytes(long receivedBytes) {
		this.receivedBytes = receivedBytes;
		return this;
	}
	
	/**
	 * Interval loss.
	 *
	 * @param intervalLoss the interval loss
	 * @return the bridge status info builder
	 */
	public BridgeStatusInfoBuilder intervalLoss(float intervalLoss) {
		this.intervalLoss = intervalLoss;
		return this;
	}
	
	/**
	 * Total loss.
	 *
	 * @param totalLoss the total loss
	 * @return the bridge status info builder
	 */
	public BridgeStatusInfoBuilder totalLoss(float totalLoss) {
		this.totalLoss = totalLoss;
		return this;
	}
	
	/**
	 * Avg interval rtt.
	 *
	 * @param avgIntervalRtt the avg interval rtt
	 * @return the bridge status info builder
	 */
	public BridgeStatusInfoBuilder avgIntervalRtt(float avgIntervalRtt) {
		this.avgIntervalRtt = avgIntervalRtt;
		return this;
	}
	
	/**
	 * Avg interval jitter.
	 *
	 * @param avgIntervalJitter the avg interval jitter
	 * @return the bridge status info builder
	 */
	public BridgeStatusInfoBuilder avgIntervalJitter(float avgIntervalJitter) {
		this.avgIntervalJitter = avgIntervalJitter;
		return this;
	}
	
	/**
	 * Builds the.
	 *
	 * @return the bridge status info
	 */
	public BridgeStatusInfo build() {
		return new BridgeStatusInfo(cpuUsage, memoryUsage, sentBytes, receivedBytes, intervalLoss, totalLoss, avgIntervalRtt, avgIntervalJitter);
	}		
}
