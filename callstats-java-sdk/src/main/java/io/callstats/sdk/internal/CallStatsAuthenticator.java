package io.callstats.sdk.internal;

import io.callstats.sdk.CallStatsErrors;
import io.callstats.sdk.httpclient.CallStatsHttpClient;
import io.callstats.sdk.internal.listeners.CallStatsHttpResponseListener;
import io.callstats.sdk.listeners.CallStatsInitListener;
import io.callstats.sdk.messages.AuthorizeRequest;
import io.callstats.sdk.messages.AuthorizeResponse;
import io.callstats.sdk.messages.ChallengeRequest;
import io.callstats.sdk.messages.ChallengeResponse;

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
import com.google.gson.JsonSyntaxException;

/**
 * The Class CallStatsAuthenticator.
 */
public class CallStatsAuthenticator {

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger("CallStatsAuthenticator");
		
	/** The Constant challengeUrl. */
	private static final String challengeUrl = "/o/challenge";
	
	/** The Constant authorizeUrl. */
	private static final String authorizeUrl = "/o/authorize";
	
	/** The listener. */
	private CallStatsInitListener listener;
	
	/** The authentication retry timeout. */
	private int  authenticationRetryTimeout = 5000;
	
	/** The Constant scheduler. */
	private static final ScheduledExecutorService scheduler = 
			  Executors.newSingleThreadScheduledExecutor();
	
	/** The gson. */
	private Gson gson;
	
	/** The token. */
	private String token;
	
	/** The expires. */
	private String expires;
	
	/** The app id. */
	private int appId;
	
	/** The app secret. */
	private String appSecret;
	
	/** The bridge id. */
	private String bridgeId;
	
	/** The http client. */
	private CallStatsHttpClient httpClient;
	
	private String authErrString = "SDK Authentication Error";
	private String authSuccessString = "SDK authentication successful";
	
	
	
	private boolean isAuthenticationInProgress = false;
	
	/**
	 * Gets the token.
	 *
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	
	/**
	 * Sets the token.
	 *
	 * @param token the new token
	 */
	public void setToken(String token) {
		this.token = token;
	}
	
	/**
	 * Gets the expires.
	 *
	 * @return the expires
	 */
	public String getExpires() {
		return expires;
	}
	
	/**
	 * Sets the expires.
	 *
	 * @param expires the new expires
	 */
	public void setExpires(String expires) {
		this.expires = expires;
	}	

	/**
	 * Instantiates a new call stats authenticator.
	 *
	 * @param appId the app id
	 * @param appSecret the app secret
	 * @param bridgeId the bridge id
	 * @param httpClient the http client
	 * @param listener the listener
	 */
	public CallStatsAuthenticator(final int appId, final String appSecret,
			final String bridgeId, final CallStatsHttpClient httpClient,
			CallStatsInitListener listener) {
		this.listener = listener;
		this.appId = appId;
		this.appSecret = appSecret;
		this.bridgeId = bridgeId;
		this.httpClient = httpClient;
		gson = new Gson();
	}
	
	/**
	 * Do authentication.
	 */
	public void doAuthentication() {
		if (!isAuthenticationInProgress) {
			sendAsyncAuthenticationRequest(appId, appSecret, bridgeId,
					httpClient);
		}
	}
	
	
//	/**
//	 * Do authentication.
//	 *
//	 * @param appId the app id
//	 * @param appSecret the app secret
//	 * @param bridgeId the bridge id
//	 * @param httpClient the http client
//	 */
//	public void doAuthentication(final int appId,final String appSecret,final String bridgeId,final CallStatsAsyncHttpClient httpClient) {
//		sendAsyncAuthenticationRequest(appId,appSecret,bridgeId,httpClient);
//	}
	
