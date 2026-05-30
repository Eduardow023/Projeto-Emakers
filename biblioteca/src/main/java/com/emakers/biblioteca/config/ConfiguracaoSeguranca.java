package com.emakers.biblioteca.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ConfiguracaoSeguranca {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .headers(headers -> headers.frameOptions(frame -> frame.disable()))
            .authorizeHttpRequests(auth -> auth
                // LIBERA: Swagger, Banco H2 E o método POST de salvar pessoas publicamente
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/h2-console/**").permitAll()
                .requestMatchers(org.springframework.http.HttpMethod.POST, "/pessoas").permitAll()
                
                // Bloqueia todo o resto (listar pessoas, mexer em livros, empréstimos, etc)
                .anyRequest().authenticated()
            )
            .httpBasic(org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer::getClass);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Criptografia BCrypt para salvar as senhas das pessoas com segurança no banco
        return new BCryptPasswordEncoder();
    }
}