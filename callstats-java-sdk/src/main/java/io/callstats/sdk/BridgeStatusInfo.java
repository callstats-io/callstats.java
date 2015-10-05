package io.callstats.sdk;

/**
 * The Class BridgeStatusInfo.
 */
public class BridgeStatusInfo {
	
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
	
	/**
	 * Instantiates a new bridge status info.
	 *
	 * @param cpuUsage the cpu usage
	 * @param memoryUsage the memory usage
	 * @param totalMemory the total memory
	 * @param threadCount the thread count
	 * @param sentBytes the sent bytes
	 * @param receivedBytes the received bytes
	 * @param intervalLoss the interval loss
	 * @param totalLoss the total loss
	 * @param avgIntervalRtt the avg interval rtt
	 * @param avgIntervalJitter the avg interval jitter
	 * @param downloadBitRate the download bit rate
	 * @param uploadBitRate the upload bit rate
	 * @param rtpLoss the rtp loss
	 * @param audioChannelsCount the audio channels count
	 * @param videoChannelsCount the video channels count
	 * @param conferenceCount the conference count
	 * @param participantsCount the participants count
	 */
	public BridgeStatusInfo(float cpuUsage, float memoryUsage,
			float totalMemory, int threadCount, long sentBytes,
			long receivedBytes, float intervalLoss, float totalLoss,
			float avgIntervalRtt, float avgIntervalJitter, int downloadBitRate,
			int uploadBitRate, float rtpLoss, int audioChannelsCount,
			int videoChannelsCount, int conferenceCount, int participantsCount) {
		super();
		this.cpuUsage = cpuUsage;
		this.memoryUsage = memoryUsage;
		this.totalMemory = totalMemory;
		this.threadCount = threadCount;
		this.sentBytes = sentBytes;
		this.receivedBytes = receivedBytes;
		this.intervalLoss = intervalLoss;
		this.totalLoss = totalLoss;
		this.avgIntervalRtt = avgIntervalRtt;
		this.avgIntervalJitter = avgIntervalJitter;
		this.downloadBitRate = downloadBitRate;
		this.uploadBitRate = uploadBitRate;
		this.rtpLoss = rtpLoss;
		this.audioChannelsCount = audioChannelsCount;
		this.videoChannelsCount = videoChannelsCount;
		this.conferenceCount = conferenceCount;
		this.participantsCount = participantsCount;
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
	
	/**
	 * Gets the total memory.
	 *
	 * @return the total memory
	 */
	public float getTotalMemory() {
		return totalMemory;
	}

	/**
	 * Sets the total memory.
	 *
	 * @param totalMemory the new total memory
	 */
	public void setTotalMemory(float totalMemory) {
		this.totalMemory = totalMemory;
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
	 * Sets the thread count.
	 *
	 * @param threadCount the new thread count
	 */
	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}

	/**
	 * Gets the download bit rate.
	 *
	 * @return the download bit rate
	 */
	public int getDownloadBitRate() {
		return downloadBitRate;
	}

	/**
	 * Sets the download bit rate.
	 *
	 * @param downloadBitRate the new download bit rate
	 */
	public void setDownloadBitRate(int downloadBitRate) {
		this.downloadBitRate = downloadBitRate;
	}

	/**
	 * Gets the upload bit rate.
	 *
	 * @return the upload bit rate
	 */
	public int getUploadBitRate() {
		return uploadBitRate;
	}

	/**
	 * Sets the upload bit rate.
	 *
	 * @param uploadBitRate the new upload bit rate
	 */
	public void setUploadBitRate(int uploadBitRate) {
		this.uploadBitRate = uploadBitRate;
	}

	/**
	 * Gets the rtp loss.
	 *
	 * @return the rtp loss
	 */
	public float getRtpLoss() {
		return rtpLoss;
	}

	/**
	 * Sets the rtp loss.
	 *
	 * @param rtpLoss the new rtp loss
	 */
	public void setRtpLoss(float rtpLoss) {
		this.rtpLoss = rtpLoss;
	}

	/**
	 * Gets the audio channels count.
	 *
	 * @return the audio channels count
	 */
	public int getAudioChannelsCount() {
		return audioChannelsCount;
	}

	/**
	 * Sets the audio channels count.
	 *
	 * @param audioChannelsCount the new audio channels count
	 */
	public void setAudioChannelsCount(int audioChannelsCount) {
		this.audioChannelsCount = audioChannelsCount;
	}

	/**
	 * Gets the video channels count.
	 *
	 * @return the video channels count
	 */
	public int getVideoChannelsCount() {
		return videoChannelsCount;
	}

	/**
	 * Sets the video channels count.
	 *
	 * @param videoChannelsCount the new video channels count
	 */
	public void setVideoChannelsCount(int videoChannelsCount) {
		this.videoChannelsCount = videoChannelsCount;
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
	 * Sets the conference count.
	 *
	 * @param conferenceCount the new conference count
	 */
	public void setConferenceCount(int conferenceCount) {
		this.conferenceCount = conferenceCount;
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
	 * Sets the participants count.
	 *
	 * @param participantsCount the new participants count
	 */
	public void setParticipantsCount(int participantsCount) {
		this.participantsCount = participantsCount;
	}
}
