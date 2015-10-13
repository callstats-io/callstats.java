package io.callstats.sdk;

/**
 * The Class BridgeStatusInfoBuilder.
 */
public class BridgeStatusInfoBuilder {
	
	/** measurementInterval: Interval since the last status measurement, measured in milliseconds.*/
	private int measurementInterval;
	
	/** cpuUsage: The cpu usage for the endpoint. The value is defined in https://github.com/jitsi/jitsi-videobridge/blob/master/doc/using_statistics.md */
	private float cpuUsage;

	/** memoryUsage: The memory usage for the endpoint. The value is defined in https://github.com/jitsi/jitsi-videobridge/blob/master/doc/using_statistics.md */
	private float memoryUsage;
	
	/** totalMemory: The total memory available to the endpoint. */
	private float totalMemory;
	
	/** threadCount: The number of Java threads that the bridge is using. This is optional, The value is defined in https://github.com/jitsi/jitsi-videobridge/blob/master/doc/using_statistics.md */
	private int threadCount;
	
	/** intervalSentBytes: The sent bytes in the last measurement interval. */
	private long intervalSentBytes;
	
	/** intervalReceivedBytes: The received bytes in the last measurement interval. */
	private long intervalReceivedBytes;
	
	/** intervalRtpFractionLoss: The packet loss observed in the last measurement interval. It is the fraction of the RTP packets lost, and the RTP packets lost and received in the last measurement interval. The value is between 0.0 and 1.0. */
	private float intervalRtpFractionLoss;
	
	/** totalLoss: The total cumulative packets lost. */
	private float totalLoss;
	
	/** avgIntervalRtt: The avg RTT observed in the last measurement interval. */
	private float avgIntervalRtt;
	
	/** avgIntervalJitter: The avg interval jitter in the last measurement interval. */
	private float avgIntervalJitter;
	
	/** intervalDownloadBitRate: Download bit rate for the endpoints in kilobits per second measured over the last measurement interval.*/
	private int intervalDownloadBitRate;
	
	/** intervalUploadBitRate: Upload bit rate for the video bridge in kilobits per second measured over the last measurement interval.*/
	private int intervalUploadBitRate;
	
	/** The cumulative fraction loss, measured as the ratio of the cumulative RTP packets lost, and cumulative sum of packets lost and received. The value is between 0.0 and 1.0. */
	// private float totalRtpFractionLoss;
	
	/** audioFabricCount: Current number of open/active 5 tuples that use audio. */
	private int audioFabricCount;
	
	/** videoFabricCount: Current number of open/active 5-tuples that use video. */
	private int videoFabricCount;
	
	/** conferenceCount: Current number of multimedia conferences. */
	private int conferenceCount;
	
	/** participantsCount: Current number of multimedia participants cumulative over all ongoing conferences. */
	private int participantsCount;
	
