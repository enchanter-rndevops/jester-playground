package one.enchanter.rndevops.jester.playground.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import one.enchanter.rndevops.jester.playground.backend.Utility;
import one.enchanter.rndevops.jester.playground.backend.constant.Severity;
import one.enchanter.rndevops.jester.playground.backend.controller.ApiResponse;
import one.enchanter.rndevops.jester.playground.backend.filter.SessionValidationFilter;
import tools.jackson.databind.ObjectMapper;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

    private final ObjectMapper mapper = new ObjectMapper();

    // @Autowired
    // private final SessionValidationFilter sessionValidationFilter;

    /*
     *
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, SessionValidationFilter sessionValidationFilter)
            throws Exception {

        http.csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/health").permitAll()
                        .requestMatchers("/api/v1/session/login").permitAll()
                        .requestMatchers("/api/v1/cognito/login").permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling(exceptions -> exceptions
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            // アクセス拒否時の処理
                            String errorId = Utility.generateErrorId("E");
                            log.warn("[{}] アクセス拒否（権限不足）: {}", errorId,
                                    accessDeniedException.getMessage());
                            ApiResponse<Void> responseBody = new ApiResponse<>(
                                    Severity.ERROR,
                                    "権限がありません（エラーID: " + errorId + "）");
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.setContentType("application/json;charset=UTF-8");
                            response.getWriter()
                                    .write(mapper.writeValueAsString(responseBody));
                        }).authenticationEntryPoint((request, response, authException) -> {
                            // 認証失敗時の処理
                            String errorId = Utility.generateErrorId("E");
                            log.warn("[{}] アクセス拒否: {}", errorId,
                                    authException.getMessage());
                            ApiResponse<Void> responseBody = new ApiResponse<>(
                                    Severity.ERROR,
                                    "認証されていません（エラーID: " + errorId + "）");
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json;charset=UTF-8");
                            response.getWriter()
                                    .write(mapper.writeValueAsString(responseBody));
                        }))
                .addFilterBefore(
                        sessionValidationFilter,
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
