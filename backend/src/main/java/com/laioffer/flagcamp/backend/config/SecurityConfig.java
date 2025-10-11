package com.laioffer.flagcamp.backend.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 新增这个 Bean 来定义安全规则链
     * @param http HttpSecurity 对象用于配置安全规则
     * @return 构建好的 SecurityFilterChain
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 启用 CORS
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // 1. 关闭 CSRF 保护：因为我们是无状态的 REST API，通常不需要 CSRF
                .csrf(csrf -> csrf.disable())

                // 2. 定义授权规则
                .authorizeHttpRequests(auth -> auth
                        // 允许所有访问 "/api/" 路径下的请求，无需身份验证
                        .requestMatchers("/api/**").permitAll()

                        // 任何其他请求都需要身份验证（这是一个好的安全默认设置）
                        .anyRequest().authenticated()
                );

        return http.build();
    }

    // ✅ CORS 配置
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList(
            "http://localhost:5173", 
            "http://localhost:3000",
            "http://127.0.0.1:5173"
        )); 
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS","PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList(
            "Authorization", 
            "Content-Type", 
            "Access-Control-Allow-Origin",
            "Access-Control-Allow-Credentials"
        ));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }
}