package com.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {
  @Bean
  protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(authz -> authz
        .requestMatchers("/**").permitAll()
        .requestMatchers("/css/**").permitAll()
        .requestMatchers("/js/**").permitAll()
        .requestMatchers("/img/**").permitAll()
        .requestMatchers("/").permitAll()
        .requestMatchers("/toInsert").permitAll()
        .requestMatchers("/insert").permitAll()
        .anyRequest().authenticated()
    ).formLogin(login -> login
        .loginPage("/login")
        .loginProcessingUrl("/login")
        .failureUrl("/login?error=true")
        .defaultSuccessUrl("/show-item-list", false)
        .usernameParameter("email")
        .passwordParameter("password")
    ).logout(logout -> logout
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout**"))
        .logoutSuccessUrl("/login")
        .invalidateHttpSession(true)
        .deleteCookies("JSESSIONID")
    ).csrf(csrf -> csrf
            .ignoringRequestMatchers(new AntPathRequestMatcher("/get-user/user-info"))
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
    );

    return http.build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}