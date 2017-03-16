package io.callstats.sdk.internal;

import io.callstats.sdk.CallStatsErrors;
import io.callstats.sdk.ICallStatsTokenGenerator;
import io.callstats.sdk.httpclient.CallStatsHttpClient;
import io.callstats.sdk.internal.listeners.CallStatsHttpResponseListener;
import io.callstats.sdk.listeners.CallStatsInitListener;
import io.callstats.sdk.messages.AuthenticateErrorAction;
import io.callstats.sdk.messages.AuthenticateErrorActionDeserializer;
import io.callstats.sdk.messages.AuthenticateErrorActionType;
import io.callstats.sdk.messages.AuthenticateResponse;
import io.callstats.sdk.messages.AuthenticateResponseError;
import io.callstats.sdk.messages.AuthenticateRetryActionParams;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * The Class CallStatsAuthenticator.
 */
public class CallStatsAuthenticator {

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger("CallStatsAuthenticator");
		
	/** The Constant authorizeUrl. */
	private static final String authenticateUrl = "/authenticate";
	
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
	private int expires;
	
	/** The app id. */
	private int appId;
	
	/** The bridge id. */
	private String bridgeId;
	
	/** The http client. */
	private CallStatsHttpClient httpClient;
	
	private String authErrString = "SDK Authentication Error";
	private String authSuccessString = "SDK authentication successful";
	
	private ScheduledFuture<?> scheduledFuture;
	
	private Boolean isAuthenticationInProgress = false;

	private ICallStatsTokenGenerator tokenGenerator;

	private boolean forcenewtoken = false;
	
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
	public long getExpires() {
		return expires;
	}
	
	/**
	 * Sets the expires.
	 *
	 * @param expires the new expires
	 */
	public void setExpires(int expires) {
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
		this.bridgeId = bridgeId;
		this.httpClient = httpClient;
		gson = new GsonBuilder()
				.registerTypeAdapter(AuthenticateErrorAction.class, new AuthenticateErrorActionDeserializer())
				.create();
	}
	
	/**
	 * Instantiates a new call stats authenticator.
	 *
	 * @param appId the app id
	 * @param tokenGenerator tokken generator
	 * @param bridgeId the bridge id
	 * @param httpClient the http client
	 * @param listener the listener
	 */
	public CallStatsAuthenticator(final int appId, ICallStatsTokenGenerator tokenGenerator,
			final String bridgeId, final CallStatsHttpClient httpClient,
			CallStatsInitListener listener) {
		this.tokenGenerator = tokenGenerator;
		this.listener = listener;
		this.appId = appId;
		this.bridgeId = bridgeId;
		this.httpClient = httpClient;
		gson = new GsonBuilder()
				.registerTypeAdapter(AuthenticateErrorAction.class, new AuthenticateErrorActionDeserializer())
				.create();
	}
	
	private synchronized void cancelScheduledAuthentication() {
		if (scheduledFuture != null) {
			scheduledFuture.cancel(false);
		}
	}
	
	/**
	 * Do authentication.
	 */
	public synchronized void doAuthentication() {
		if (!isAuthenticationInProgress) {
			sendAsyncAuthenticationRequest(appId, bridgeId,
					httpClient);
		}
	}
	
	private synchronized void doAuthenticationAfterExpire(long timeout) {	
		cancelScheduledAuthentication();
		scheduledFuture = scheduler.schedule(new Runnable() {
			public void run() {
				doAuthentication();
			}
		}, timeout, TimeUnit.SECONDS);
	}
	
