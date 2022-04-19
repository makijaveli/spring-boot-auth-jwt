package com.github.makijaveli.springbootjwtauth.config.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * 1. @EnableWebSecurity -  This allows us to enable security in our API.
 * 2. WebSecurityConfigurerAdapter - WebSecurityConfigurerAdapter: Allows us to override the default behavior of the security provided by Spring.
 * 3. CORS: We enable the default configuration that we can overwrite through the CorsFilter bean.
 * 4. SessionCreationPolicy: Defines the API as Stateless avoiding the creation of HTTPSession, and we disable the use of cookies.
 * 5. CSRF: By configuring the API as stateless we do not need the use of cookies.
 * 6. Configure the API response in case of an authentication error.
 * 7. We declare as public the methods that allow the login and obtaining of JWT tokens. And we implement the security for the rest of the methods.
 * 8. We add the filter to check and validate if the request has a JWT token.
 */
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenFilter jwtTokenFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Enable CORS and disable CSRF
        http = http
                .cors()
                .and()
                .csrf().disable();

        // Set session management to stateless
        http = http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();

        // Set unauthorized requests exception handler
        http = http
                .exceptionHandling()
                .authenticationEntryPoint((request, response, ex) -> {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
                })
                .and();

        // Set permissions on endpoints
        http.authorizeRequests()
                // Our public endpoints
                .antMatchers("/login", "/refresh-token")
                .permitAll()
                // Our private endpoints
                .anyRequest()
                .authenticated();

        // Add JWT token filter.
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

    }

    // Used by spring security if CORS is enabled
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        config.setAllowedHeaders(Arrays.asList("authorization",  "Authorization",
                "content-type","Content-Type", "Access-Control-Allow-Origin",
                "Access-Control-Allow-Headers", "Access-Control-Allow-Methods"));
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
