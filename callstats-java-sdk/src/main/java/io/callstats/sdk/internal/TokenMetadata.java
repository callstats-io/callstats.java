package io.callstats.sdk.internal;

public class TokenMetadata {
  class Urls {
    private String stats;
    private String events;

    public String getStats() {
      return stats;
    }

    public void setStats(String stats) {
      this.stats = stats;
    }

    public String getEvents() {
      return events;
    }

    public void setEvents(String events) {
      this.events = events;
    }
  }

  private Urls urls;

  public String getStatsUrl() {
    if (urls == null) {
      return null;
    }
    return urls.getStats();
  }

  public String getEventsUrl() {
    if (urls == null) {
      return null;
    }
    return urls.getEvents();
  }
}
