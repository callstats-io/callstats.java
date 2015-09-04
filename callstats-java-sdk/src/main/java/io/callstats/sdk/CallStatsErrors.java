package io.callstats.sdk;

/**
*
* @author Karthik Budigere
*/

public enum CallStatsErrors {
	NONE(0,"none"),
	SUCCESS(1,"Success"),
	HTTP_ERROR(2,"Http Error"),
	AUTH_ERROR(3,"Authentication Error"),
	CS_PROTO_ERROR(4,"CallStats Protocol Error"),
	APP_CONNECTIVITY_ERROR(5,"App Connectivity Error");
  	    	
	private int index;
	private String reason;
	private CallStatsErrors(int index,String reason){
		this.index = index;
		this.reason = reason;
	}
    public int getIndex(){
    	return index;
    }
    
    public String getReason(){
    	return reason;
    }
}
