package io.callstats.sdk;

import io.callstats.sdk.messages.AuthorizeRequestMessage;
import io.callstats.sdk.messages.AuthorizeResponseMessage;
import io.callstats.sdk.messages.ChallengeRequestMessage;
import io.callstats.sdk.messages.ChallengeResponseMessage;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

/**
 *
 * @author Karthik Budigere
 */
public class CallStats {
	
	private static final String CS_VERSION = "0.1.0";
	private static final int RESPONSE_STATUS_SUCCESS = 200;
	private static final int SERVER_ERROR = 500;
	private static final int INVALID_PROTO_FORMAT_ERROR = 400;
	private static final int INVALID_PARAM_ERROR = 403;
	private static final int GATEWAY_ERROR = 502;
	
	private static final String END_POINT_TYPE = "VideoBridge";
	
	private CallStatsHttpClient httpClient;
	private int appId;
	private String appSecret;
	private String bridgeId;
	private Gson gson;
	private CallStatsInitListener listener;
	private int  authenticationRetryTimeout = 5000;
	private static final ScheduledExecutorService scheduler = 
			  Executors.newSingleThreadScheduledExecutor();
	private static final Logger logger = LogManager.getLogger("CallStats");
	
	public CallStatsHttpClient getHttpClient() {
		return httpClient;
	}

