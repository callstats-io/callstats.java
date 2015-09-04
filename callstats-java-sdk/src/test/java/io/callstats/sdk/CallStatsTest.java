package io.callstats.sdk;

import org.junit.Test;

/**
*
* @author Karthik Budigere
*/
public class CallStatsTest {
	CallStats callstatslib;
	
	@Test
	public void sendAuthenticationRequest() {
		callstatslib = new CallStats();
		callstatslib.intialize(175240362, "Gvd126EUWQheaWQX9mwmeWIbzvs=", "jit.si.345");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void sendAuthenticationRequestWithInvalidAppId() {
		callstatslib = new CallStats();
		callstatslib.intialize(175240363, "Gvd126EUWQheaWQX9mwmeWIbzvs=", "jit.si.345");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
