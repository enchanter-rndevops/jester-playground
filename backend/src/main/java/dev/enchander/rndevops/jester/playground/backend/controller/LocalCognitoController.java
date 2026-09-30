package dev.enchander.rndevops.jester.playground.backend.controller;

import java.util.Base64;
import java.util.Map;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

record LocalCognito(
        String username,
        String password) {
}

@Profile("local")
@RestController
@RequestMapping("/api/v1")
public class LocalCognitoController {

    @PostMapping("/localauth")
    public Map<String, Object> localCognito(@RequestBody LocalCognito auth) {

        String header = Base64.getUrlEncoder().withoutPadding()
                .encodeToString("{\"alg\":\"none\",\"typ\":\"JWT\"}".getBytes());

        String payloadJson = String.format("""
                {
                  "sub": "%s",
                  "cognito:username": "%s",
                  "email": "local0001@example.com",
                  "token_use": "id",
                  "auth_time": 1690000000,
                  "iat": 1690000000,
                  "exp": 1690003600,
                  "iss": "http://localhost:8080/local-cognito"
                }
                """, auth.username(), auth.username());

        String payload = Base64.getUrlEncoder().withoutPadding()
                .encodeToString(payloadJson.getBytes());

        String idToken = header + "." + payload + ".";

        return Map.of(
                "IdToken", idToken,
                "AccessToken", "NO-ACCESS-TOKEN",
                "RefreshToken", "NO-REFRESH-TOKEN",
                "ExpiresIn", "3600",
                "TokenType", "Bearer");

    }

}
