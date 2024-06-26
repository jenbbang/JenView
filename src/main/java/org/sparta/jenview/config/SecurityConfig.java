package org.sparta.jenview.config;

import jakarta.servlet.http.HttpServletRequest;
import org.sparta.jenview.jwt.BlacklistFilter;
import org.sparta.jenview.jwt.JWTFilter;
import org.sparta.jenview.oauth2.CustomLogoutSuccessHandler;
import org.sparta.jenview.oauth2.CustomSuccessHandler;
import org.sparta.jenview.service.CustomOAuth2UserService;
import org.sparta.jenview.service.JWTUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomSuccessHandler customSuccessHandler;
    private final JWTUtil jwtUtil;
    private final BlacklistFilter blacklistFilter; // BlacklistFilter 주입
    private final CustomLogoutSuccessHandler customLogoutSuccessHandler; // CustomLogoutSuccessHandler 주입

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService, CustomSuccessHandler customSuccessHandler, JWTUtil jwtUtil, BlacklistFilter blacklistFilter, CustomLogoutSuccessHandler customLogoutSuccessHandler) {
        this.customOAuth2UserService = customOAuth2UserService;
        this.customSuccessHandler = customSuccessHandler;
        this.jwtUtil = jwtUtil;
        this.blacklistFilter = blacklistFilter; // BlacklistFilter 초기화
        this.customLogoutSuccessHandler = customLogoutSuccessHandler; // CustomLogoutSuccessHandler 초기화
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {

                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                        CorsConfiguration configuration = new CorsConfiguration();

                        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:8080"));
                        configuration.setAllowedMethods(Collections.singletonList("*"));
                        configuration.setAllowCredentials(true);
                        configuration.setAllowedHeaders(Collections.singletonList("*"));
                        configuration.setMaxAge(3600L);

                        configuration.setExposedHeaders(Collections.singletonList("Set-Cookie"));
                        configuration.setExposedHeaders(Collections.singletonList("Authorization"));

                        return configuration;
                    }
                }));

        //csrf disable
        http.csrf((auth) -> auth.disable());

        //From 로그인 방식 disable
        http.formLogin((auth) -> auth.disable());

        //HTTP Basic 인증 방식 disable
        http.httpBasic((auth) -> auth.disable());

        //JWTFilter 추가
        http.addFilterBefore(new JWTFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        //BlacklistFilter 추가
        http.addFilterBefore(blacklistFilter, JWTFilter.class);

        //oauth2
        http.oauth2Login((oauth2) -> oauth2
                .userInfoEndpoint((userInfoEndpointConfig) -> userInfoEndpointConfig
                        .userService(customOAuth2UserService))
                .successHandler(customSuccessHandler)
        );

        //경로별 인가 작업
        http.authorizeHttpRequests((auth) -> auth
                .requestMatchers("/").permitAll()
                .anyRequest().authenticated());

        //세션 설정 : STATELESS
        http.sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // 로그아웃 설정 추가
        http.logout(logout -> logout
                .logoutUrl("/api/logout") // 로그아웃 요청 URL 설정, 기본값은 "/logout"
                .logoutSuccessUrl("/api/logoutsuccess")
//                .invalidateHttpSession(true) // HTTP 세션 무효화 여부
                .deleteCookies("JSESSIONID") // 로그아웃 시 삭제할 쿠키 설정, 여러 개일 경우 여러 번 호출
//                .logoutSuccessHandler(customLogoutSuccessHandler) // CustomLogoutSuccessHandler 적용
                .permitAll());

        return http.build();
    }
}
