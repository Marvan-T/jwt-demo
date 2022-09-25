package dev.marvan.jwtdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService users() { //Creates an in memory user, so we don't have to use the default one provided by spring
        return new InMemoryUserDetailsManager(
                User.withUsername("marvan")
                        .password("{noop}password")// NoOpPasswordEncoder - This is a password encoder that does nothing and is useful for testing (don't use in production)
                        .authorities("read")
                        .build()
        );

    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(auth -> auth.anyRequest().authenticated()) //this is us explicitly saying to authenticate every request we are overwriting the default security config
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // turn off session management. Don't need sessions to be stateful as we are dealing with rest api's here and tokens
                .httpBasic(Customizer.withDefaults()) // this is way to authenticate users (can use something like a form login like the default that spring security had). Here we are using httpBasic
                .build();

        /* Never disable CSRF when having session management enabled - this is a recipe for CSRF attacks */
    }
}
