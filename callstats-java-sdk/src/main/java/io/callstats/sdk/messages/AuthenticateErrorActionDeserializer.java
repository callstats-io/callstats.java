package io.callstats.sdk.messages;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class AuthenticateErrorActionDeserializer implements JsonDeserializer<AuthenticateErrorAction> {
	public AuthenticateErrorAction deserialize(JsonElement je, Type type, JsonDeserializationContext jdc)
			throws JsonParseException {
		AuthenticateErrorAction action = new AuthenticateErrorAction();
		action.setAction(AuthenticateErrorActionType.values()[je.getAsJsonObject().get("action").getAsInt()]);
		if (action.getAction() == AuthenticateErrorActionType.RETRY) {
			AuthenticateRetryActionParams params = new AuthenticateRetryActionParams();
			action.setParams(params);
			params.setTimeout(je.getAsJsonObject().get("params").getAsJsonObject().get("timeout").getAsInt());
		}
		
		return action;
	}
	
}