	/**
	 * Gets the hmac response.
	 *
	 * @param challenge the challenge
	 * @param appSecret the app secret
	 * @return the hmac response
	 */
	private String getHmacResponse(String challenge, String appSecret) {
		String HMAC_SHA1_ALGORITHM = "HmacSHA1";
		String response = null;
		try {
			Key signingKey = new SecretKeySpec(appSecret.getBytes(),
					HMAC_SHA1_ALGORITHM);
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
	
	/**
	 * Handle authentication challenge.
	 *
	 * @param appId the app id
	 * @param appSecret the app secret
	 * @param challenge the challenge
	 * @param bridgeId the bridge id
	 * @param httpClient the http client
	 */
	private void handleAuthenticationChallenge(final int appId,
			final String appSecret, final String challenge,
			final String bridgeId, final CallStatsHttpClient httpClient) {
		String response = getHmacResponse(challenge, appSecret);
		ChallengeRequest requestMessage = new ChallengeRequest(appId, bridgeId,
				CallStatsConst.CS_VERSION, CallStatsConst.END_POINT_TYPE,
				response, challenge);
		String requestMessageString = gson.toJson(requestMessage);

		httpClient.sendAsyncHttpRequest(challengeUrl, CallStatsConst.httpPostMethod, requestMessageString, new CallStatsHttpResponseListener() {
			public void onResponse(HttpResponse response) {
				isAuthenticationInProgress = false;
				int responseStatus = response.getStatusLine()
						.getStatusCode();
				logger.info("Response status " + responseStatus);
				if (responseStatus == CallStatsResponseStatus.RESPONSE_STATUS_SUCCESS) {
					ChallengeResponse challengeResponseMessage;
					try {
						String responseString = EntityUtils.toString(response.getEntity());
						challengeResponseMessage = gson.fromJson(responseString, ChallengeResponse.class);
					} catch (ParseException e) {
						e.printStackTrace();
						throw new RuntimeException(e);
					} catch (IOException e) {
						e.printStackTrace();
						throw new RuntimeException(e);
					} catch (JsonSyntaxException e) {
						e.printStackTrace();
						throw new RuntimeException(e);
					}
					if (challengeResponseMessage.getStatus().equals(CallStatsConst.SUCCESS)) {
						logger.info("Challenge response "
								+ responseStatus + " "
								+ challengeResponseMessage.getExpires()
								+ " "
								+ challengeResponseMessage.getToken());
						expires = challengeResponseMessage.getExpires();
						token = challengeResponseMessage.getToken();
						listener.onInitialized(authSuccessString);
					} else {
						// if its proto error , callback to inform the
						// error
						if (challengeResponseMessage.getReason().equals(CallStatsErrors.CS_PROTO_ERROR.getReason())) {
							listener.onError(CallStatsErrors.CS_PROTO_ERROR, authErrString);
						} else {
							scheduleAuthentication(appId, appSecret, bridgeId, httpClient);
						}
					}
				} else if (responseStatus == CallStatsResponseStatus.GATEWAY_ERROR) {
					scheduleAuthentication(appId, appSecret, bridgeId, httpClient);
					listener.onError(
							CallStatsErrors.APP_CONNECTIVITY_ERROR, authErrString);
				} else if (responseStatus == CallStatsResponseStatus.INVALID_PROTO_FORMAT_ERROR) {
					listener.onError(CallStatsErrors.AUTH_ERROR, authErrString);
				} else {
					listener.onError(CallStatsErrors.HTTP_ERROR, authErrString);
				}
			}

			public void onFailure(Exception e) {
				isAuthenticationInProgress = false;
				listener.onError(CallStatsErrors.HTTP_ERROR,
						e.getMessage());
			}
		});
	}
	
	
//	/**
//	 * Handle authentication challenge.
//	 *
//	 * @param appId the app id
//	 * @param appSecret the app secret
//	 * @param challenge the challenge
//	 * @param bridgeId the bridge id
//	 * @param httpClient the http client
//	 */
//	private void handleAuthenticationChallenge(final int appId,final String appSecret,final String challenge,final String bridgeId,final CallStatsAsyncHttpClient httpClient) {
//		String response = getHmacResponse(challenge,appSecret);
//		ChallengeRequest requestMessage = new ChallengeRequest(appId, bridgeId, CallStatsConst.CS_VERSION, CallStatsConst.END_POINT_TYPE, response);		
//		String requestMessageString = gson.toJson(requestMessage);
//		
//		httpClient.sendAsyncHttpRequest(challengeUrl, CallStatsConst.httpPostMethod, requestMessageString,new CallStatsAsyncHttpResponseListener() {		
//			public void onResponse(Response response) {
//				int responseStatus = response.getStatusCode();
//				ChallengeResponse challengeResponseMessage;
//				try {
//					String responseString = response.getResponseBody();
//					challengeResponseMessage  = gson.fromJson(responseString,ChallengeResponse.class);							
//				} catch (ParseException e) {						
//					e.printStackTrace();
//					throw new RuntimeException(e);
//				} catch (IOException e) {
//					e.printStackTrace();
//					throw new RuntimeException(e);					
//				}
//				if(responseStatus == RESPONSE_STATUS_SUCCESS) {
//					if(challengeResponseMessage.getStatus().equals("OK")) {
//						logger.info("Challenge response "+responseStatus+" "+challengeResponseMessage.getExpires()+" "+challengeResponseMessage.getToken());
//						expires = challengeResponseMessage.getExpires();
//						token = challengeResponseMessage.getToken();
//						listener.onInitialized("SDK authentication successful");
//					}
//					else {
//						//if its proto error , callback to inform the error
//						if(challengeResponseMessage.getReason().equals(CallStatsErrors.CS_PROTO_ERROR.getReason())) {
//							listener.onError(CallStatsErrors.CS_PROTO_ERROR, "SDK Authentication Error");
//						} else {
//							scheduleAuthentication(appId, appSecret, bridgeId,httpClient);
//						}
//					}
//				} else if(responseStatus == GATEWAY_ERROR) {
//					scheduleAuthentication(appId, appSecret, bridgeId,httpClient);
//					listener.onError(CallStatsErrors.APP_CONNECTIVITY_ERROR, "SDK Authentication Error");
//				} else if(responseStatus == INVALID_PROTO_FORMAT_ERROR) {
//					listener.onError(CallStatsErrors.AUTH_ERROR, "SDK Authentication Error");
//				} else {
//					listener.onError(CallStatsErrors.HTTP_ERROR, "SDK Authentication Error");
//				}
//			}
//
//			public void onFailure(Exception e) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
//	}
	
	/**
	 * Schedule authentication.
	 *
	 * @param appId the app id
	 * @param appSecret the app secret
	 * @param bridgeId the bridge id
	 * @param httpClient the http client
	 */
	private void scheduleAuthentication(final int appId, final String appSecret, final String bridgeId, final CallStatsHttpClient httpClient) {
		scheduler.schedule(new Runnable() {
			public void run() {
				sendAsyncAuthenticationRequest(appId, appSecret, bridgeId, httpClient);
			}
		}, authenticationRetryTimeout, TimeUnit.MILLISECONDS);
	}
	
	
//	/**
//	 * Schedule authentication.
//	 *
//	 * @param appId the app id
//	 * @param appSecret the app secret
//	 * @param bridgeId the bridge id
//	 * @param httpClient the http client
//	 */
//	private void scheduleAuthentication(final int appId,final String appSecret,final String bridgeId, final CallStatsAsyncHttpClient httpClient) {
//		scheduler.schedule(new Runnable() {
//			
//			public void run() {
//				sendAsyncAuthenticationRequest(appId, appSecret, bridgeId,httpClient);
//			}
//		}, authenticationRetryTimeout, TimeUnit.MILLISECONDS);
//	}
	
	
	/**
	 * Send async authentication request.
	 *
	 * @param appId the app id
	 * @param appSecret the app secret
	 * @param bridgeId the bridge id
	 * @param httpClient the http client
	 */
	private void sendAsyncAuthenticationRequest(final int appId, final String appSecret, final String bridgeId, final CallStatsHttpClient httpClient) {
		AuthorizeRequest requestMessage = new AuthorizeRequest(appId, bridgeId, CallStatsConst.CS_VERSION, CallStatsConst.END_POINT_TYPE);
		String requestMessageString = gson.toJson(requestMessage);
		isAuthenticationInProgress = true;
		httpClient.sendAsyncHttpRequest(authorizeUrl, CallStatsConst.httpPostMethod, requestMessageString, new CallStatsHttpResponseListener() {
			public void onResponse(HttpResponse response) {
				String challenge;
				int responseStatus = response.getStatusLine().getStatusCode();
				if (responseStatus == CallStatsResponseStatus.RESPONSE_STATUS_SUCCESS) {
					AuthorizeResponse authorizeResponseMessage;
					try {
						String responseString = EntityUtils.toString(response.getEntity());
						logger.info("Auth response " + responseStatus);
						authorizeResponseMessage = gson.fromJson(responseString, AuthorizeResponse.class);
					} catch (ParseException e) {
						isAuthenticationInProgress = false;
						e.printStackTrace();
						throw new RuntimeException(e);
					} catch (IOException e) {
						isAuthenticationInProgress = false;
						e.printStackTrace();
						throw new RuntimeException(e);
					} catch (JsonSyntaxException e) {
						isAuthenticationInProgress = false;
						e.printStackTrace();
						throw new RuntimeException(e);
					}
					if (authorizeResponseMessage.getStatus().equals(CallStatsConst.SUCCESS)) {
						challenge = authorizeResponseMessage.getChallenge();
						logger.info("Challenge " + challenge);
						handleAuthenticationChallenge(appId, appSecret, challenge, bridgeId, httpClient);
					} else {
						isAuthenticationInProgress = false;
						logger.info("Authentication Request Error");
						listener.onError(CallStatsErrors.CS_PROTO_ERROR, authErrString);
					}
				} else if (responseStatus == CallStatsResponseStatus.SERVER_ERROR || responseStatus == CallStatsResponseStatus.GATEWAY_ERROR) {
					isAuthenticationInProgress = false;
					scheduleAuthentication(appId, appSecret, bridgeId, httpClient);
					listener.onError(CallStatsErrors.APP_CONNECTIVITY_ERROR, authErrString);
				} else if (responseStatus == CallStatsResponseStatus.INVALID_PROTO_FORMAT_ERROR) {
					isAuthenticationInProgress = false;
					listener.onError(CallStatsErrors.AUTH_ERROR, authErrString);
				} else {
					isAuthenticationInProgress = false;
					listener.onError(CallStatsErrors.HTTP_ERROR, authErrString);
				}
			}

			public void onFailure(Exception e) {
				isAuthenticationInProgress = false;
				listener.onError(CallStatsErrors.HTTP_ERROR, e.getMessage());
			}

		});
	}
	
	
//	/**
//	 * Send async authentication request.
//	 *
//	 * @param appId the app id
//	 * @param appSecret the app secret
//	 * @param bridgeId the bridge id
//	 * @param httpClient the http client
//	 */
//	private void sendAsyncAuthenticationRequest(final int appId, final String appSecret, final String bridgeId, final CallStatsAsyncHttpClient httpClient) {
//		AuthorizeRequest requestMessage = new AuthorizeRequest(appId, bridgeId, CallStatsConst.CS_VERSION, CallStatsConst.END_POINT_TYPE);	
//		String requestMessageString = gson.toJson(requestMessage);
//		httpClient.sendAsyncHttpRequest(authorizeUrl, CallStatsConst.httpPostMethod, requestMessageString,new CallStatsAsyncHttpResponseListener() {
//			
//			public void onResponse(Response response) {
//				// TODO Auto-generated method stub
//				String challenge;
//				int responseStatus = response.getStatusCode();
//				AuthorizeResponse authorizeResponseMessage;
//				try {
//					String responseString = response.getResponseBody();
//					authorizeResponseMessage = gson.fromJson(responseString,AuthorizeResponse.class);							
//				} catch (ParseException e) {						
//					e.printStackTrace();
//					throw new RuntimeException(e);
//				} catch (IOException e) {
//					e.printStackTrace();
//					throw new RuntimeException(e);
//				}
//				if(responseStatus == RESPONSE_STATUS_SUCCESS) {
//					if(authorizeResponseMessage.getStatus().equals("OK")) {
//						challenge = authorizeResponseMessage.getChallenge();
//						logger.info("Challenge "+challenge);		
//						handleAuthenticationChallenge(appId,appSecret,challenge,bridgeId,httpClient);
//					}
//					else {
//						listener.onError(CallStatsErrors.CS_PROTO_ERROR, "SDK Authentication Error");
//					}
//				} else if(responseStatus == SERVER_ERROR || responseStatus == GATEWAY_ERROR) {
//					scheduleAuthentication(appId, appSecret, bridgeId,httpClient);
//					listener.onError(CallStatsErrors.APP_CONNECTIVITY_ERROR, "SDK Authentication Error");
//				} else if(responseStatus == INVALID_PROTO_FORMAT_ERROR) {
//					listener.onError(CallStatsErrors.AUTH_ERROR, "SDK Authentication Error");
//				} else {
//					listener.onError(CallStatsErrors.HTTP_ERROR, "SDK Authentication Error");
//				}
//			}
//			
//			public void onFailure(Exception e) {
//				// TODO Auto-generated method stub
//				
//			}
//		}); 
//	}
}
