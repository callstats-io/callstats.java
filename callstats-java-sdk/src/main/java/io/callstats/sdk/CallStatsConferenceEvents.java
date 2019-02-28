package io.callstats.sdk;

/**
 * The Enum CallStatsConferenceEvents.
 *
 * @author Karthik Budigere
 */
public enum CallStatsConferenceEvents {
  /** The conferenceSetup. */
  CONFERENCE_SETUP(0, "conferenceSetup"),

  /** The conferenceSetupFailed. */
  CONFERENCE_SETUP_FAILED(1, "conferenceSetupFailed"),

  /** The conferenceTerminated. */
  CONFERENCE_TERMINATED(2, "conferenceTerminated"),

  /** The userJoined. */
  USER_JOINED(3, "userJoined"),

  /** The userLeft. */
  USER_LEFT(4, "userLeft"),

  /** The fabricHold. */
  FABRIC_HOLD(5, "fabricHold"),

  /** The fabricResume. */
  FABRIC_RESUME(6, "fabricResume"),

  /** The audioMute. */
  AUDIO_MUTE(7, "audioMute"),

  /** The audioUnmute. */
  AUDIO_UNMUTE(8, "audioUnmute"),

  /** The videoPause. */
  VIDEO_PAUSE(9, "videoPause"),

  /** The videoResume. */
  VIDEO_RESUME(10, "videoResume"),

  /** The conferenceStats. */
  CONFERENCE_STATS(11, "conferenceStats");

  private final int msgCode;
  private final String msgType;

  private CallStatsConferenceEvents(int code, String msgType) {
    this.msgCode = code;
    this.msgType = msgType;
  }

  public String getMessageType() {
    return msgType;
  }

  public int getMessageCode() {
    return msgCode;
  }

  @Override
  public String toString() {
    return msgCode + ": " + msgType;
  }
}
