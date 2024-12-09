package com.sparta.b2b.config;


import com.sparta.b2b.filter.SessionIdFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextHolderFilter;

@Configuration
@EnableWebSecurity(debug = true)
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable());

        http.authorizeHttpRequests((authorization) ->
                authorization.requestMatchers("/api/b2b-members/signup", "/api/b2b-members/login").permitAll()
                        .anyRequest().authenticated()
        );

        http.addFilterBefore(sessionIdFilter(), SecurityContextHolderFilter.class);

        return http.build();
    }

    @Bean
    public SessionIdFilter sessionIdFilter() {
        return new SessionIdFilter();
    }

}
