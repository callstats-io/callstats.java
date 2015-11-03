package io.callstats.sdk;

public enum CallStatsConferenceEvents {
	CONFERENCE_SETUP(0, "conferenceSetup"), 
	CONFERENCE_SETUP_FAILED(1, "conferenceSetupFailed"), 
	CONFERENCE_TERMINATED(2, "conferenceTerminated"),
	USER_JOINED(3, "userJoined"),
	USER_LEFT(4, "userLeft"),
	AUDIO_MUTE(5, "audioMute"),
	AUDIO_UNMUTE(6, "audioUnmute"),
	VIDEO_PAUSE(7, "videoPause"),
	VIDEO_RESUME(8, "videoResume"),
	CONFERENCE_STATS(9, "conferenceStats");

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
