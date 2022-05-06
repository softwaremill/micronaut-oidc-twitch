package com.softwaremill.micronaut.openid.serdes;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.micronaut.context.event.BeanCreatedEvent;
import io.micronaut.context.event.BeanCreatedEventListener;
import io.micronaut.security.oauth2.endpoint.token.response.OpenIdTokenResponse;
import jakarta.inject.Singleton;

@Singleton
class ObjectMapperBeanEventListener implements BeanCreatedEventListener<ObjectMapper> {

    @Override
    public ObjectMapper onCreated(BeanCreatedEvent<ObjectMapper> event) {
        SimpleModule serdesModule = new SimpleModule("CustomSerializers", version())
                .addDeserializer(OpenIdTokenResponse.class, new OpenIdTokenResponseDeserializer());

        return event.getBean()
                .findAndRegisterModules()
                .registerModule(serdesModule);
    }

    private Version version() {
        return new Version(1, 0, 0, null, null, null);
    }
}
