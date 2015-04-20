package org.appspot.apprtc;

import org.webrtc.StatsReport;

import android.util.Log;

public class CallStatsAPI {
	
	private static String TAG = "CallStatsAPI";
	
	public void initAPIAUTH(String clientId,CallStatsAPIListener listener)
	{
		Log.d(TAG,"Authentication happening");
		listener.onSucess();
	}
	
	public void onCallProgress(CallStatsAPIListener listener)
	{
		Log.d(TAG,"Call in progress");
		listener.onSucess();
	}
	
	
	public void onCallEnded(CallStatsAPIListener listener)
	{
		Log.d(TAG,"Call disconnected");
		listener.onSucess();
	}
	
	public void onCallStatsReceived(StatsReport[] report , CallStatsAPIListener listener)
	{
		//Log.d(TAG,"Call stats received");
		for(int i = 0;i<report.length;i++)
		{
			//Log.d(TAG,"report "+report[i].toString());
		}
		
		listener.onSucess();
	}
	
	public void onConnectedtoRoom(String roomId,CallStatsAPIListener listener)
	{
		Log.d(TAG,"Call connected to "+roomId);
		listener.onSucess();
	}
	
}
