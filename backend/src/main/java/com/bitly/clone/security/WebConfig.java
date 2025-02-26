package com.bitly.clone.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${frontend.url}")
    private String frontEndUrl;

    /**
     * Configures CORS (Cross-Origin Resource Sharing) settings for the application.
     * - Allows requests from the specified frontend URL.
     * - Supports common HTTP methods such as GET, POST, PUT, DELETE, and OPTIONS.
     * - Permits all headers and enables credentials for cross-origin requests.
     * - Sets a maximum age for CORS preflight requests to improve performance.
     *
     * @return a WebMvcConfigurer instance with customized CORS settings
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(frontEndUrl)
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .maxAge(3600);
            }
        };
    }
}
