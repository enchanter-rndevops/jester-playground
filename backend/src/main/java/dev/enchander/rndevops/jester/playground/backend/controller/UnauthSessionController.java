package dev.enchander.rndevops.jester.playground.backend.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;

import dev.enchander.rndevops.jester.playground.backend.constant.Severity;
import dev.enchander.rndevops.jester.playground.backend.repository.records.ApiResponse;
import dev.enchander.rndevops.jester.playground.backend.service.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

record UnauthSessionRequestBody(
        String idToken) {
}

@Profile("local")
@RestController
@RequestMapping("/api")
@Slf4j
public class UnauthSessionController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private LocalTokenVerifier tokenVerifier;

    @PostMapping("/v1/test/session")
    public ResponseEntity<?> unauthSession(HttpServletRequest request, HttpServletResponse response,
            @RequestBody UnauthSessionRequestBody body) throws IOException {
        String idToken = body.idToken();

        Map<?, ?> decoded = tokenVerifier.verify(idToken);

        if (decoded == null || decoded.get("sub") == null) {
            throw new AuthorizationServiceException("認証されていません");
        }

        // sub を取り出す
        String sub = String.valueOf(decoded.get("sub"));

        // セッション発行
        String sessionId = sessionService.createSession(sub);

        log.debug("Session ID:" + sessionId);
        var responseBody = new ApiResponse<Void>(Severity.SUCCESS, "OK");
        return ResponseEntity.ok(responseBody);
    }

}

@Component
@Profile("local")
class LocalTokenVerifier {

    // @Autowired
    // private CognitoProperties cognitoProperties;

    public Map<String, Object> verify(String idToken) {
        try {
            // 署名検証なしで JWT をパース
            // SignedJWT jwt = SignedJWT.parse(idToken);
            JWT jwt = JWTParser.parse(idToken);

            if (jwt == null) {
                throw new RuntimeException("Invalid local token");
            }
            return jwt.getJWTClaimsSet().getClaims();
        } catch (Exception e) {
            throw new RuntimeException("Invalid local token", e);
        }
    }
}
