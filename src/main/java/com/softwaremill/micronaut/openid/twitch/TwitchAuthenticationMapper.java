package com.softwaremill.micronaut.openid.twitch;

import com.softwaremill.micronaut.openid.Error;
import com.softwaremill.micronaut.openid.account_info.AccountInfoProvider;
import com.softwaremill.micronaut.openid.account_info.PlatformAccountInfo;
import io.micronaut.context.annotation.Primary;
import io.micronaut.context.annotation.Prototype;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.config.AuthenticationModeConfiguration;
import io.micronaut.security.oauth2.configuration.OpenIdAdditionalClaimsConfiguration;
import io.micronaut.security.oauth2.endpoint.authorization.state.State;
import io.micronaut.security.oauth2.endpoint.token.response.DefaultOpenIdAuthenticationMapper;
import io.micronaut.security.oauth2.endpoint.token.response.OauthAuthenticationMapper;
import io.micronaut.security.oauth2.endpoint.token.response.OpenIdClaims;
import io.micronaut.security.oauth2.endpoint.token.response.OpenIdTokenResponse;
import io.vavr.control.Either;
import jakarta.inject.Named;

import java.util.List;
import java.util.Map;

@Primary
@Prototype
@Named("twitch")
public class TwitchAuthenticationMapper extends DefaultOpenIdAuthenticationMapper {

    private final AccountInfoProvider accountInfoProvider;

    TwitchAuthenticationMapper(OpenIdAdditionalClaimsConfiguration openIdAdditionalClaimsConfiguration,
                               AuthenticationModeConfiguration authenticationModeConfiguration,
                               @Named("twitch") AccountInfoProvider accountInfoProvider) {
        super(openIdAdditionalClaimsConfiguration, authenticationModeConfiguration);
        this.accountInfoProvider = accountInfoProvider;
    }

    @NonNull
    @Override
    public AuthenticationResponse createAuthenticationResponse(String providerName,
                                                               OpenIdTokenResponse tokenResponse,
                                                               OpenIdClaims openIdClaims,
                                                               @Nullable State state) {
        var claims = buildAttributes(providerName, tokenResponse, openIdClaims);
        var roles = getRoles(providerName, tokenResponse, openIdClaims);
        var username = getUsername(providerName, tokenResponse, openIdClaims);
        var accessToken = accessToken(claims);

        return fetchAccountInfo(username, accessToken)
                .map(result -> {
                    var userId = mapToUserId(result.externalAccountId());
                    claims.put("email", result.email());
                    return AuthenticationResponse.success(userId, roles, claims);
                })
                .getOrElseGet(err -> AuthenticationResponse.failure(err.message()));
    }

    private String mapToUserId(String externalAccountId) {
        return "user" + externalAccountId;
    }

    private String accessToken(Map<String, Object> claims) {
        return (String) claims.get(OauthAuthenticationMapper.ACCESS_TOKEN_KEY);
    }

    private Either<Error, PlatformAccountInfo> fetchAccountInfo(String twitchId, String accessToken) {
        return accountInfoProvider.getAccountInfo(twitchId, accessToken);
    }

    @Override
    protected List<String> getRoles(String providerName, OpenIdTokenResponse tokenResponse, OpenIdClaims openIdClaims) {
        return List.of("USER");
    }
}