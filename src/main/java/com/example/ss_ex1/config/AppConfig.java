package com.example.ss_ex1.config;

import com.example.ss_ex1.filter.AuthenticationLoggingFilter;
import com.example.ss_ex1.filter.RequestValidationFilter;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
public class AppConfig {
    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;
    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(dbUrl);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);
        System.out.println(dataSource.getJdbcUrl());
        return dataSource;
    }

    @Bean
    public JdbcUserDetailsManager userDetailsManager() {
        return new JdbcUserDetailsManager(dataSource());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                .requestMatchers(HttpMethod.GET, "/test/hello").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/test/hello").permitAll()
                .requestMatchers(HttpMethod.GET, "/test/good").permitAll()
                .anyRequest().authenticated());
        http.headers(Customizer.withDefaults());
        http.sessionManagement(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults());
        http.addFilterBefore(new RequestValidationFilter(), BasicAuthenticationFilter.class);
        http.addFilterAfter(new AuthenticationLoggingFilter(), BasicAuthenticationFilter.class);
        http.formLogin(Customizer.withDefaults());
        http.anonymous(Customizer.withDefaults());
        http.csrf(AbstractHttpConfigurer::disable);
        http.userDetailsService(userDetailsManager());
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
