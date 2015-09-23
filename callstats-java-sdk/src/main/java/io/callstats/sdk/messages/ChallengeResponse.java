package io.callstats.sdk.messages;

/**
*
* @author Karthik Budigere
*/
public class ChallengeResponse {
	private int appID;
	private String userID;
	private String version;
	private String endpointType;
	private ChallengeResponseBody challenge;

	class ChallengeResponseBody {
		String status;
		String token;
		String expires;
		String reason;
		String description;
		
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getToken() {
			return token;
		}
		public void setToken(String token) {
			this.token = token;
		}
		public String getExpires() {
			return expires;
		}
		public void setExpires(String expires) {
			this.expires = expires;
		}
		public String getReason() {
			return reason;
		}
		public void setReason(String reason) {
			this.reason = reason;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}		
	}
	
	public ChallengeResponse() {
		challenge = new ChallengeResponseBody();
	}
	
	public int getAppID() {
		return appID;
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
	
	public String getStatus() {
		return challenge.getStatus();
	}
	public void setStatus(String status) {
		challenge.setStatus(status);
	}
	public String getToken() {
		return challenge.getToken();
	}
	public void setToken(String token) {
		challenge.setToken(token);
	}
	public String getExpires() {
		return challenge.getExpires();
	}
	public void setExpires(String expires) {
		challenge.setExpires(expires);
	}
	public String getReason() {
		return challenge.getReason();
	}
	public void setReason(String reason) {
		challenge.setReason(reason);
	}
	public String getDescription() {
		return challenge.getDescription();
	}
	public void setDescription(String description) {
		challenge.setDescription(description);
	}		
}
