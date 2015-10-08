package io.callstats.sdk;

/**
 * The Class HealthStatusData.
 */
public class HealthStatusData {
	
	/** The cpu usage. */
	private float cpuUsage;
	
	/** Total used memory on the machine. */
	private float memoryUsage;
	
	/** The total memory of the machine. */
	private float totalMemory;
	
	/** The number of Java threads that the video bridge is using. */
	private int threadCount;

	
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
	
	
	/**
	 * Gets the total memory.
	 *
	 * @return the total memory
	 */
	public float getTotalMemory() {
		return totalMemory;
	}

	/**
	 * Sets the total memory.
	 *
	 * @param cpuUsage the new cpu usage
	 */
	public void setTotalMemory(float totalMemory) {
		this.totalMemory = totalMemory;
	}

	
	/**
	 * Gets the thread count.
	 *
	 * @return the thread count
	 */
	public int getThreadCount() {
		return threadCount;
	}

	/**
	 * Sets the thread count.
	 *
	 * @param cpuUsage the new cpu usage
	 */
	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}
}
