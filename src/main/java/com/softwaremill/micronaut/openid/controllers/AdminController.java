package com.softwaremill.micronaut.openid.controllers;

import io.micronaut.core.util.CollectionUtils;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.views.View;

@Controller
public class AdminController {

    @Secured(SecurityRule.IS_AUTHENTICATED)
    @View("admin")
    @Get("/admin")
    public HttpResponse index() {
        return HttpResponse.ok(
                CollectionUtils.mapOf("loggedIn", true)
        );
    }
}