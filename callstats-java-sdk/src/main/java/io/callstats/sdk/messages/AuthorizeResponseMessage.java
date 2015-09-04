package io.callstats.sdk.messages;

/**
*
* @author Karthik Budigere
*/
public class AuthorizeResponseMessage {
	int appID;
	String userID;
	String version;
	String endpointType;
	AuthorizeResponseBody authorize;
	
	public AuthorizeResponseMessage() {
		authorize = new AuthorizeResponseBody();
	}

	class AuthorizeResponseBody {
		String status;
		String challenge;
		String reason;
		String description;
		
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getChallenge() {
			return challenge;
		}
		public void setChallenge(String challenge) {
			this.challenge = challenge;
		}
		public String getReason() {
			return reason;
		}
		public void setReason(String reason) {
			this.reason = reason;
		}
	}
	
	
	public String getDescription() {
		return authorize.getDescription();
	}
	public void setDescription(String description) {
		authorize.setDescription(description);
	}
		
	public String getStatus() {
		return authorize.getStatus();
	}
	
	public String getChallenge() {
		return authorize.getChallenge();
	}
	
	public String getReason() {
		return authorize.getReason();
	}
	
	public int getAppID() {
		return appID;
	}

	public void setStatus(String status) {
		authorize.setStatus(status);
	}
	
	public void setChallenge(String challenge) {
		authorize.setChallenge(challenge);
	}
	
	public void setReason(String reason) {
		authorize.setReason(reason);
	}
	
	public void setAppID(int appID) {
		this.appID = appID;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getEndpointType() {
		return endpointType;
	}

	public void setEndpointType(String endpointType) {
		this.endpointType = endpointType;
	}	
}
