package com.websecurity.websecurity.auth;

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

        http
                .cors().disable()      // cors 비활성화
                .csrf().disable()      // csrf 비활성화
                .headers().frameOptions().disable();
                //.formLogin().disable() //기본 로그인 페이지 없애기

        http.authorizeRequests()
                    //페이지 권한 설정
                .antMatchers("/", "/members/new").permitAll()
                //home.html, 회원가입 리소스에 접근할 때( / , /members/new ,/hello )를 제외하고 모든 사용자 요청은 인증을 받도록 설정
                //즉, /,/members/new, /hello 가 아닌 다른 리소스 접근 시 formLogin 화면 나타남
                //혹은 .antMatchers("/", "/members/new").anonymous() 도 가능할 듯
                //.antMatchers(HttpMethod.POST,"/*").permitAll() ???
                .anyRequest().authenticated()
            .and()  //로그인 설정
                .formLogin()
            .and()  //로그아웃 설정
                .logout()
                .logoutSuccessUrl("/")
            .and()
                .httpBasic();
                //formLogin 과 httpBasic 동시에 인증을 받도록 설정

        /*
configure(HttpSecurity http)
- HttpSecurity 객체를 이용해 각 요청을 먼저 intercept하여 URL별 인증 여부, login 처리, logout아웃 처리등 다양한 처리를 할 수 있다.

- antMachers : 각 URL 요청에 대한 접근 여부를 설정한다.  ex) /userAccess에 접근할 경우 hasRole()을 통해 USER라는 권한을 가진 유저만 접근할 수 있다. anonymous()은 인증되지 않은 즉, 로그인 되지 않은 사용자만 접근 가능하다.

- formLogin() : spring security에서 제공하는 login form을 이용한다는 뜻. 로그인 성공시 '/'로 리다이렉트

- csrf() : 웹 사이트의 취약점을 이용한 의도치 않은 요청을 통한 공격을 의미한다. 이 기능을 disable한 상태이다.

configure(AuthenticationManagerBuilder auth)
- AuthenticationManagerBuilder 객체를 통해 인증 객체 생성을 기능을 제공한다. MemberService는 UserDetails인터페이스를 상속받은 객체이고 이것을 이용해 로그인 된 사용자의 데이터를 관리한다.
passwordEncoder()는 로그인때 입력한 패스워드를 암호화 처리한다.
         */

        /*
                http.authorizeRequests()
                // 페이지 권한 설정
                                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/myinfo").hasRole("MEMBER")
                .antMatchers("/**").permitAll()
            .and() // 로그인 설정
                                .formLogin()
                .loginPage("/user/login")   //디폴트 위치는 /login 이지만 사용자 정의하여 /user/login
                .defaultSuccessUrl("/user/login/result")
                .permitAll()
            .and() // 로그아웃 설정
                               .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))    //디폴트 위치는 /logout
                .logoutSuccessUrl("/user/logout/result")
                .invalidateHttpSession(true)
            .and()
                // 403 예외처리 핸들링
                               .exceptionHandling().accessDeniedPage("/user/denied");
        */


    }
}
