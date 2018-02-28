package io.callstats.sdk.internal;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.callstats.sdk.listeners.CallStatsStartConferenceListener;
import io.callstats.sdk.messages.CallStatsEventMessage;

public class CallStatsEventMessageQueue {
	private List<CallStatsEventMessageInfo> callStatsEventMessageList;
	private int MAX_QUEUE_LENGTH = 1000;
	/** The logger. */
	private static final Logger logger = LogManager.getLogger("CallStats");
	
	public class CallStatsEventMessageInfo {
		CallStatsEventMessage callStatsEventMessage;
		CallStatsStartConferenceListener listener;
		CallStatsEventMessageInfo(CallStatsEventMessage mCallStatsEventMessage, CallStatsStartConferenceListener mListener) {
			this.listener = mListener;
			this.callStatsEventMessage = mCallStatsEventMessage;
		}
		
		public CallStatsEventMessage getCallStatsEventMessage() {
			return callStatsEventMessage;
		}

		public CallStatsStartConferenceListener getListener() {
			return listener;
		}		
	}

	public CallStatsEventMessageQueue() {
		callStatsEventMessageList = new ArrayList<CallStatsEventMessageInfo>();
	}

	public int getLength() {
		return callStatsEventMessageList.size();
	}

	public void push(CallStatsEventMessage callStatsEventMessage, CallStatsStartConferenceListener listener) {
		if (getLength() > MAX_QUEUE_LENGTH) {
			logger.warn("Queue is full, removing old element");
			pop();
		}
		CallStatsEventMessageInfo callStatsEventMessageInfo = new CallStatsEventMessageInfo(callStatsEventMessage, listener);
		callStatsEventMessageList.add(callStatsEventMessageInfo);
	}

	public void clear() {
		callStatsEventMessageList.clear();
	}

	public CallStatsEventMessageInfo pop() {
		CallStatsEventMessageInfo callStatsEventMessageInfo = null;
		if (callStatsEventMessageList.size() > 0) {
			callStatsEventMessageInfo = callStatsEventMessageList.get(0);
			callStatsEventMessageList.remove(0);
		}
		return callStatsEventMessageInfo;
	}
	
}

