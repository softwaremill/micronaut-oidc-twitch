package com.softwaremill.micronaut.openid.controllers;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.views.View;

@Controller
public class HomeController {

    @Secured(SecurityRule.IS_ANONYMOUS)
    @View("home")
    @Get("/home")
    public HttpResponse index() {
        return HttpResponse.ok();
    }
}