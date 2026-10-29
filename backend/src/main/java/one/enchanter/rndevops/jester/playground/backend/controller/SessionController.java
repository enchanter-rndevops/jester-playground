package one.enchanter.rndevops.jester.playground.backend.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import one.enchanter.rndevops.jester.playground.backend.constant.Severity;
import one.enchanter.rndevops.jester.playground.backend.service.SessionService;

record SessionRequestBody(
        String idToken) {
}

@RestController
@RequestMapping("/api")
@Slf4j
@Profile({ "aws" })
@RequiredArgsConstructor
public class SessionController {

    @Autowired(required = false)
    private SessionService sessionService;

    @Autowired(required = false)
    private TokenVerifier tokenVerifier;

    @PostMapping("/v1/session/login")
    public ResponseEntity<ApiResponse<Void>> login(HttpServletRequest request, HttpServletResponse response,
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
        return ResponseEntity.ok(new ApiResponse<Void>(Severity.SUCCESS, "OK"));
    }

    @PostMapping("/v1/session/logout")
    public ApiResponse<Void> logout(HttpServletRequest request, HttpServletResponse response,
            @RequestBody SessionRequestBody body) throws IOException {

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        return new ApiResponse<Void>(Severity.SUCCESS, "OK");

    }

}
