package one.enchanter.rndevops.jester.playground.backend.controller;

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

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import one.enchanter.rndevops.jester.playground.backend.constant.Severity;
import one.enchanter.rndevops.jester.playground.backend.service.SessionService;

@Profile({ "local", "test", "default" })
@RestController
@RequestMapping("/api")
@Slf4j
public class UnauthSessionController {

    @Autowired(required = false)
    private SessionService sessionService;

    @Autowired(required = false)
    private LocalTokenVerifier tokenVerifier;

    @PostMapping("/v1/session/login")
    public ApiResponse<Void> unauthSession(HttpServletRequest request, HttpServletResponse response,
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
        return new ApiResponse<Void>(Severity.SUCCESS, "OK");
    }

}
