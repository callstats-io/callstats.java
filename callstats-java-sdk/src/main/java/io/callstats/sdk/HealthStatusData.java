package io.callstats.sdk;

/**
 * The Class HealthStatusData.
 */
public class HealthStatusData {
	
	/** The cpu usage. */
	private float cpuUsage;
	
	/** The memory usage. */
	private float memoryUsage;
	
	/**
	 * Instantiates a new health status data.
	 */
	public HealthStatusData() {
		
	}
	
	/**
	 * Gets the memory usage.
	 *
	 * @return the memory usage
	 */
	public float getMemoryUsage() {
		return memoryUsage;
	}
	
	/**
	 * Sets the memory usage.
	 *
	 * @param memoryUsage the new memory usage
	 */
	public void setMemoryUsage(float memoryUsage) {
		this.memoryUsage = memoryUsage;
	}
	
	/**
	 * Gets the cpu usage.
	 *
	 * @return the cpu usage
	 */
	public float getCpuUsage() {
		return cpuUsage;
	}
	
	/**
	 * Sets the cpu usage.
	 *
	 * @param cpuUsage the new cpu usage
	 */
	public void setCpuUsage(float cpuUsage) {
		this.cpuUsage = cpuUsage;
	}		
}
