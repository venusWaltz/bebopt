package com.bebopt.app.security;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * The {@code SecurityConfiguration} class configures the security settings for this application.
 */
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends VaadinWebSecurity {

    /**
     * Configures the HttpSecurity settings to define the security constraints.
     * 
     * @param http The HttpSecurity object to be configured.
     * @throws Exception If an error occurs during configuration.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests().requestMatchers(new AntPathRequestMatcher("/images/*.png")).permitAll();
        http.authorizeHttpRequests().requestMatchers(new AntPathRequestMatcher("/callback*")).anonymous();
        http.authorizeHttpRequests().requestMatchers(new AntPathRequestMatcher("/line-awesome/**/*.svg")).permitAll();
        super.configure(http);
    }
}
