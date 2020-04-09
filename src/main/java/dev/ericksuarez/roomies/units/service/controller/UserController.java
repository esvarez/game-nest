package dev.ericksuarez.roomies.units.service.controller;

import dev.ericksuarez.roomies.units.service.model.User;
import dev.ericksuarez.roomies.units.service.service.UserService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static dev.ericksuarez.roomies.units.service.config.UnitsUri.API;
import static dev.ericksuarez.roomies.units.service.config.UnitsUri.UNITS;
import static dev.ericksuarez.roomies.units.service.config.UnitsUri.USERS;

@RestController
@RequestMapping(API)
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(UNITS + "/{unitId}" + USERS)
    public List<User> getUsersByUnitId(@PathVariable(value = "unitId") Long unitId) {
        log.info("event=getUsersByUnitIdInvoked, unitId={}", unitId);
        return userService.getUsersByUnitId(unitId);
    }

    @GetMapping("/test")
    public String test () {
        String serverUrl = "http://sso.tdlabs.local:8083/auth";
        String realm = "esuarez";
        // idm-client needs to allow "Direct Access Grants: Resource Owner Password Credentials Grant"
        String clientId = "idm-client";
        String clientSecret = "0d61686d-57fc-4048-b052-4ce74978c468";

        /*
        Keycloak keycloak = KeycloakBuilder.builder() //
                .serverUrl(serverUrl) //
                .realm(realm) //
                .grantType(OAuth2Constants.PASSWORD) //
                .clientId(clientId) //
                .clientSecret(clientSecret) //
                .username("idm-admin") //
                .password("admin") //
                .build();

         */

        return "Done men";
    }

}
