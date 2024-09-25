package com.hymns.hymns.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hymns.hymns.service.impl.SecurityUserDetailService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {

    private final SecurityUserDetailService securityUserDetailService;

    // Password encoder bean
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Authentication provider configuration
    @Bean
    public DaoAuthenticationProvider authenticationConfigurer() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(securityUserDetailService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors().and() // Enable CORS
                .csrf().disable() // Disable CSRF for stateless APIs, you may enable this if needed
                .sessionManagement().disable() // Disable session management (stateless API)
                .authorizeHttpRequests()
                .anyRequest().permitAll() // Allow unrestricted access to all URLs
                .and()
                .httpBasic() // Keep basic HTTP security if needed (optional)
                .and()
                .logout()
                .logoutUrl("/user/logout") // Customize the logout URL
                .logoutSuccessHandler(new RestfulLogoutSuccessHandler()) // Handle logout success in a RESTful way
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");

        return httpSecurity.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:3000"); // Frontend URL
        config.addAllowedMethod("*"); // Allows all HTTP methods (GET, POST, etc.)
        config.addAllowedHeader("*"); // Allows all headers
        config.setAllowCredentials(true); // Allow credentials (e.g., cookies)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }

    // Customize authentication success for REST APIs to return JSON instead of redirection
    private static class RestfulAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
            Map<String, String> data = new HashMap<>();
            data.put("message", "Login successful");

            // Check user role for custom logic
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                if (authority.getAuthority().equals("Admin")) {
                    data.put("role", "Admin");
                } else {
                    data.put("role", "User");
                }
            }

            response.getOutputStream().println(new ObjectMapper().writeValueAsString(data));
        }
    }

    // Customize logout success response for RESTful APIs
    private static class RestfulLogoutSuccessHandler implements LogoutSuccessHandler {
        @Override
        public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getOutputStream().println("{\"message\": \"Logout successful\"}");
        }
    }
}
