package com.websecurity.websecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Id : user, PW : e7cb4f74-94d4-4741-877c-acdf4c76f486 (it changes for each reload)
     * Using generated security password: e7cb4f74-94d4-4741-877c-acdf4c76f486
     */
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //Security Customizing
    @Override
    protected void configure(HttpSecurity http) throws Exception {

/*
        http
                .cors().disable()      // cors 비활성화
                .csrf().disable()      // csrf 비활성화
                .formLogin().disable() //기본 로그인 페이지 없애기
                .headers().frameOptions().disable();
*/
        http
                .cors().disable()      // cors 비활성화
                .csrf().disable()      // csrf 비활성화
                .headers().frameOptions().disable();

        http.authorizeRequests()
                .antMatchers("/", "/members/new").permitAll()
                //home.html, 회원가입 리소스에 접근할 때( / , /members/new ,/hello )를 제외하고 모든 사용자 요청은 인증을 받도록 설정
                //즉, /,/members/new, /hello 가 아닌 다른 리소스 접근 시 formLogin 화면 나타남
                //.antMatchers(HttpMethod.POST,"/*").permitAll() ???
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic();
                //formLogin 과 httpBasic 동시에 인증을 받도록 설정


    }
}
