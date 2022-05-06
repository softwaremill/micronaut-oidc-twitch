package com.softwaremill.micronaut.openid.twitch;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.vavr.collection.List;

import java.io.IOException;
import java.time.Instant;

record TwitchAccountInfoResponse(List<RawUserInfo> data)
{
    @JsonDeserialize(using = RawUserInfoDeserializer.class)
    record RawUserInfo(String id, String login, String displayName, String profileImageUrl, String email, Instant createdAt) {}

    static class RawUserInfoDeserializer extends JsonDeserializer<RawUserInfo> {

        @Override
        public RawUserInfo deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            var codec = p.getCodec();
            JsonNode node = codec.readTree(p);

            var id = node.get("id").asText();
            var login = node.get("login").asText();
            var displayName = node.get("display_name").asText();
            var profileImageUri = node.get("profile_image_url").asText();
            var email = node.get("email").asText();
            var createdAt = Instant.parse(node.get("created_at").asText());

            return new RawUserInfo(id, login, displayName, profileImageUri, email, createdAt);
        }
    }
}

