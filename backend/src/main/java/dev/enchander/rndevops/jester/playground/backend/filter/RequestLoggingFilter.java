package dev.enchander.rndevops.jester.playground.backend.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

/**
 * アクセスログを出力する。
 * このFilterよりも前に、Spring SecurityのFilterが実行されるため、Spring
 * Securityで未認証となった場合、このFilterは実行されない。
 */
@Component
public class RequestLoggingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String uri = req.getRequestURI();
        String userAgent = req.getHeader("User-Agent");
        String remoteAddr = req.getRemoteAddr();
        System.out.println("Access detected -> IP: " + remoteAddr + ", User-Agent: " + userAgent);
        chain.doFilter(request, response);
    }
}