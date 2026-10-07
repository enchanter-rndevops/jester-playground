package dev.enchander.rndevops.jester.playground.backend.interceptor;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

import dev.enchander.rndevops.jester.playground.backend.domain.extension.entity.UserSessionEntity;
import dev.enchander.rndevops.jester.playground.backend.domain.extension.repository.UserSessionRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SessionValidationInterceptor implements HandlerInterceptor {

    @Autowired
    private UserSessionRepository userSessionRepository;

    private static final List<String> notFilterPath = List.of("/api/v1/login", "/api/v1/health", "/api/v1/session",
            "/api/v1/localauth");

    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response,
            Object handler) {

        String path = request.getRequestURI();
        if (notFilterPath.contains(path)) {
            return true;
        }

        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "認証されていません。");
        }

        String sessionId = session.getId();

        Optional<UserSessionEntity> userSession = userSessionRepository.validSession(sessionId, LocalDateTime.now());

        if (userSession.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "認証されていません。");
        }

        UserSessionEntity entity = userSession.get();

        log.debug("User Session Entity: {}", entity);

        // SecurityContext に認証情報をセット
        PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(
                entity.getSub(), null, Collections.emptyList());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        session.setAttribute(
                org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());

        return true;
    }
}
