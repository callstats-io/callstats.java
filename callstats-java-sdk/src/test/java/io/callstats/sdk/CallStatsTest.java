package io.callstats.sdk;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

/**
*
* @author Karthik Budigere
*/
public class CallStatsTest{
	CallStats callstatslib;
	CallStatsInitListener listener;
	
	
	@Before
	public void setUp() {

	}
	
	@Test
	public void intializeTest() {
		callstatslib = new CallStats();
		listener = mock(CallStatsInitListener.class);
		callstatslib.intialize(175240362, "Gvd126EUWQheaWQX9mwmeWIbzvs=", "jit.si.345",listener);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String msg = "SDK authentication successful";
		verify(listener).onInitialized(msg);
	}
	
	@Test
	public void intializeWithInvalidAppIdTest() {
		callstatslib = new CallStats();
		listener = mock(CallStatsInitListener.class);
		CallStatsErrors error = CallStatsErrors.HTTP_ERROR;
		String errMsg = "SDK Authentication Error";
		callstatslib.intialize(175240363, "Gvd126EUWQheaWQX9mwmeWIbzvs=", "jit.si.345",listener);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		verify(listener).onError(error, errMsg);
	}
	
	@Test
	public void intializeWithNullArgsTest() {
		Throwable e = null;
		callstatslib = new CallStats();
		listener = mock(CallStatsInitListener.class);
		try {
		callstatslib.intialize(0, "Gvd126EUWQheaWQX9mwmeWIbzvs=", "jit.si.345",listener);
		} catch (Throwable e1) {
			e = e1;
		}
		assertTrue(e instanceof IllegalArgumentException);
		
		try {
			callstatslib.intialize(175240363, null, "jit.si.345",listener);
		} catch (Throwable e1) {
				e = e1;
		}
		assertTrue(e instanceof IllegalArgumentException);
		
		try {
			callstatslib.intialize(175240363, "Gvd126EUWQheaWQX9mwmeWIbzvs=", null,listener);
		} catch (Throwable e1) {
				e = e1;
		}
		assertTrue(e instanceof IllegalArgumentException);
	}
}
