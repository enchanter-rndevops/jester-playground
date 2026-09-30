package dev.enchander.rndevops.jester.playground.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // @Autowired
    // private SessionValidationFilter sessionValidationFilter;

    /*
     *
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authorizeHttpRequests(auth -> auth
                        // パスによる認可の設定。ここに書いてしまうと、再デプロイでないと反映されない。
                        .requestMatchers("/api/v1/health").permitAll()
                        .requestMatchers("/api/v1/hello").permitAll()
                        .requestMatchers("/api/v1/session").permitAll()
                        .requestMatchers("/api/v1/localauth").permitAll()
                        .anyRequest().authenticated());
        // Spring Security の標準認証 Filter の前に自作 Filter を挟む。
        // UsernamePasswordAuthenticationFilterは使わないが、だいたいこのへんにFilterいれとけばいいんじゃね。という目印的な感じ.。
        // Fliterに @Component があると、自動的に追加される。ここにもFilterの登録があると、同じFilterが二回実行されてしまうことに注意。
        // このFilterはInterceptorへ移動。
        // .addFilterBefore(sessionValidationFilter,
        // UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
