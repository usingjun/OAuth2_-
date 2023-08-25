package com.example.demo.config;

import com.example.demo.auth.PrincipalOauth2UserService;
import com.nimbusds.oauth2.sdk.auth.ClientAuthenticationMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/user/**").authenticated()
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/loginForm")        // 인증이 필요한 URL에 접근하면 /loginForm으로 이동
                .usernameParameter("id")        // 로그인 시 form에서 가져올 값(id, email 등이 해당)
                .passwordParameter("pw")        // 로그인 시 form에서 가져올 값
                .loginProcessingUrl("/login")        // 로그인을 처리할 URL 입력
                .defaultSuccessUrl("/")            // 로그인 성공하면 "/" 으로 이동
                .failureUrl("/loginForm")        //로그인 실패 시 /loginForm으로 이동
                .and()
                .logout()                    // logout할 경우
                .logoutUrl("/logout")            // 로그아웃을 처리할 URL 입력
                .logoutSuccessUrl("/")    // 로그아웃 성공 시 "/"으로 이동
                .and()                    //추가
                .oauth2Login()                // OAuth2기반의 로그인인 경우
                .loginPage("/loginForm")        // 인증이 필요한 URL에 접근하면 /loginForm으로 이동
                .defaultSuccessUrl("/")            // 로그인 성공하면 "/" 으로 이동
                .failureUrl("/loginForm")        // 로그인 실패 시 /loginForm으로 이동
                .userInfoEndpoint()            // 로그인 성공 후 사용자정보를 가져온다
                .userService(principalOauth2UserService);    //사용자정보를 처리할 때 사용한다

    }




}

