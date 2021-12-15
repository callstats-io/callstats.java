package io.callstats.sdk.messages;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.*;

import io.callstats.sdk.data.BridgeStatusInfo;

/**
 * The Class BridgeEventMessage.
 */
public class BridgeStatusUpdateMessage {

  /** The bridge id. */
  private String localID;

  /** The origin id. */
  private String originID;

  /** The device id. */
  private String deviceID;

  /** The timestamp. */
  private long timestamp;

  /** The cpu usage. */
  private float cpuUsage;

  /** Total used memory on the machine. */
  private float memoryUsage;

  /** The total memory of the machine. */
  private float totalMemory;

  /** The number of Java threads that the video bridge is using. */
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
  private long totalRtpLostPackets;

  /** avgIntervalRtt: The avg RTT observed in the last measurement interval. */
  private float intervalAverageRtt;

  /** avgIntervalJitter: The avg interval jitter in the last measurement interval. */
  private float intervalAverageJitter;

  /**
   * intervalDownloadBitRate: Download bit rate for the endpoints in kilobits per second measured
   * over the last measurement interval.
   */
  private int intervalDownloadBitrate;

  /**
   * intervalUploadBitRate: Upload bit rate for the video bridge in kilobits per second measured
   * over the last measurement interval.
   */
  private int intervalUploadBitrate;

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

  /** The logger. */
  private static final Logger logger = Logger.getLogger("CallStats");

  /**
   * Instantiates a new bridge event message.
   *
   * @param bridgeID the bridge id
   * @param timestamp the timestamp
   * @param bridgeStatusInfo the bridge status info
   * 
   */
  public BridgeStatusUpdateMessage(String bridgeID, long timestamp,
      BridgeStatusInfo bridgeStatusInfo) {
    super();
    try {
      this.localID = URLEncoder.encode(bridgeID, "UTF-8");
      this.setOriginID(this.localID);
      this.setDeviceID(this.localID);
    } catch (UnsupportedEncodingException e) {
      logger.log(Level.SEVERE, "UnsupportedEncodingException " + e.getMessage(), e);
      e.printStackTrace();
      throw new RuntimeException(e);
    }
    this.timestamp = timestamp;
    setData(bridgeStatusInfo);
  }

  /**
   * Sets the data.
   *
   * @param bridgeStatusInfo the new data
   */
  private void setData(BridgeStatusInfo bridgeStatusInfo) {
    setCpuUsage(bridgeStatusInfo.getCpuUsage());
    setMemoryUsage(bridgeStatusInfo.getMemoryUsage());
    threadCount = bridgeStatusInfo.getThreadCount();
    totalMemory = bridgeStatusInfo.getTotalMemory();

    intervalAverageJitter = bridgeStatusInfo.getAvgIntervalJitter();
    intervalAverageRtt = bridgeStatusInfo.getAvgIntervalRtt();
    totalRtpLostPackets = (long) bridgeStatusInfo.getTotalLoss();
    audioFabricCount = bridgeStatusInfo.getAudioFabricCount();
    videoFabricCount = bridgeStatusInfo.getVideoFabricCount();
    conferenceCount = bridgeStatusInfo.getConferenceCount();
    intervalDownloadBitrate = bridgeStatusInfo.getIntervalDownloadBitRate();
    intervalUploadBitrate = bridgeStatusInfo.getIntervalUploadBitRate();
    participantsCount = bridgeStatusInfo.getParticipantsCount();
    intervalReceivedBytes = bridgeStatusInfo.getIntervalReceivedBytes();
    intervalSentBytes = bridgeStatusInfo.getIntervalSentBytes();
    intervalRtpFractionLoss = bridgeStatusInfo.getIntervalRtpFractionLoss();
  }

  /**
   * Gets the bridge id.
   *
   * @return the bridge id
   */
  public String getLocalID() {
    return localID;
  }

