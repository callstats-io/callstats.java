package io.callstats.sdk.messages;

public class CallStatsEventResponse {

	private String status;
	private String msg;
	private String ucID;

	public CallStatsEventResponse() {

	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public String getUcID() {
		return ucID;
	}
	public void setUcID(String ucID) {
		this.ucID = ucID;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
