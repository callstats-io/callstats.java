package io.callstats.sdk.data;


/**
 * The Class TrafficStatusData.
 */
public class TrafficStatusData {

  /** intervalSentBytes: The sent bytes in the last measurement interval. */
  private long intervalSentBytes;

  /** intervalReceivedBytes: The received bytes in the last measurement interval. */
  private long intervalReceivedBytes;

  /**
   * intervalRtpFractionLoss: The packet loss observed in the last measurement interval. It is the
   * fraction of the RTP packets lost, and the RTP packets lost and received in the last measurement
   * interval. The value is between 0.0 and 1.0.
   */
  private float intervalRtpFractionLoss;

  /** totalLoss: The total cumulative packets lost. */
  private float totalLoss;

  /** avgIntervalRtt: The avg RTT observed in the last measurement interval. */
  private float avgIntervalRtt;

  /** avgIntervalJitter: The avg interval jitter in the last measurement interval. */
  private float avgIntervalJitter;

  /**
   * intervalDownloadBitRate: Download bit rate for the endpoints in kilobits per second measured
   * over the last measurement interval.
   */
  private int intervalDownloadBitRate;

  /**
   * intervalUploadBitRate: Upload bit rate for the video bridge in kilobits per second measured
   * over the last measurement interval.
   */
  private int intervalUploadBitRate;

  /**
   * The cumulative fraction loss, measured as the ratio of the cumulative RTP packets lost, and
   * cumulative sum of packets lost and received. The value is between 0.0 and 1.0.
   */
  // private float totalRtpFractionLoss;

  /** audioFabricCount: Current number of open/active 5 tuples that use audio. */
  private int audioFabricCount;

  /** videoFabricCount: Current number of open/active 5-tuples that use video. */
  private int videoFabricCount;

  /** conferenceCount: Current number of multimedia conferences. */
  private int conferenceCount;

  /**
   * participantsCount: Current number of multimedia participants cumulative over all ongoing
   * conferences.
   */
  private int participantsCount;


  /**
   * Instantiates a new traffic status data.
   */
  public TrafficStatusData() {

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
   * Sets the interval sent bytes.
   *
   * @param intervalSentBytes the new interval sent bytes
   */
  public void setIntervalSentBytes(long intervalSentBytes) {
    this.intervalSentBytes = intervalSentBytes;
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
   * Sets the interval received bytes.
   *
   * @param intervalReceivedBytes the new interval received bytes
   */
  public void setIntervalReceivedBytes(long intervalReceivedBytes) {
    this.intervalReceivedBytes = intervalReceivedBytes;
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
   * Sets the interval rtp fraction loss.
   *
   * @param intervalRtpFractionLoss the new interval rtp fraction loss
   */
  public void setIntervalRtpFractionLoss(float intervalRtpFractionLoss) {
    this.intervalRtpFractionLoss = intervalRtpFractionLoss;
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
   * Sets the interval download bit rate.
   *
   * @param intervalDownloadBitRate the new interval download bit rate
   */
  public void setIntervalDownloadBitRate(int intervalDownloadBitRate) {
    this.intervalDownloadBitRate = intervalDownloadBitRate;
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
   * Sets the interval upload bit rate.
   *
   * @param intervalUploadBitRate the new interval upload bit rate
   */
  public void setIntervalUploadBitRate(int intervalUploadBitRate) {
    this.intervalUploadBitRate = intervalUploadBitRate;
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
   * Sets the audio fabric count.
   *
   * @param audioFabricCount the new audio fabric count
   */
  public void setAudioFabricCount(int audioFabricCount) {
    this.audioFabricCount = audioFabricCount;
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
   * Sets the video fabric count.
   *
   * @param videoFabricCount the new video fabric count
   */
  public void setVideoFabricCount(int videoFabricCount) {
    this.videoFabricCount = videoFabricCount;
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
