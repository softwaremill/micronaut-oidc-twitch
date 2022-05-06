package com.softwaremill.micronaut.openid.serdes;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.micronaut.security.oauth2.endpoint.token.response.OpenIdTokenResponse;

import java.io.IOException;
import java.util.List;

import static java.util.Spliterators.spliteratorUnknownSize;
import static java.util.stream.StreamSupport.stream;

public class OpenIdTokenResponseDeserializer extends JsonDeserializer<OpenIdTokenResponse> {

    @Override
    public OpenIdTokenResponse deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        var codec = p.getCodec();
        JsonNode node = codec.readTree(p);

        var scopesNode = node.get("scope");
        var scopes = List.<String>of();
        if (scopesNode.isArray()) {
            var arrayNode = (ArrayNode) scopesNode;
            scopes = stream(spliteratorUnknownSize(arrayNode.elements(), 0), false)
                    .map(JsonNode::asText)
                    .toList();
        }

        OpenIdTokenResponse openIdTokenResponse = new OpenIdTokenResponse();
        openIdTokenResponse.setIdToken(node.get("id_token").asText());
        openIdTokenResponse.setAccessToken(node.get("access_token").asText());
        openIdTokenResponse.setRefreshToken(node.get("refresh_token").asText());
        openIdTokenResponse.setTokenType(node.get("token_type").asText());
        openIdTokenResponse.setExpiresIn(node.get("expires_in").asInt());
        openIdTokenResponse.setScope(String.join(",", scopes));

        return openIdTokenResponse;
    }
}
