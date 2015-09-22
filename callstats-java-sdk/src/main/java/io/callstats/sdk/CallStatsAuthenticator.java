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
import com.ning.http.client.Response;

public class CallStatsAuthenticator {

	private static final Logger logger = LogManager.getLogger("CallStatsAuthenticator");
	private static final int RESPONSE_STATUS_SUCCESS = 200;
	private static final int SERVER_ERROR = 500;
	private static final int INVALID_PROTO_FORMAT_ERROR = 400;
	private static final int INVALID_PARAM_ERROR = 403;
	private static final int GATEWAY_ERROR = 502;
	
	private static final String challengeUrl = "/o/challenge";
	private static final String authorizeUrl = "/o/authorize";
	
	private CallStatsInitListener listener;
	private int  authenticationRetryTimeout = 5000;
	private static final ScheduledExecutorService scheduler = 
			  Executors.newSingleThreadScheduledExecutor();
	private Gson gson;
	
	private String token;
	private String expires;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getExpires() {
		return expires;
	}
	public void setExpires(String expires) {
		this.expires = expires;
	}	

	public CallStatsAuthenticator(CallStatsInitListener listener) {
		this.listener = listener;
		gson = new Gson();
	}
	
	public void doAuthentication(final int appId,final String appSecret,final String bridgeId,final CallStatsHttpClient httpClient) {
		sendAsyncAuthenticationRequest(appId,appSecret,bridgeId,httpClient);
	}
	
	
	public void doAuthentication1(final int appId,final String appSecret,final String bridgeId,final CallStatsAsyncHttpClient httpClient) {
		sendAsyncAuthenticationRequest(appId,appSecret,bridgeId,httpClient);
	}
	
