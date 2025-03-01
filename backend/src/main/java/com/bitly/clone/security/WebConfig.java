package com.bitly.clone.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${frontend.url}")
    String frontEndUrl;

    /**
     * Configures CORS (Cross-Origin Resource Sharing) settings for the application.
     * This method:
     * - Allows cross-origin requests from the frontend URL specified in the application properties.
     * - Supports common HTTP methods: GET, POST, PUT, DELETE, and OPTIONS.
     * - Permits all request headers to ensure flexibility in API interactions.
     * - Enables credentials (e.g., cookies, authorization headers) for cross-origin requests.
     * - Applies these settings to all API endpoints ("/**").
     *
     * @param registry the {@link CorsRegistry} to which the CORS configuration is applied
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(frontEndUrl)
                .allowedMethods("GET","POST","PUT","DELETE","OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}