package com.geez14.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    // allow only user role to access the api!
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(request -> request.requestMatchers("/activities/**").hasRole("USER")).httpBasic(Customizer.withDefaults()).csrf(CsrfConfigurer::disable);
        http.authorizeHttpRequests(request -> request.requestMatchers("/admin/**").hasRole("ADMIN")).httpBasic(Customizer.withDefaults()).csrf(CsrfConfigurer::disable);
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    UserDetailsService testOnlyUsers(PasswordEncoder passwordEncoder) {
        User.UserBuilder users = User.builder();
        UserDetails user = users.username("Mxtylish")
                .password(passwordEncoder.encode("password1234"))
                .roles("USER")
                .build();
        UserDetails developer = users.username("admin")
                .password(passwordEncoder.encode("password"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(developer, user);
    }
}
