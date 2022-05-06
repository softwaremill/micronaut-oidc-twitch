package com.softwaremill.micronaut.openid.twitch;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;

import static io.micronaut.http.HttpHeaders.ACCEPT;
import static io.micronaut.http.HttpHeaders.USER_AGENT;

@Client("${twitch.api}")
@Header(name = USER_AGENT, value = "HTTP Client for Twitch")
@Header(name = ACCEPT, value = "application/json")
@Header(name = "Client-Id", value = "${micronaut.security.oauth2.clients.twitch.client-id}")
public interface TwitchApiClient {

    @Get("/users")
    TwitchAccountInfoResponse getUserInfo(
            @NonNull @Header(name = "Authorization") String authorization,
            @NonNull @QueryValue(value = "id") String twitchId);

}
