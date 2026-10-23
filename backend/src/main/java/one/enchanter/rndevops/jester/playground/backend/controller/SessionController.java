package one.enchanter.rndevops.jester.playground.backend.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import one.enchanter.rndevops.jester.playground.backend.constant.Severity;
import one.enchanter.rndevops.jester.playground.backend.exception.ForbiddenOperationException;
import one.enchanter.rndevops.jester.playground.backend.properties.CognitoProperties;
import one.enchanter.rndevops.jester.playground.backend.service.SessionService;

record SessionRequestBody(
        String idToken) {
}

@RestController
@RequestMapping("/api")
@Slf4j
@Profile({ "aws" })
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private CognitoTokenVerifier tokenVerifier;

    @PostMapping("/v1/session")
    public ResponseEntity<?> session(HttpServletRequest request, HttpServletResponse response,
            @RequestBody SessionRequestBody body) throws IOException {
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

/**
 * IDTokenを検証する。
 */
@Component
@Slf4j
@Profile({ "aws" })
class CognitoTokenVerifier {

    private CognitoProperties cognitoProperties;

    private final JwtDecoder jwtDecoder;

    public CognitoTokenVerifier(CognitoProperties cognitoProperties) {
        this.cognitoProperties = cognitoProperties;
        this.jwtDecoder = JwtDecoders.fromIssuerLocation(cognitoProperties.issuer());
    }

    public Map<String, Object> verify(String idToken) {

        Jwt jwt = jwtDecoder.decode(idToken);

        log.debug("JWT Claims: " + jwt.getClaims());

        // iss, aud(clientid)の検証をここで行う。
        if (!cognitoProperties.audience().equals(jwt.getAudience().getFirst())) {
            throw new ForbiddenOperationException("Invalid token");
        }

        if (!cognitoProperties.issuer().equals(jwt.getIssuer().toString())) {
            throw new ForbiddenOperationException("Invalid token");
        }

        return jwt.getClaims();
    }
}
