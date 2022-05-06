package com.softwaremill.micronaut.openid.account_info;

import com.softwaremill.micronaut.openid.Error;
import io.vavr.control.Either;

public interface AccountInfoProvider {

    Either<Error, PlatformAccountInfo> getAccountInfo(String platformAccountId, String accessToken);

}
