package io.callstats.sdk;

import io.callstats.sdk.messages.AuthorizeRequestMessage;
import io.callstats.sdk.messages.AuthorizeResponseMessage;
import io.callstats.sdk.messages.ChallengeRequestMessage;
import io.callstats.sdk.messages.ChallengeResponseMessage;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

/**
 *
 * @author Karthik Budigere
 */
public class CallStats {
	
	private static final String CS_VERSION = "0.1.0";
	private static final int RESPONSE_STATUS_SUCCESS = 200;
	
	private static final String END_POINT_TYPE = "VideoBridge";
	
	private CallStatsHttpClient httpClient;
	private int appId;
	private String appSecret;
	private String bridgeId;
	Gson gson;
	
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
		// TODO Auto-generated constructor stub
		gson = new Gson();
	}

	public void intialize(int appId, String appSecret, String bridgeId) {
		
		this.appId = appId;
		this.appSecret = appSecret;
		this.bridgeId = bridgeId;
		
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
	
	private void handleAuthenticationChallenge(int appId,String appSecret,String challenge,String bridgeId) {
		
		String response = getHmacResponse(challenge);
		ChallengeRequestMessage requestMessage = new ChallengeRequestMessage(appId, bridgeId, CS_VERSION, END_POINT_TYPE, response);		
		String requestMessageString = gson.toJson(requestMessage);
		
		httpClient.sendAsyncHttpRequest("/o/challenge", "POST", requestMessageString,new CallStatsHttpResponseListener() {		
			public void onResponse(HttpResponse response) {
				int responseStatus = response.getStatusLine().getStatusCode();
				try {
					String responseString = EntityUtils.toString(response.getEntity());
					ChallengeResponseMessage obj = gson.fromJson(responseString,ChallengeResponseMessage.class);							
				} catch (ParseException e) {						
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}											
			}			
			public void onFailure(Exception e) {
				
			}
		});
	}
	
	private void sendAsyncAuthenticationRequest(final int appId,final String appSecret,final String bridgeId) {
		AuthorizeRequestMessage requestMessage = new AuthorizeRequestMessage(appId, bridgeId, CS_VERSION, END_POINT_TYPE);	
		String requestMessageString = gson.toJson(requestMessage);
		httpClient.sendAsyncHttpRequest("/o/authorize", "POST", requestMessageString,new CallStatsHttpResponseListener() {
			public void onResponse(HttpResponse response) {
				String challenge;
				int responseStatus = response.getStatusLine().getStatusCode();
				if(responseStatus == RESPONSE_STATUS_SUCCESS) {
					try {
						String responseString = EntityUtils.toString(response.getEntity());
						if(responseString.contains("challenge")) {
							AuthorizeResponseMessage obj = gson.fromJson(responseString,AuthorizeResponseMessage.class);
							challenge = obj.getChallenge();
							System.out.println("Challenge is "+challenge);
							handleAuthenticationChallenge(appId,appSecret,challenge,bridgeId);
						}
					} catch (ParseException e) {						
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
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
