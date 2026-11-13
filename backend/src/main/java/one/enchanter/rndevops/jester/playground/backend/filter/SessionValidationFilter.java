package one.enchanter.rndevops.jester.playground.backend.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerExceptionResolver;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import one.enchanter.rndevops.jester.playground.backend.domain.extension.repository.UserSessionRepository;
import one.enchanter.rndevops.jester.playground.backend.service.SessionService;

@Component // Interceptorのほうで行うため、Filterで行わない。
public class SessionValidationFilter extends OncePerRequestFilter {

    @Autowired
    private SessionService sessionService;

    @Autowired
    public UserSessionRepository userSessionRepository;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    List<String> notFilterPath = List.of("/api/v1/health", "/api/v1/session/login");

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return notFilterPath.contains(path);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null) {
            // filterから例外をthrowする場合、このようにしないとGlobalHandlerでハンドルされないらしい
            resolver.resolveException(request, response, null,
                    new ResponseStatusException(HttpStatus.UNAUTHORIZED, "認証されていません。"));
            return;
        }

        String sessionId = session.getId();

        sessionService.createAuthentication(sessionId);

        filterChain.doFilter(request, response);
    }
}
