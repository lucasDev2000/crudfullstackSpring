package com.exemplo.crudmongo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.config.Customizer;

@Configuration
@EnableMethodSecurity   // <- permite usar @PreAuthorize nos controllers
public class SecurityConfig {

    // 1) Usuários em memória
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {

        // usuário coordenador
        UserDetails coordenador = User.builder()
                .username("coordenador")
                .password(encoder.encode("123"))
                .roles("COORDENADOR")
                .build();

        // usuário aluno
        UserDetails aluno = User.builder()
                .username("aluno")
                .password(encoder.encode("123"))
                .roles("ALUNO")
                .build();

        return new InMemoryUserDetailsManager(coordenador, aluno);
    }

    // 2) encoder de senha
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 3) regras HTTP
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // estamos testando via Postman -> desliga CSRF
            .csrf(csrf -> csrf.disable())
            // libera o H2
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                    // o resto precisa estar logado
                    .anyRequest().authenticated()
            )
            // libera o frame do h2
            .headers(headers -> headers.frameOptions(frame -> frame.disable()))
            // ativa basic auth (é isso que o Postman usa)
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
