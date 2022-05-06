package com.softwaremill.micronaut.openid.account_info;

import java.time.Instant;

public record PlatformAccountInfo(String externalAccountId, String login, String email, String avatarUri, Instant createdAt) { }
