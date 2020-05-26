package io.callstats.sdk.data;

public class ConferenceInfo {

  private String confID;
  private String initiatorID;
  private String initiatorSiteID;

  public String getConfID() {
    return confID;
  }

  public void setConfID(String confID) {
    this.confID = confID;
  }

  public String getInitiatorID() {
    return initiatorID;
  }

  public void setInitiatorID(String initiatorID) {
    this.initiatorID = initiatorID;
  }

  public String getInitiatorSiteID() {
    return initiatorSiteID;
  }

  public void setInitiatorSiteID(String initiatorSiteID) {
    this.initiatorSiteID = initiatorSiteID;
  }

  public ConferenceInfo(String confID, String initiatorID) {
    this(confID, initiatorID, "");
  }
  public ConferenceInfo(String confID, String initiatorID, String initiatorSiteID) {
    super();
    this.confID = confID;
    this.initiatorID = initiatorID;
    this.initiatorSiteID = initiatorSiteID;
  }
}
