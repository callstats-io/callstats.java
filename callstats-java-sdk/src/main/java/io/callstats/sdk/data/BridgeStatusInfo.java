package io.callstats.sdk.data;


/**
 * The Class BridgeStatusInfo.
 */
public class BridgeStatusInfo {

  /** measurementInterval: Interval since the last status measurement, measured in milliseconds. */
  private int measurementInterval;

  /**
   * cpuUsage: The cpu usage for the endpoint. The value is defined in
   * https://github.com/jitsi/jitsi-videobridge/blob/master/doc/using_statistics.md
   */
  private float cpuUsage;

  /**
   * memoryUsage: The memory usage for the endpoint. The value is defined in
   * https://github.com/jitsi/jitsi-videobridge/blob/master/doc/using_statistics.md
   */
  private float memoryUsage;

  /** totalMemory: The total memory available to the endpoint. */
  private float totalMemory;

  /**
   * threadCount: The number of Java threads that the bridge is using. This is optional, The value
   * is defined in https://github.com/jitsi/jitsi-videobridge/blob/master/doc/using_statistics.md
   */
  private int threadCount;

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
   * Instantiates a new bridge status info.
   *
   * @param builder the builder
   */
  public BridgeStatusInfo(BridgeStatusInfoBuilder builder) {
    super();
    this.cpuUsage = builder.getCpuUsage();
    this.memoryUsage = builder.getMemoryUsage();
    this.totalMemory = builder.getTotalMemory();
    this.threadCount = builder.getThreadCount();
    this.intervalSentBytes = builder.getIntervalSentBytes();
    this.intervalReceivedBytes = builder.getIntervalReceivedBytes();
    this.intervalRtpFractionLoss = builder.getIntervalRtpFractionLoss();
    this.totalLoss = builder.getTotalLoss();
    this.avgIntervalRtt = builder.getAvgIntervalRtt();
    this.avgIntervalJitter = builder.getAvgIntervalJitter();
    this.intervalDownloadBitRate = builder.getIntervalDownloadBitRate();
    this.intervalUploadBitRate = builder.getIntervalUploadBitRate();
    this.audioFabricCount = builder.getAudioFabricCount();
    this.videoFabricCount = builder.getVideoFabricCount();
    this.conferenceCount = builder.getConferenceCount();
    this.participantsCount = builder.getParticipantsCount();
    this.measurementInterval = builder.getMeasurementInterval();
  }


  /**
   * Gets the measurement interval.
   *
   * @return the measurement interval
   */
  public int getMeasurementInterval() {
    return measurementInterval;
  }

  /**
   * Sets the measurement interval.
   *
   * @param measurementInterval the new measurement interval
   */
  public void setMeasurementInterval(int measurementInterval) {
    this.measurementInterval = measurementInterval;
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
  public long getIntervalSentBytes() {
    return intervalSentBytes;
  }

  /**
   * Sets the sent bytes.
   *
   * @param intervalSentBytes the new interval sent bytes
   */
  public void setIntervalSentBytes(long intervalSentBytes) {
    this.intervalSentBytes = intervalSentBytes;
  }

  /**
   * Gets the received bytes.
   *
   * @return the received bytes
   */
  public long getIntervalReceivedBytes() {
    return intervalReceivedBytes;
  }

  /**
   * Sets the received bytes.
   *
   * @param intervalReceivedBytes the new received bytes
   */
  public void setReceivedBytes(long intervalReceivedBytes) {
    this.intervalReceivedBytes = intervalReceivedBytes;
  }

  /**
   * Gets the interval loss.
   *
   * @return the interval loss
   */
  public float getIntervalRtpFractionLoss() {
    return intervalRtpFractionLoss;
  }

  /**
   * Sets the interval loss.
   *
   * @param intervalRtpFractionLoss the new interval rtp fraction loss
   */
  public void setIntervalRtpFractionLoss(float intervalRtpFractionLoss) {
    this.intervalRtpFractionLoss = intervalRtpFractionLoss;
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
  public int getIntervalDownloadBitRate() {
    return intervalDownloadBitRate;
  }

  /**
   * Sets the download bit rate.
   *
   * @param intervalDownloadBitRate the new interval download bit rate
   */
  public void setIntervalDownloadBitRate(int intervalDownloadBitRate) {
    this.intervalDownloadBitRate = intervalDownloadBitRate;
  }

  /**
   * Gets the upload bit rate.
   *
   * @return the upload bit rate
   */
  public int getIntervalUploadBitRate() {
    return intervalUploadBitRate;
  }

  /**
   * Sets the upload bit rate.
   *
   * @param intervalUploadBitRate the new interval upload bit rate
   */
  public void setIntervalUploadBitRate(int intervalUploadBitRate) {
    this.intervalUploadBitRate = intervalUploadBitRate;
  }


  /**
   * Gets the audio channels count.
   *
   * @return the audio channels count
   */
  public int getAudioFabricCount() {
    return audioFabricCount;
  }

  /**
   * Sets the audio channels count.
   *
   * @param audioFabricCount the new audio fabric count
   */
  public void setAudioFabricCount(int audioFabricCount) {
    this.audioFabricCount = audioFabricCount;
  }

  /**
   * Gets the video channels count.
   *
   * @return the video channels count
   */
  public int getVideoFabricCount() {
    return videoFabricCount;
  }

  /**
   * Sets the video channels count.
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

}
