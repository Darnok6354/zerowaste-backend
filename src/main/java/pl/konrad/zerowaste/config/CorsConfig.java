package pl.konrad.zerowaste.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Zezwalamy na wszystkie endpointy zaczynające się od /api/
                .allowedOrigins(
                        "http://localhost:3000", // Standardowy port dla React i Next.js
                        "http://localhost:4200", // Standardowy port dla Angulara
                        "http://localhost:5173"  // Standardowy port dla Vite
                )
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}