package io.callstats.sdk;

/**
 * The Class BridgeStatusInfoBuilder.
 */
public class BridgeStatusInfoBuilder {
	
	/** measurementInterval: Interval since the last status measurement, measured in milliseconds.*/
	private int measurementInterval;
	
	/** cpuUsage: The cpu usage for the endpoint. The value is defined in https://github.com/jitsi/jitsi-videobridge/blob/master/doc/using_statistics.md */
	private float cpuUsage;

	/** memoryUage: The memory usage for the endpoint. The value is defined in https://github.com/jitsi/jitsi-videobridge/blob/master/doc/using_statistics.md */
	private float memoryUsage;
	
	/** totalMemory: The total memory available to the endpoint. */
	private float totalMemory;
	
	/** threadCount: The number of Java threads that the bridge is using. This is optional, The value is defined in https://github.com/jitsi/jitsi-videobridge/blob/master/doc/using_statistics.md */
	private int threadCount;
	
	/** sentBytes: The cumulative sent bytes. */
	private long sentBytes;
	
	/** receivedBytes: The cumulative received bytes. */
	private long receivedBytes;
	
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
	
	public int getMeasurementInterval() {
		return measurementInterval;
	}

	public float getCpuUsage() {
		return cpuUsage;
	}

	public float getMemoryUsage() {
		return memoryUsage;
	}

	public float getTotalMemory() {
		return totalMemory;
	}

	public int getThreadCount() {
		return threadCount;
	}

	public long getSentBytes() {
		return sentBytes;
	}

	public long getReceivedBytes() {
		return receivedBytes;
	}

	public float getIntervalLoss() {
		return intervalLoss;
	}

	public float getTotalLoss() {
		return totalLoss;
	}

	public float getAvgIntervalRtt() {
		return avgIntervalRtt;
	}

	public float getAvgIntervalJitter() {
		return avgIntervalJitter;
	}

	public int getDownloadBitRate() {
		return downloadBitRate;
	}

	public int getUploadBitRate() {
		return uploadBitRate;
	}

	public float getRtpLoss() {
		return rtpLoss;
	}

	public int getAudioChannelsCount() {
		return audioChannelsCount;
	}

	public int getVideoChannelsCount() {
		return videoChannelsCount;
	}

	public int getConferenceCount() {
		return conferenceCount;
	}

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
	
	public BridgeStatusInfoBuilder totalMemory(float totalMemory) {
		this.totalMemory = totalMemory;
		return this;
	}
	
	public BridgeStatusInfoBuilder threadCount(int threadCount) {
		this.threadCount = threadCount;
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
	
	public BridgeStatusInfoBuilder downloadBitRate(int downloadBitRate) {
		this.downloadBitRate = downloadBitRate;
		return this;
	}
	
	public BridgeStatusInfoBuilder uploadBitRate(int uploadBitRate) {
		this.uploadBitRate = uploadBitRate;
		return this;
	}
	
	public BridgeStatusInfoBuilder rtpLoss(float rtpLoss) {
		this.rtpLoss = rtpLoss;
		return this;
	}
	
	public BridgeStatusInfoBuilder audioChannelsCount(int audioChannelsCount) {
		this.audioChannelsCount = audioChannelsCount;
		return this;
	}
	
	public BridgeStatusInfoBuilder videoChannelsCount(int videoChannelsCount) {
		this.videoChannelsCount = videoChannelsCount;
		return this;
	}
	
	public BridgeStatusInfoBuilder conferenceCount(int conferenceCount) {
		this.conferenceCount = conferenceCount;
		return this;
	}
	
	public BridgeStatusInfoBuilder participantsCount(int participantsCount) {
		this.participantsCount = participantsCount;
		return this;
	}
	
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