	public void setHttpClient(CallStatsHttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getBridgeId() {
		return bridgeId;
	}

	public void setBridgeId(String bridgeId) {
		this.bridgeId = bridgeId;
	}

	public CallStats() {
		gson = new Gson();
	}

	public void intialize(int appId, String appSecret, String bridgeId,CallStatsInitListener listener) {
		if(appId <= 0 || appSecret == null || bridgeId == null) {
			throw new IllegalArgumentException("intialize: Arguments cannot be null");
		}
		
		this.appId = appId;
		this.appSecret = appSecret;
		this.bridgeId = bridgeId;
		this.listener = listener;
		
		httpClient = new CallStatsHttpClient();
		sendAsyncAuthenticationRequest(appId,appSecret,bridgeId);
	}
	
	private String getHmacResponse(String challenge) {
		String HMAC_SHA1_ALGORITHM = "HmacSHA1";   
		String response = null;
		try {
			Key signingKey = new SecretKeySpec(appSecret.getBytes(), HMAC_SHA1_ALGORITHM);
			Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
			mac.init(signingKey);
			byte[] rawHmac = mac.doFinal(challenge.getBytes());
			response = Base64.encodeBase64String(rawHmac);
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (InvalidKeyException e1) {
			e1.printStackTrace();
		}
		return response;
	}
	
	private void handleAuthenticationChallenge(final int appId,final String appSecret,final String challenge,final String bridgeId) {
		
		String response = getHmacResponse(challenge);
		ChallengeRequestMessage requestMessage = new ChallengeRequestMessage(appId, bridgeId, CS_VERSION, END_POINT_TYPE, response);		
		String requestMessageString = gson.toJson(requestMessage);
		
		httpClient.sendAsyncHttpRequest("/o/challenge", "POST", requestMessageString,new CallStatsHttpResponseListener() {		
			public void onResponse(HttpResponse response) {
				int responseStatus = response.getStatusLine().getStatusCode();
				ChallengeResponseMessage challengeResponseMessage;
				try {
					String responseString = EntityUtils.toString(response.getEntity());
					challengeResponseMessage  = gson.fromJson(responseString,ChallengeResponseMessage.class);							
				} catch (ParseException e) {						
					e.printStackTrace();
					throw new RuntimeException(e);
				} catch (IOException e) {
					e.printStackTrace();
					throw new RuntimeException(e);					
				}
				if(responseStatus == RESPONSE_STATUS_SUCCESS) {
					if(challengeResponseMessage.getStatus().equals("OK")) {
						logger.info("Challenge response "+responseStatus+" "+challengeResponseMessage.getExpires()+" "+challengeResponseMessage.getToken());
						listener.onInitialized("SDK authentication successful");
					}
					else {
						//if its proto error , callback to inform the error
						if(challengeResponseMessage.getReason() == CallStatsErrors.CS_PROTO_ERROR.getReason()) {
							listener.onError(CallStatsErrors.CS_PROTO_ERROR, "SDK Authentication Error");
						} else {
							scheduleAuthentication(appId, appSecret, bridgeId);
						}
					}
				} else if(responseStatus == GATEWAY_ERROR) {
					scheduleAuthentication(appId, appSecret, bridgeId);
					listener.onError(CallStatsErrors.APP_CONNECTIVITY_ERROR, "SDK Authentication Error");
				} else if(responseStatus == INVALID_PROTO_FORMAT_ERROR) {
					listener.onError(CallStatsErrors.AUTH_ERROR, "SDK Authentication Error");
				} else {
					listener.onError(CallStatsErrors.HTTP_ERROR, "SDK Authentication Error");
				}
			}		
			
			public void onFailure(Exception e) {
				
			}
		});
	}
	
	private void scheduleAuthentication(final int appId,final String appSecret,final String bridgeId) {
		scheduler.schedule(new Runnable() {
			
			public void run() {
				sendAsyncAuthenticationRequest(appId, appSecret, bridgeId);
			}
		}, authenticationRetryTimeout, TimeUnit.MILLISECONDS);
	}
	
	private void sendAsyncAuthenticationRequest(final int appId,final String appSecret,final String bridgeId) {
		AuthorizeRequestMessage requestMessage = new AuthorizeRequestMessage(appId, bridgeId, CS_VERSION, END_POINT_TYPE);	
		String requestMessageString = gson.toJson(requestMessage);
		httpClient.sendAsyncHttpRequest("/o/authorize", "POST", requestMessageString,new CallStatsHttpResponseListener() {
			public void onResponse(HttpResponse response) {
				String challenge;
				int responseStatus = response.getStatusLine().getStatusCode();
				AuthorizeResponseMessage authorizeResponseMessage;
				try {
					String responseString = EntityUtils.toString(response.getEntity());
					authorizeResponseMessage = gson.fromJson(responseString,AuthorizeResponseMessage.class);							
				} catch (ParseException e) {						
					e.printStackTrace();
					throw new RuntimeException(e);
				} catch (IOException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
				if(responseStatus == RESPONSE_STATUS_SUCCESS) {
					if(authorizeResponseMessage.getStatus().equals("OK")) {
						challenge = authorizeResponseMessage.getChallenge();
						logger.info("Challenge "+challenge);		
						handleAuthenticationChallenge(appId,appSecret,challenge,bridgeId);
					}
					else {
						listener.onError(CallStatsErrors.CS_PROTO_ERROR, "SDK Authentication Error");
					}
				} else if(responseStatus == SERVER_ERROR || responseStatus == GATEWAY_ERROR) {
					scheduleAuthentication(appId, appSecret, bridgeId);
					listener.onError(CallStatsErrors.APP_CONNECTIVITY_ERROR, "SDK Authentication Error");
				} else if(responseStatus == INVALID_PROTO_FORMAT_ERROR) {
					listener.onError(CallStatsErrors.AUTH_ERROR, "SDK Authentication Error");
				} else {
					listener.onError(CallStatsErrors.HTTP_ERROR, "SDK Authentication Error");
				}
			}

			public void onFailure(Exception e) {
				
			}
			
		});
	}
	
	private HttpResponse sendAuthenticationRequest(int appId,String appSecret,String bridgeId) {
		AuthorizeRequestMessage requestMessage = new AuthorizeRequestMessage(appId, bridgeId, CS_VERSION, END_POINT_TYPE);	
		String requestMessageString = gson.toJson(requestMessage);	
		HttpResponse response = httpClient.sendHttpRequest("/o/authorize", "POST", requestMessageString);
		return response;
	}
	
}