	/**
	 * Gets the measurement interval.
	 *
	 * @return the measurement interval
	 */
	public int getMeasurementInterval() {
		return measurementInterval;
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
	 * Gets the memory usage.
	 *
	 * @return the memory usage
	 */
	public float getMemoryUsage() {
		return memoryUsage;
	}

	/**
	 * Gets the total memory.
	 *
	 * @return the total memory
	 */
	public float getTotalMemory() {
		return totalMemory;
	}

	/**
	 * Gets the thread count.
	 *
	 * @return the thread count
	 */
	public int getThreadCount() {
		return threadCount;
	}

	/**
	 * Gets the interval sent bytes.
	 *
	 * @return the interval sent bytes
	 */
	public long getIntervalSentBytes() {
		return intervalSentBytes;
	}

	/**
	 * Gets the interval received bytes.
	 *
	 * @return the interval received bytes
	 */
	public long getIntervalReceivedBytes() {
		return intervalReceivedBytes;
	}

	/**
	 * Gets the interval rtp fraction loss.
	 *
	 * @return the interval rtp fraction loss
	 */
	public float getIntervalRtpFractionLoss() {
		return intervalRtpFractionLoss;
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
	 * Gets the avg interval rtt.
	 *
	 * @return the avg interval rtt
	 */
	public float getAvgIntervalRtt() {
		return avgIntervalRtt;
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
	 * Gets the interval download bit rate.
	 *
	 * @return the interval download bit rate
	 */
	public int getIntervalDownloadBitRate() {
		return intervalDownloadBitRate;
	}

	/**
	 * Gets the interval upload bit rate.
	 *
	 * @return the interval upload bit rate
	 */
	public int getIntervalUploadBitRate() {
		return intervalUploadBitRate;
	}


	/**
	 * Gets the audio fabric count.
	 *
	 * @return the audio fabric count
	 */
	public int getAudioFabricCount() {
		return audioFabricCount;
	}

	/**
	 * Gets the video fabric count.
	 *
	 * @return the video fabric count
	 */
	public int getVideoFabricCount() {
		return videoFabricCount;
	}

	/**
	 * Gets the conference count.
	 *
	 * @return the conference count
	 */
	public int getConferenceCount() {
		return conferenceCount;
	}

	/**
	 * Gets the participants count.
	 *
	 * @return the participants count
	 */
	public int getParticipantsCount() {
		return participantsCount;
	}

	
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
	 * Total memory.
	 *
	 * @param totalMemory the total memory
	 * @return the bridge status info builder
	 */
	public BridgeStatusInfoBuilder totalMemory(float totalMemory) {
		this.totalMemory = totalMemory;
		return this;
	}
	
	/**
	 * Thread count.
	 *
	 * @param threadCount the thread count
	 * @return the bridge status info builder
	 */
	public BridgeStatusInfoBuilder threadCount(int threadCount) {
		this.threadCount = threadCount;
		return this;
	}
	
	
	/**
	 * Sent bytes.
	 *
	 * @param intervalSentBytes the interval sent bytes
	 * @return the bridge status info builder
	 */
	public BridgeStatusInfoBuilder intervalSentBytes(long intervalSentBytes) {
		this.intervalSentBytes = intervalSentBytes;
		return this;
	}
	
	/**
	 * Received bytes.
	 *
	 * @param intervalReceivedBytes the interval received bytes
	 * @return the bridge status info builder
	 */
	public BridgeStatusInfoBuilder intervalReceivedBytes(long intervalReceivedBytes) {
		this.intervalReceivedBytes = intervalReceivedBytes;
		return this;
	}
	
	/**
	 * Interval loss.
	 *
	 * @param intervalRtpFractionLoss the interval rtp fraction loss
	 * @return the bridge status info builder
	 */
	public BridgeStatusInfoBuilder intervalRtpFractionLoss(float intervalRtpFractionLoss) {
		this.intervalRtpFractionLoss = intervalRtpFractionLoss;
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
	 * Interval download bit rate.
	 *
	 * @param intervalDownloadBitRate the interval download bit rate
	 * @return the bridge status info builder
	 */
	public BridgeStatusInfoBuilder intervalDownloadBitRate(int intervalDownloadBitRate) {
		this.intervalDownloadBitRate = intervalDownloadBitRate;
		return this;
	}
	
	/**
	 * Interval upload bit rate.
	 *
	 * @param intervalUploadBitRate the interval upload bit rate
	 * @return the bridge status info builder
	 */
	public BridgeStatusInfoBuilder intervalUploadBitRate(int intervalUploadBitRate) {
		this.intervalUploadBitRate = intervalUploadBitRate;
		return this;
	}
		
	/**
	 * Audio fabric count.
	 *
	 * @param audioFabricCount the audio fabric count
	 * @return the bridge status info builder
	 */
	public BridgeStatusInfoBuilder audioFabricCount(int audioFabricCount) {
		this.audioFabricCount = audioFabricCount;
		return this;
	}
	
	/**
	 * Video fabric count.
	 *
	 * @param videoFabricCount the video fabric count
	 * @return the bridge status info builder
	 */
	public BridgeStatusInfoBuilder videoFabricCount(int videoFabricCount) {
		this.videoFabricCount = videoFabricCount;
		return this;
	}
	
	/**
	 * Conference count.
	 *
	 * @param conferenceCount the conference count
	 * @return the bridge status info builder
	 */
	public BridgeStatusInfoBuilder conferenceCount(int conferenceCount) {
		this.conferenceCount = conferenceCount;
		return this;
	}
	
	/**
	 * Participants count.
	 *
	 * @param participantsCount the participants count
	 * @return the bridge status info builder
	 */
	public BridgeStatusInfoBuilder participantsCount(int participantsCount) {
		this.participantsCount = participantsCount;
		return this;
	}
	
	/**
	 * Measurement interval.
	 *
	 * @param measurementInterval the measurement interval
	 * @return the bridge status info builder
	 */
	public BridgeStatusInfoBuilder measurementInterval(int measurementInterval) {
		this.measurementInterval = measurementInterval;
		return this;
	}
	
	/**
	 * Builds the.
	 *
	 * @return the bridge status info
	 */
	public BridgeStatusInfo build() {
		return new BridgeStatusInfo(this);
	}		
}
