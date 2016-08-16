package io.callstats.sdk.internal;

public enum CallStatsUrls {
	STATS_SUBMIT_BASE("CallStats.BaseURL", "https://collector.callstats.io"),
	AUTH_BASE("CallStats.AuthBaseURL", "https://auth.callstats.io");
	
	private final String propertyName;
	private String propertyDefaultUrl;       

    private CallStatsUrls(String s, String defaultUrl) {
        propertyName = s;
        propertyDefaultUrl = defaultUrl;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : propertyName.equals(otherName);
    }
    
    public String getDefaultUrl() {
    	return propertyDefaultUrl;
    }

    public String toString() {
       return this.propertyName;
    }


}
