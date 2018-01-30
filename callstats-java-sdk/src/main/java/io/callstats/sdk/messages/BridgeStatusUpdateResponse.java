package io.callstats.sdk.messages;

/**
 * The Class BridgeEventResponse.
 */
public class BridgeStatusUpdateResponse {

	/** The user id. */
	private String status;

	/** The version. */
	private String msg;

	/**
	 * Instantiates a new bridge keep alive response.
	 */
	public BridgeStatusUpdateResponse() {

	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