  /**
   * Sets the bridge id.
   *
   * @param userID the new bridge id
   *
   */
  public void setLocalID(String userID) {
    try {
      this.localID = URLEncoder.encode(userID, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      logger.log(Level.SEVERE, "UnsupportedEncodingException " + e.getMessage(), e);
      e.printStackTrace();
      throw new RuntimeException(e);
    } ;
  }

  /**
   * Gets the timestamp.
   *
   * @return the timestamp
   */
  public long getTimestamp() {
    return timestamp;
  }

  /**
   * Sets the timestamp.
   *
   * @param timestamp the new timestamp
   */
  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public String getOriginID() {
    return originID;
  }

  public void setOriginID(String originID) {
    this.originID = originID;
  }

  public String getDeviceID() {
    return deviceID;
  }

  public void setDeviceID(String deviceID) {
    this.deviceID = deviceID;
  }

  public float getCpuUsage() {
    return cpuUsage;
  }

  public void setCpuUsage(float cpuUsage) {
    this.cpuUsage = cpuUsage;
  }

  public float getMemoryUsage() {
    return memoryUsage;
  }

  public void setMemoryUsage(float memoryUsage) {
    this.memoryUsage = memoryUsage;
  }

  public float getTotalMemory() {
    return totalMemory;
  }

  public void setTotalMemory(float totalMemory) {
    this.totalMemory = totalMemory;
  }

  public int getThreadCount() {
    return threadCount;
  }

  public void setThreadCount(int threadCount) {
    this.threadCount = threadCount;
  }

  public long getIntervalSentBytes() {
    return intervalSentBytes;
  }

  public void setIntervalSentBytes(long intervalSentBytes) {
    this.intervalSentBytes = intervalSentBytes;
  }

  public long getIntervalReceivedBytes() {
    return intervalReceivedBytes;
  }

  public void setIntervalReceivedBytes(long intervalReceivedBytes) {
    this.intervalReceivedBytes = intervalReceivedBytes;
  }

  public float getIntervalRtpFractionLoss() {
    return intervalRtpFractionLoss;
  }

  public void setIntervalRtpFractionLoss(float intervalRtpFractionLoss) {
    this.intervalRtpFractionLoss = intervalRtpFractionLoss;
  }

  public float getTotalRtpLostPackets() {
    return totalRtpLostPackets;
  }

  public void setTotalRtpLostPackets(long totalRtpLostPackets) {
    this.totalRtpLostPackets = totalRtpLostPackets;
  }

  public float getIntervalAverageRtt() {
    return intervalAverageRtt;
  }

  public void setIntervalAverageRtt(float intervalAverageRtt) {
    this.intervalAverageRtt = intervalAverageRtt;
  }

  public float getIntervalAverageJitter() {
    return intervalAverageJitter;
  }

  public void setIntervalAverageJitter(float intervalAverageJitter) {
    this.intervalAverageJitter = intervalAverageJitter;
  }

  public int getIntervalDownloadBitrate() {
    return intervalDownloadBitrate;
  }

  public void setIntervalDownloadBitrate(int intervalDownloadBitrate) {
    this.intervalDownloadBitrate = intervalDownloadBitrate;
  }

  public int getIntervalUploadBitrate() {
    return intervalUploadBitrate;
  }

  public void setIntervalUploadBitrate(int intervalUploadBitrate) {
    this.intervalUploadBitrate = intervalUploadBitrate;
  }

  public int getAudioFabricCount() {
    return audioFabricCount;
  }

  public void setAudioFabricCount(int audioFabricCount) {
    this.audioFabricCount = audioFabricCount;
  }

  public int getVideoFabricCount() {
    return videoFabricCount;
  }

  public void setVideoFabricCount(int videoFabricCount) {
    this.videoFabricCount = videoFabricCount;
  }

  public int getConferenceCount() {
    return conferenceCount;
  }

  public void setConferenceCount(int conferenceCount) {
    this.conferenceCount = conferenceCount;
  }

  public int getParticipantsCount() {
    return participantsCount;
  }

  public void setParticipantsCount(int participantsCount) {
    this.participantsCount = participantsCount;
  }
}
