package io.callstats.sdk;

/**
 * The Class EndpointInfo.
 */
public class ServerInfo {
	
	/** The endpoint data. */
	EndPointData endpointData;
	
	/**
	 * Instantiates a new endpoint info.
	 */
	public ServerInfo() {
		super();
		endpointData = new EndPointData();
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return endpointData.getName();
	}
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		endpointData.setName(name);
	}
	
	/**
	 * Gets the os.
	 *
	 * @return the os
	 */
	public String getOs() {
		return endpointData.getOs();
	}
	
	/**
	 * Sets the os.
	 *
	 * @param os the new os
	 */
	public void setOs(String os) {
		endpointData.setOs(os);
	}
	
	/**
	 * Gets the ver.
	 *
	 * @return the ver
	 */
	public String getVer() {
		return endpointData.getVer();
	}
	
	/**
	 * Sets the ver.
	 *
	 * @param ver the new ver
	 */
	public void setVer(String ver) {
		endpointData.setVer(ver);
	}
	
	/**
	 * The Class EndPointData.
	 */
	private class EndPointData {
		
		/** The name. */
		String name;
		
		/** The os. */
		String  os;		
		
		/** The ver. */
		String ver;
		
		
		/**
		 * Gets the name.
		 *
		 * @return the name
		 */
		public String getName() {
			return name;
		}
		
		/**
		 * Sets the name.
		 *
		 * @param name the new name
		 */
		public void setName(String name) {
			this.name = name;
		}
		
		/**
		 * Gets the os.
		 *
		 * @return the os
		 */
		public String getOs() {
			return os;
		}
		
		/**
		 * Sets the os.
		 *
		 * @param os the new os
		 */
		public void setOs(String os) {
			this.os = os;
		}
		
		/**
		 * Gets the ver.
		 *
		 * @return the ver
		 */
		public String getVer() {
			return ver;
		}
		
		/**
		 * Sets the ver.
		 *
		 * @param ver the new ver
		 */
		public void setVer(String ver) {
			this.ver = ver;
		}
	}
			
}