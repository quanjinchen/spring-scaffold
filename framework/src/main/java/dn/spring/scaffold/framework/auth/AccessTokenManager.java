package dn.spring.scaffold.framework.auth;

public interface AccessTokenManager {

    String getAccessToken(AccessTokenInfo accessTokenInfo, long expireSeconds);

    boolean checkAccessToken(String accessToken);

    AccessTokenInfo getAccessToken(String accessToken);
}
