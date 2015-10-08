package io.callstats.sdk;

/**
 * The Enum CallStatsErrors.
 *
 * @author Karthik Budigere
 */

public enum CallStatsErrors {
	
	/** The none. */
	NONE(0,"none"),
	
	/** The success. */
	SUCCESS(1,"Success"),
	
	/** The http error. */
	HTTP_ERROR(2,"Http Error"),
	
	/** The auth error. */
	AUTH_ERROR(3,"Authentication Error"),
	
	/** The cs proto error. */
	CS_PROTO_ERROR(4,"CallStats Protocol Error"),
	
	/** The app connectivity error. */
	APP_CONNECTIVITY_ERROR(5,"App Connectivity Error"),
	
	
	INVALID_TOKEN_ERROR(6,"Invalid client token");
	
	/** The index. */
	private int index;
	
	/** The reason. */
	private String reason;
	
	/**
	 * Instantiates a new call stats errors.
	 *
	 * @param index the index
	 * @param reason the reason
	 */
	private CallStatsErrors(int index,String reason){
		this.index = index;
		this.reason = reason;
	}
    
    /**
     * Gets the index.
     *
     * @return the index
     */
    public int getIndex(){
    	return index;
    }
    
    /**
     * Gets the reason.
     *
     * @return the reason
     */
    public String getReason(){
    	return reason;
    }
}
