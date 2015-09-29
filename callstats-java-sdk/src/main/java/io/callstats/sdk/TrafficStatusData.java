package io.callstats.sdk;

/**
 * The Class TrafficStatusData.
 */
public class TrafficStatusData {
	
	/** The sent bytes. */
	long sentBytes;
	
	/** The received bytes. */
	long receivedBytes;
	
	/** The interval loss. */
	float intervalLoss;
	
	/** The total loss. */
	float totalLoss;
	
	/** The avg interval rtt. */
	float avgIntervalRtt;
	
	/** The avg interval jitter. */
	float avgIntervalJitter;
	
	/**
	 * Instantiates a new traffic status data.
	 */
	public TrafficStatusData() {
		
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