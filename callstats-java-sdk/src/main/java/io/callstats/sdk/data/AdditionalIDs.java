package io.callstats.sdk.data;

public class AdditionalIDs {

  private String sessionID;
  private String pbxID;
  private String pbxExtensionID;
  private String tenantID;
  private String productName;
  private String meetingsName;
  private String serverName;
  private String fqExtensionID;
  private String xcaasID;
  private String customerID;
  
  public String getCustomerID() {
	return customerID;
  }
	
  public void setCustomerID(String customerID) {
	this.customerID = customerID;
  }

  public String getSessionID() {
	return sessionID;
  }

  public void setSessionID(String sessionID) {
	this.sessionID = sessionID;
  }

  public String getPbxID() {
	return pbxID;
  }

  public void setPbxID(String pbxID) {
	this.pbxID = pbxID;
  }

  public String getPbxExtensionID() {
	return pbxExtensionID;
  }

  public void setPbxExtensionID(String pbxExtensionID) {
	this.pbxExtensionID = pbxExtensionID;
  }

  public String getTenantID() {
	return tenantID;
  }

  public void setTenantID(String tenantID) {
	this.tenantID = tenantID;
  }

  public String getProductName() {
	return productName;
  }

  public void setProductName(String productName) {
	this.productName = productName;
  }

  public String getMeetingsName() {
	return meetingsName;
  }

  public void setMeetingsName(String meetingsName) {
	this.meetingsName = meetingsName;
  }

  public String getServerName() {
	return serverName;
  }

  public void setServerName(String serverName) {
	this.serverName = serverName;
  }

  public String getFqExtensionID() {
	return fqExtensionID;
  }

  public void setFqExtensionID(String fqExtensionID) {
	this.fqExtensionID = fqExtensionID;
  }

  public String getXcaasID() {
	return xcaasID;
  }

  public void setXcaasID(String xcaasID) {
	this.xcaasID = xcaasID;
  }

}