	/**
	 * Schedule authentication.
	 *
	 * @param appId the app id
	 * @param appSecret the app secret
	 * @param bridgeId the bridge id
	 * @param httpClient the http client
	 */
	private synchronized void scheduleAuthentication(final int appId, final String bridgeId, final CallStatsHttpClient httpClient) {
		cancelScheduledAuthentication();
		scheduledFuture = scheduler.schedule(new Runnable() {
			public void run() {
				sendAsyncAuthenticationRequest(appId, bridgeId, httpClient);
			}
		}, authenticationRetryTimeout, TimeUnit.MILLISECONDS);
	}
	
	
	/**
	 * Send async authentication request.
	 *
	 * @param appId the app id
	 * @param appSecret the app secret
	 * @param bridgeId the bridge id
	 * @param httpClient the http client
	 */
	private void sendAsyncAuthenticationRequest(final int appId, final String bridgeId, final CallStatsHttpClient httpClient) {
		synchronized (isAuthenticationInProgress) {
			if (isAuthenticationInProgress)
				return;
			isAuthenticationInProgress = true;
			
		}
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("client_id", bridgeId+"@"+appId));
		params.add(new BasicNameValuePair("code", tokenGenerator.generateToken(forcenewtoken)));
		params.add(new BasicNameValuePair("grant_type", "authorization_code"));
		
		httpClient.sendAsyncHttpRequest(authenticateUrl, CallStatsConst.httpPostMethod, params, new CallStatsHttpResponseListener() {
			public void onResponse(HttpResponse response) {
				isAuthenticationInProgress = false;
				int responseStatus = response.getStatusLine()
						.getStatusCode();
				logger.info("Response status " + responseStatus);
				if (responseStatus == CallStatsResponseStatus.RESPONSE_STATUS_SUCCESS) {
					AuthenticateResponse authResponseMessage;
					String responseString;
					try {
						responseString = EntityUtils.toString(response.getEntity());
						authResponseMessage = gson.fromJson(responseString, AuthenticateResponse.class);
					} catch (Exception e) {
						e.printStackTrace();
						listener.onError(CallStatsErrors.HTTP_ERROR, e.getMessage());
						scheduleAuthentication(appId, bridgeId, httpClient);
						return;
					}					
					
					expires = authResponseMessage.getExpires();
					token = authResponseMessage.getToken();
							
					int timeout = (int) (expires*0.9);
					if (timeout > 0 ) {
						doAuthenticationAfterExpire(timeout);
					}		
					listener.onInitialized(authSuccessString);
					
				} else if (responseStatus == CallStatsResponseStatus.INVALID_PROTO_FORMAT_ERROR) {
					AuthenticateResponseError authResponseMessageError;
					String responseString;
					try {
						responseString = EntityUtils.toString(response.getEntity());
						authResponseMessageError = gson.fromJson(responseString, AuthenticateResponseError.class);
					} catch (Exception e) {
						e.printStackTrace();
						listener.onError(CallStatsErrors.HTTP_ERROR, e.getMessage());
						scheduleAuthentication(appId, bridgeId, httpClient);
						return;
					}
					for (AuthenticateErrorAction action : authResponseMessageError.getErrorActions()) {
						if (action.getAction() == AuthenticateErrorActionType.GET_NEW_TOKEN) {
							forcenewtoken = true;
						} else if (action.getAction() == AuthenticateErrorActionType.RETRY) {
							authenticationRetryTimeout = ((AuthenticateRetryActionParams)action.getParams()).getTimeout();
							scheduleAuthentication(appId, bridgeId, httpClient);
						}
					}
					listener.onError(CallStatsErrors.AUTH_ERROR, authErrString);
				} else if (responseStatus == CallStatsResponseStatus.GATEWAY_ERROR) {
					scheduleAuthentication(appId,  bridgeId, httpClient);
					listener.onError(
							CallStatsErrors.APP_CONNECTIVITY_ERROR, authErrString);
				} else {
					scheduleAuthentication(appId, bridgeId, httpClient);
					listener.onError(CallStatsErrors.HTTP_ERROR, authErrString);
				}
			
				
			}

			public void onFailure(Exception e) {
				isAuthenticationInProgress = false;
				listener.onError(CallStatsErrors.HTTP_ERROR, e.getMessage());
			}

		});
	}
	
	
}
