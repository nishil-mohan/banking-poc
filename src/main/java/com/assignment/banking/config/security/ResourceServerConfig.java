package com.assignment.banking.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import static java.lang.String.format;


@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers(format("%s/**", "/h2-console")).permitAll()
                .antMatchers(format("/%s", "actuator")).permitAll()
                .antMatchers(format("/%s/**", "actuator")).permitAll()
                .antMatchers(format("%s/**", "oauth")).permitAll()
                // Our private endpoints
                .anyRequest().authenticated();
    }


}