package dev.enchander.rndevops.jester.playground.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import dev.enchander.rndevops.jester.playground.backend.Utility;
import dev.enchander.rndevops.jester.playground.backend.constant.Severity;
import dev.enchander.rndevops.jester.playground.backend.repository.records.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import tools.jackson.databind.ObjectMapper;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

    /*
     *
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        http.csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authorizeHttpRequests(auth -> auth
                        // パスによる認可の設定。ここに書いてしまうと、再デプロイでないと反映されない。
                        .requestMatchers("/api/v1/health").permitAll()
                        .requestMatchers("/api/v1/hello").permitAll() // 確認用。後で削除する。
                        .requestMatchers("/api/v1/session").permitAll()
                        .requestMatchers("/api/v1/test/session").permitAll() // ローカル動作確認用。ローカル時のみ有効。
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
                        }));

        return http.build();
    }
}
