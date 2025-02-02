package com.craigwoodcock.fishingapp.config;

import com.craigwoodcock.fishingapp.security.CustomAccessDeniedHandler;
import com.craigwoodcock.fishingapp.security.CustomAuthenticationEntryPoint;
import com.craigwoodcock.fishingapp.security.JwtAuthenticationFilter;
import com.craigwoodcock.fishingapp.service.CustomUserDetailsService;
import com.craigwoodcock.fishingapp.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Security configuration class that defines the security settings for both API and web interfaces
 * of the fishing application. This class configures authentication, authorization, session management,
 * and other security-related aspects of the application.
 *
 * @author Craig Woodcock
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtils jwtUtils;
    private final ObjectMapper objectMapper;

    /**
     * Constructs a new SecurityConfig with the necessary dependencies.
     *
     * @param customUserDetailsService Service for handling user details and authentication
     * @param jwtUtils                 Utility class for JWT token operations
     * @param objectMapper             Jackson ObjectMapper for JSON serialization/deserialization
     */
    @Autowired
    public SecurityConfig(CustomUserDetailsService customUserDetailsService, JwtUtils jwtUtils, ObjectMapper objectMapper) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtUtils = jwtUtils;
        this.objectMapper = objectMapper;
    }

    /**
     * Creates and configures the password encoder bean for secure password hashing.
     *
     * @return BCryptPasswordEncoder instance for password encryption
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Creates and configures the custom authentication entry point bean.
     * This entry point handles unauthorized access attempts to secured endpoints.
     *
     * @return CustomAuthenticationEntryPoint instance
     */
    @Bean
    public CustomAuthenticationEntryPoint customAuthenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint(objectMapper);
    }

    /**
     * Creates and configures the custom access denied handler bean.
     * This handler manages responses when authenticated users attempt to access
     * resources they don't have permission for.
     *
     * @return CustomAccessDeniedHandler instance
     */
    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler(objectMapper);
    }

    /**
     * Configures security settings for the API endpoints (/api/**).
     * This configuration implements stateless JWT-based authentication for API requests.
     *
     * @param http HttpSecurity instance to be configured
     * @return Configured SecurityFilterChain for API security
     * @throws Exception if an error occurs during security configuration
     */
    @Bean
    @Order(1)
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/**")
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/auth/**").permitAll()  // Public authentication endpoints
                        .anyRequest().authenticated()                  // All other API endpoints require authentication
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // No session management for API
                .addFilterBefore(new JwtAuthenticationFilter(customUserDetailsService, jwtUtils), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(customAuthenticationEntryPoint())
                        .accessDeniedHandler(customAccessDeniedHandler())
                )
                .csrf(csrf -> csrf.disable());  // Disable CSRF for API endpoints as they use JWT

        return http.build();
    }

    /**
     * Configures security settings for web interface endpoints.
     * This configuration implements form-based authentication with session management
     * for the web application.
     *
     * @param http HttpSecurity instance to be configured
     * @return Configured SecurityFilterChain for web security
     * @throws Exception if an error occurs during security configuration
     */
    @Bean
    @Order(2)
    public SecurityFilterChain webSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/login", "/index", "/register", "/js/**", "/css/**", "/error", "/error/**", "/docs", "/actuator/health", "/swagger-ui/", "/swagger-ui/**").permitAll()  // Public resources
                        .requestMatchers("/admin/**").hasRole("ADMIN")  // Admin-only endpoints
                        .anyRequest().hasRole("USER")                   // All other endpoints require USER role
                )
                .formLogin(form -> form
                        .loginPage("/login")                    // Custom login page
                        .defaultSuccessUrl("/dashboard")        // Redirect after successful login
                        .failureUrl("/login?error=true")       // Redirect after failed login
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login?logout")      // Redirect after logout
                        .deleteCookies("JSESSIONID")           // Clean up session cookie
                        .invalidateHttpSession(true)           // Invalidate session
                        .permitAll())
                .exceptionHandling(exceptions -> exceptions
                        .accessDeniedPage("/error/403")  // Handle forbidden access attempts
                )
                .userDetailsService(customUserDetailsService);

        return http.build();
    }
}