package com.busanit501.bootproject.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Log4j2
@Configuration
@RequiredArgsConstructor
public class CustomSecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("---------------------------config---------------------------------");
        http.formLogin(
                formLogin -> formLogin.loginPage("/user/login")
        );

        http.formLogin(formLogin ->
                formLogin.defaultSuccessUrl("/user/profile", true)
        );

        http.authorizeHttpRequests(
                authorizeRequests -> {
                    authorizeRequests.requestMatchers
                            ("/css/**", "/js/**", "/user/login","/user/check-email").permitAll();
                    authorizeRequests.requestMatchers
                            ("/user/profile","user/update","user/delete").authenticated();
                    authorizeRequests.requestMatchers
                            ("/admin/**").hasRole("ADMIN");
                    authorizeRequests.anyRequest().authenticated();
                }
        );

        return http.build();
    }

    // css, js, 등 정적 자원은 시큐리티 필터에서 제외하기
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        log.info("시큐리티 동작 확인 ====webSecurityCustomizer======================");
        return (web) ->
                web.ignoring()
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

}
