package io.callstats.sdk.internal;

import io.callstats.sdk.CallStatsErrors;
import io.callstats.sdk.ICallStatsTokenGenerator;
import io.callstats.sdk.httpclient.CallStatsHttp2Client;
import io.callstats.sdk.internal.listeners.CallStatsHttp2ResponseListener;
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
import java.util.concurrent.TimeUnit;

import okhttp3.Response;
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
	private String expires;
	
	/** The app id. */
	private int appId;
	
	/** The bridge id. */
	private String bridgeId;
	
	/** The http client. */
	private CallStatsHttp2Client httpClient;
	
	private String authErrString = "SDK Authentication Error";
	private String authSuccessString = "SDK authentication successful";
	
	
	
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
			final String bridgeId, final CallStatsHttp2Client httpClient,
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
			final String bridgeId, final CallStatsHttp2Client httpClient,
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
	
	/**
	 * Do authentication.
	 */
	public void doAuthentication() {
		if (!isAuthenticationInProgress) {
			sendAsyncAuthenticationRequest(appId, bridgeId,
					httpClient);
		}
	}
	
	/**
	 * Schedule authentication.
	 *
	 * @param appId the app id
	 * @param appSecret the app secret
	 * @param bridgeId the bridge id
	 * @param httpClient the http client
	 */
	private void scheduleAuthentication(final int appId, final String bridgeId, final CallStatsHttp2Client httpClient) {
		scheduler.schedule(new Runnable() {
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
	private void sendAsyncAuthenticationRequest(final int appId, final String bridgeId, final CallStatsHttp2Client httpClient) {
		synchronized (isAuthenticationInProgress) {
			if (isAuthenticationInProgress)
				return;
			isAuthenticationInProgress = true;
			
		}
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new NameValuePair("client_id", bridgeId+"@"+appId));
		params.add(new NameValuePair("code", tokenGenerator.generateToken(forcenewtoken)));
		params.add(new NameValuePair("grant_type", "authorization_code"));
		
		httpClient.sendHttp2Request(authenticateUrl, params, new CallStatsHttp2ResponseListener() {
			public void onResponse(Response response) {
				isAuthenticationInProgress = false;
				int responseStatus = response.code();
				if (responseStatus == CallStatsResponseStatus.RESPONSE_STATUS_SUCCESS) {
					AuthenticateResponse authResponseMessage;
					String responseString;
					try {
						responseString = response.body().string();
						authResponseMessage = gson.fromJson(responseString, AuthenticateResponse.class);
					} catch (Exception e) {
						e.printStackTrace();
						listener.onError(CallStatsErrors.HTTP_ERROR, e.getMessage());
						scheduleAuthentication(appId, bridgeId, httpClient);
						return;
					}
					logger.info("Authentication response "
								+ responseStatus + " "
								+ authResponseMessage.getToken());
					//expires = authResponseMessage.getAuthenticateBody().getExpires();
					token = authResponseMessage.getToken();
					listener.onInitialized(authSuccessString);
				} else if (responseStatus == CallStatsResponseStatus.INVALID_PROTO_FORMAT_ERROR) {
					AuthenticateResponseError authResponseMessageError;
					String responseString;
					try {
						responseString = response.body().string();
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
