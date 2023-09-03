package com.example.demo.config;

import com.example.demo.service.OAuth2MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

// 스프링 시큐리티 설정 클래스
@EnableMethodSecurity
@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final OAuth2MemberService oAuth2MemberService;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .cors().and()
                .authorizeRequests()
                .requestMatchers(new AntPathRequestMatcher("/private/**")).authenticated()
                .requestMatchers(new AntPathRequestMatcher("/admin/**")).access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/user/login")
                .loginProcessingUrl("/user/login")
                .defaultSuccessUrl("/success")
                .failureUrl("/user/login")
                .and()
                .logout()
                .logoutUrl("/user/logout")
                .logoutSuccessUrl("/success")
                .and()
                .oauth2Login()
                .loginPage("/login_form")
                .defaultSuccessUrl("/success")
                .userInfoEndpoint()
                .userService(oAuth2MemberService).and().and().build();
    }


}