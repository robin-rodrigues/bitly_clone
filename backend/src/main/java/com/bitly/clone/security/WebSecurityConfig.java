package com.bitly.clone.security;

import com.bitly.clone.security.jwt.JwtAuthenticationFilter;
import com.bitly.clone.service.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class WebSecurityConfig {

    private UserDetailsServiceImpl userDetailsService;

    /**
     * Creates and returns a JWT authentication filter bean.
     * - Handles the extraction and validation of JWT tokens from requests.
     * - Ensures authenticated requests have valid tokens before proceeding.
     *
     * @return an instance of JwtAuthenticationFilter
     */
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    /**
     * Provides a password encoder bean using BCrypt.
     * - Encrypts passwords before storing them in the database.
     * - Ensures secure password hashing for authentication.
     *
     * @return a BCryptPasswordEncoder instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures and provides an authentication manager bean.
     * - Retrieves the authentication manager from the provided configuration.
     * - Used for authenticating users during login attempts.
     *
     * @param authenticationConfiguration the authentication configuration
     * @return an instance of AuthenticationManager
     * @throws Exception if authentication manager retrieval fails
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Configures and returns a DaoAuthenticationProvider.
     * - Uses a custom UserDetailsService to load user authentication details.
     * - Sets a password encoder to securely handle password verification.
     *
     * @return a configured DaoAuthenticationProvider for authentication
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Configures the security filter chain for the application.
     * - Disables CSRF protection since JWT is used for authentication.
     * - Defines authorization rules:
     * 1. Allows public access to authentication-related endpoints.
     * 2. Requires authentication for URL-related endpoints.
     * 3. Permits public access to short URL redirection.
     * 4. Ensures all other requests require authentication.
     * - Sets the custom authentication provider.
     * - Adds the JWT authentication filter before the UsernamePasswordAuthenticationFilter
     * to handle JWT-based authentication before standard login processing.
     *
     * @param http the HttpSecurity object to configure security settings
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/urls/**").authenticated()
                        .requestMatchers("/{shortUrl}").permitAll()
                        .anyRequest().authenticated()
                );

        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
