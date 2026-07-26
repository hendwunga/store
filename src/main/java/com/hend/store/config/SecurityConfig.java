package com.hend.store.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/login", "/actuator/health",
                    "/css/**", "/js/**", "/webjars/**", "/uploads/**",
                    "/h2-console", "/h2-console/**",
                    "/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html",
                    "/api/**"
                ).permitAll()
                .requestMatchers("/categories/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/products/create", "/products/edit", "/products/delete").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/products/create", "/products/edit", "/products/delete").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/products")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
                .logoutRequestMatcher(PathPatternRequestMatcher.pathPattern(HttpMethod.GET, "/logout"))
            )
            .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**", "/api/**"))
            .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
            User.withUsername("admin")
                .password("{noop}admin123")
                .roles("ADMIN")
                .build(),
            User.withUsername("user")
                .password("{noop}user123")
                .roles("USER")
                .build()
        );
    }
}
