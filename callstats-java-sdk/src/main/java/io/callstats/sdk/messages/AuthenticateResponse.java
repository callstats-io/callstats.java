package io.callstats.sdk.messages;

import com.google.gson.annotations.SerializedName;
import io.callstats.sdk.internal.TokenMetadata;

public class AuthenticateResponse {
  /** The access token */
  @SerializedName("access_token")
  private String token;

  /** The token type */
  @SerializedName("token_type")
  private String tokenType;

  /** When token expires */
  @SerializedName("expires_in")
  private long expires;

  /** the token metadata */
  @SerializedName("metadata")
  private TokenMetadata metadata;

  public TokenMetadata getMetadata() {
    return metadata;
  }

  public void setMetadata(TokenMetadata metadata) {
    this.metadata = metadata;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getTokenType() {
    return tokenType;
  }

  public void setTokenType(String tokenType) {
    this.tokenType = tokenType;
  }

  public long getExpires() {
    return expires;
  }

  public void setExpires(long expires) {
    this.expires = expires;
  }
}
