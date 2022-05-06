package com.softwaremill.micronaut.openid.twitch;

import com.softwaremill.micronaut.openid.Error;
import com.softwaremill.micronaut.openid.account_info.AccountInfoProvider;
import com.softwaremill.micronaut.openid.account_info.PlatformAccountInfo;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named("twitch")
public class TwitchInfoProvider implements AccountInfoProvider {

    private static final Logger LOG = LoggerFactory.getLogger(TwitchInfoProvider.class);

    private final TwitchApiClient twitchApiClient;

    @Inject
    TwitchInfoProvider(TwitchApiClient twitchApiClient) {
        this.twitchApiClient = twitchApiClient;
    }

    @Override
    public Either<Error, PlatformAccountInfo> getAccountInfo(String platformAccountId, String accessToken) {
        return Try.ofSupplier(() -> twitchApiClient.getUserInfo("Bearer " + accessToken, platformAccountId))
                .onFailure(exc -> LOG.error("Error when fetching Twitch influencer info", exc))
                .toEither()
                .mapLeft(exc -> new Error("Cannot fetch account info"))
                .flatMap(this::accountInfoFrom);
    }

    private Either<Error, PlatformAccountInfo> accountInfoFrom(TwitchAccountInfoResponse response) {
        return response.data().headOption()
                .map(userInfo ->
                    new PlatformAccountInfo(
                            userInfo.id(),
                            userInfo.login(),
                            userInfo.email(),
                            userInfo.profileImageUrl(),
                            userInfo.createdAt())
                )
                .toEither(() -> new Error("Account info not found"));
    }
}
