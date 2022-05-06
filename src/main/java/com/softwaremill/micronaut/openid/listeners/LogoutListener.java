package com.softwaremill.micronaut.openid.listeners;

import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.security.authentication.ServerAuthentication;
import io.micronaut.security.event.LogoutEvent;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class LogoutListener implements ApplicationEventListener<LogoutEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(LogoutListener.class);

    @Override
    public void onApplicationEvent(LogoutEvent event) {
        LOG.warn("Logout successful: {}", ((ServerAuthentication)event.getSource()).toJson());
    }
}
