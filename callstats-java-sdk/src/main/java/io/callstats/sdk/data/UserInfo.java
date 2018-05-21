package io.callstats.sdk.data;

public class UserInfo {

	private String confID;
	private String userID;
	private String ucID;
	private String connectionID;

	public String getConnectionID() {
		return connectionID;
	}
	public void setConnectionID(String connectionID) {
		this.connectionID = connectionID;
	}
	public String getConfID() {
		return confID;
	}
	public void setConfID(String confID) {
		this.confID = confID;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getUcID() {
		return ucID;
	}
	public void setUcID(String ucID) {
		this.ucID = ucID;
	}

	public UserInfo(String confID, String userID, String ucID) {
		super();
		this.confID = confID;
		this.userID = userID;
		this.ucID = ucID;
		this.connectionID = ucID;
	}

	public UserInfo(String confID, String userID, String ucID, String connectionID) {
		super();
		this.confID = confID;
		this.userID = userID;
		this.ucID = ucID;
		this.connectionID = connectionID;
	}

}
