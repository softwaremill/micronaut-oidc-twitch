package com.softwaremill.micronaut.openid.twitch;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.ClientFilterChain;
import io.micronaut.http.filter.HttpClientFilter;
import io.micronaut.security.oauth2.configuration.OauthClientConfiguration;
import org.reactivestreams.Publisher;

@Filter("/api.twitch.tv/**")
class TwitchFilter implements HttpClientFilter {

    private final OauthClientConfiguration configuration;

    TwitchFilter(OauthClientConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Publisher<? extends HttpResponse<?>> doFilter(MutableHttpRequest<?> request, ClientFilterChain chain) {
        return chain.proceed(request
                .header("Client-Id", configuration.getClientId()));
    }
}