package io.callstats.sdk;


/**
 * The Class TrafficStatusData.
 */
public class TrafficStatusData {
	
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
	 * Instantiates a new traffic status data.
	 */
	public TrafficStatusData() {
		
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