package com.softwaremill.micronaut.openid.listeners;

import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.security.authentication.ServerAuthentication;
import io.micronaut.security.event.LoginSuccessfulEvent;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class LoginListener implements ApplicationEventListener<LoginSuccessfulEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(LoginListener.class);

    @Override
    public void onApplicationEvent(LoginSuccessfulEvent event) {
        LOG.warn("Login attempt successful: {}", ((ServerAuthentication)event.getSource()).toJson());
    }
}
