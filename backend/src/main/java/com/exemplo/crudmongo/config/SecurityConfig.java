package com.exemplo.crudmongo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) 
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/cursos/**").authenticated()
                .anyRequest().permitAll()
            )
            .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    // Usuários em memória apenas para teste
    @Bean
    public UserDetailsService userDetailsService() {
        var userAluno = User.withUsername("aluno")
                .password("{noop}burro12") // {noop} = sem criptografia
                .roles("ALUNO")
                .build();

        var userCoord = User.withUsername("coordenador")
                .password("{noop}burro34")
                .roles("COORDENADOR")
                .build();

        return new InMemoryUserDetailsManager(userAluno, userCoord);
    }
}