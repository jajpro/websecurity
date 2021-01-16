package com.websecurity.websecurity.auth;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class MyUserDetail implements UserDetails {

    /*
    출처 - https://cjw-awdsd.tistory.com/35

    Spring Security 는 사용자 정보를 UserDetails 라는 인터페이스 구현체로 사용한다. 즉, UserDetails 는 사용자 정보 VO 라고 생각하면 된다.

    DAO(Data Access Object)는 실질적인 DB에 접근하는 객체를 말한다. DAO 를 사용하는 이유는 효율적인 커넥션 관리와 보안성 때문이다.
    DTO(Data Transfer Object)는 계층간 데이터 교환을 위한 자바빈즈를 말한다.
    여기서 말하는 계층간의 컨트롤러, 뷰, 비즈니스 계층, 퍼시스턴스 계층을 말하며 각 계층간 데이터 교환을 위한 객체를 DTO 또는 VO 라고 부른다.
    VO(Value Object)는 DTO 와 동일한 개념이지만 Read only 속성을 갖는다.
     */

    private String name;
    private String password;
    //private String auth;

    public MyUserDetail(User user) {
        this.name = user.getUsername();
        this.password = user.getPassword();
        //this.auth = "ROLE_" + user.getRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //return Collections.singletonList(new SimpleGrantedAuthority(this.authorities));
        return null;
    }
    /*
    getAuthorities() : 계정이 가지고 있는 권한 목록 반환
    getPassword() : 계정의 비밀번호 반환
    getUsername() : 계정의 이름 반환
    isAccountNonExpired() : 계정 만료 여부(false = 만료)
    isAccountNonLocked() : 계정 잠김 여부(false = 잠김)
    isCredentialNonExpired() : 계정 비밀번호 만료 여부(false = 만료)
    isEnabled() : 계정 활성화 여부(false = 비활성)
    */

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
