package io.callstats.sdk;

/**
 * The Class BridgeStatusInfoBuilder.
 */
public class BridgeStatusInfoBuilder {
	
	/** The cpu usage. */
	private float cpuUsage;

	/** The memory usage. */
	private float memoryUsage;
	
	/** The total memory of the machine. */
	private float totalMemory;
	
	/** The number of Java threads that the video bridge is using. */
	private int threadCount;
	
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
	
	/** Download bit rate for the video bridge in kilobits per second.*/
	private int downloadBitRate;
	
	/** Upload bit rate for the video bridge in kilobits per second.*/
	private int uploadBitRate;
	
	/** The value is between 0 and 1 and represents the RTP packet loss for the video bridge. */
	private float rtpLoss;
	
	/** Number of audio channels. */
	private int audioChannelsCount;
	
	/** Number of video channels. */
	private int videoChannelsCount;
	
	/** Number of video conferences. */
	private int conferenceCount;
	
	/** Number of video participants. */
	private int participantsCount;
	
	/** Interval from the last status submission*/
	private int measurementInterval;
	
	
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
