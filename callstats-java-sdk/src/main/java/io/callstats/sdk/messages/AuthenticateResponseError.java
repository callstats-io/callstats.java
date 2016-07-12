package io.callstats.sdk.messages;

import java.util.ArrayList;
import java.util.Collection;

import com.google.gson.annotations.SerializedName;

public class AuthenticateResponseError {
	/**
	 * Error code
	 */
	@SerializedName("error")
	private String error;
	
	/**
	 * Error description
	 */
	@SerializedName("error_description")
	private String errorDescription;
	
	/**
	 * Error actions
	 */
	@SerializedName("urn:x-callstats:auth:errorActions")
	private Collection<AuthenticateErrorAction> errorActions;
	
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getErrorDescription() {
		return errorDescription;
	}
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	public Collection<AuthenticateErrorAction> getErrorActions() {
		return errorActions;
	}
	public void setErrorActions(ArrayList<AuthenticateErrorAction> errorActions) {
		this.errorActions = errorActions;
	}
	
}