	private String getHmacResponse(String challenge, String appSecret) {
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
	
	private void handleAuthenticationChallenge(final int appId,final String appSecret,final String challenge,final String bridgeId,final CallStatsHttpClient httpClient) {
		String response = getHmacResponse(challenge,appSecret);
		ChallengeRequestMessage requestMessage = new ChallengeRequestMessage(appId, bridgeId, CallStats.CS_VERSION, CallStats.END_POINT_TYPE, response);		
		String requestMessageString = gson.toJson(requestMessage);
		
		httpClient.sendAsyncHttpRequest(challengeUrl, CallStats.httpPostMethod, requestMessageString,new CallStatsHttpResponseListener() {		
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
				logger.info("Response status "+responseStatus);
				if(responseStatus == RESPONSE_STATUS_SUCCESS) {
					if(challengeResponseMessage.getStatus().equals("OK")) {
						logger.info("Challenge response "+responseStatus+" "+challengeResponseMessage.getExpires()+" "+challengeResponseMessage.getToken());
						expires = challengeResponseMessage.getExpires();
						token = challengeResponseMessage.getToken();
						listener.onInitialized("SDK authentication successful");
					}
					else {
						//if its proto error , callback to inform the error
						if(challengeResponseMessage.getReason() == CallStatsErrors.CS_PROTO_ERROR.getReason()) {
							listener.onError(CallStatsErrors.CS_PROTO_ERROR, "SDK Authentication Error");
						} else {
							scheduleAuthentication(appId, appSecret, bridgeId,httpClient);
						}
					}
				} else if(responseStatus == GATEWAY_ERROR) {
					scheduleAuthentication(appId, appSecret, bridgeId,httpClient);
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
	
	
	private void handleAuthenticationChallenge(final int appId,final String appSecret,final String challenge,final String bridgeId,final CallStatsAsyncHttpClient httpClient) {
		String response = getHmacResponse(challenge,appSecret);
		ChallengeRequestMessage requestMessage = new ChallengeRequestMessage(appId, bridgeId, CallStats.CS_VERSION, CallStats.END_POINT_TYPE, response);		
		String requestMessageString = gson.toJson(requestMessage);
		
		httpClient.sendAsyncHttpRequest(challengeUrl, CallStats.httpPostMethod, requestMessageString,new CallStatsAsyncHttpResponseListener() {		
			public void onResponse(Response response) {
				int responseStatus = response.getStatusCode();
				ChallengeResponseMessage challengeResponseMessage;
				try {
					String responseString = response.getResponseBody();
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
						expires = challengeResponseMessage.getExpires();
						token = challengeResponseMessage.getToken();
						listener.onInitialized("SDK authentication successful");
					}
					else {
						//if its proto error , callback to inform the error
						if(challengeResponseMessage.getReason() == CallStatsErrors.CS_PROTO_ERROR.getReason()) {
							listener.onError(CallStatsErrors.CS_PROTO_ERROR, "SDK Authentication Error");
						} else {
							scheduleAuthentication(appId, appSecret, bridgeId,httpClient);
						}
					}
				} else if(responseStatus == GATEWAY_ERROR) {
					scheduleAuthentication(appId, appSecret, bridgeId,httpClient);
					listener.onError(CallStatsErrors.APP_CONNECTIVITY_ERROR, "SDK Authentication Error");
				} else if(responseStatus == INVALID_PROTO_FORMAT_ERROR) {
					listener.onError(CallStatsErrors.AUTH_ERROR, "SDK Authentication Error");
				} else {
					listener.onError(CallStatsErrors.HTTP_ERROR, "SDK Authentication Error");
				}
			}

			public void onFailure(Exception e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private void scheduleAuthentication(final int appId,final String appSecret,final String bridgeId, final CallStatsHttpClient httpClient) {
		scheduler.schedule(new Runnable() {
			
			public void run() {
				sendAsyncAuthenticationRequest(appId, appSecret, bridgeId,httpClient);
			}
		}, authenticationRetryTimeout, TimeUnit.MILLISECONDS);
	}
	
	
	private void scheduleAuthentication(final int appId,final String appSecret,final String bridgeId, final CallStatsAsyncHttpClient httpClient) {
		scheduler.schedule(new Runnable() {
			
			public void run() {
				sendAsyncAuthenticationRequest(appId, appSecret, bridgeId,httpClient);
			}
		}, authenticationRetryTimeout, TimeUnit.MILLISECONDS);
	}
	
	
	private void sendAsyncAuthenticationRequest(final int appId,final String appSecret,final String bridgeId,final CallStatsHttpClient httpClient) {
		AuthorizeRequestMessage requestMessage = new AuthorizeRequestMessage(appId, bridgeId,CallStats.CS_VERSION, CallStats.END_POINT_TYPE);	
		String requestMessageString = gson.toJson(requestMessage);
		httpClient.sendAsyncHttpRequest(authorizeUrl, CallStats.httpPostMethod, requestMessageString,new CallStatsHttpResponseListener() {
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
						handleAuthenticationChallenge(appId,appSecret,challenge,bridgeId,httpClient);
					}
					else {
						listener.onError(CallStatsErrors.CS_PROTO_ERROR, "SDK Authentication Error");
					}
				} else if(responseStatus == SERVER_ERROR || responseStatus == GATEWAY_ERROR) {
					scheduleAuthentication(appId, appSecret, bridgeId,httpClient);
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
	
	
	private void sendAsyncAuthenticationRequest(final int appId,final String appSecret,final String bridgeId,final CallStatsAsyncHttpClient httpClient) {
		AuthorizeRequestMessage requestMessage = new AuthorizeRequestMessage(appId, bridgeId, CallStats.CS_VERSION, CallStats.END_POINT_TYPE);	
		String requestMessageString = gson.toJson(requestMessage);
		httpClient.sendAsyncHttpRequest(authorizeUrl, CallStats.httpPostMethod, requestMessageString,new CallStatsAsyncHttpResponseListener() {
			
			public void onResponse(Response response) {
				// TODO Auto-generated method stub
				String challenge;
				int responseStatus = response.getStatusCode();
				AuthorizeResponseMessage authorizeResponseMessage;
				try {
					String responseString = response.getResponseBody();
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
						handleAuthenticationChallenge(appId,appSecret,challenge,bridgeId,httpClient);
					}
					else {
						listener.onError(CallStatsErrors.CS_PROTO_ERROR, "SDK Authentication Error");
					}
				} else if(responseStatus == SERVER_ERROR || responseStatus == GATEWAY_ERROR) {
					scheduleAuthentication(appId, appSecret, bridgeId,httpClient);
					listener.onError(CallStatsErrors.APP_CONNECTIVITY_ERROR, "SDK Authentication Error");
				} else if(responseStatus == INVALID_PROTO_FORMAT_ERROR) {
					listener.onError(CallStatsErrors.AUTH_ERROR, "SDK Authentication Error");
				} else {
					listener.onError(CallStatsErrors.HTTP_ERROR, "SDK Authentication Error");
				}
			}
			
			public void onFailure(Exception e) {
				// TODO Auto-generated method stub
				
			}
		}); 
	}
}
