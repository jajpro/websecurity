package com.websecurity.websecurity.service;

import com.websecurity.websecurity.auth.MyUserDetail;
import com.websecurity.websecurity.domain.Member;
import com.websecurity.websecurity.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    //UserDetailsService 의 loadUserByUsername 은 사용자 인증 처리를 할 시,
    // 사용자가 보내온 인증 정보와 DB에 적재된 사용자 로그인 데이터의 일치 여부를 확인하는 역할

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입
     * */
    @Transactional(readOnly = false)
    public long join(Member member) {

        validateDuplicateMember(member); //중복회원 검증

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 로그인 검증
     */
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Optional<Member> byUserName = memberRepository.findByName(name);
        Member member = byUserName.orElseThrow(() -> new UsernameNotFoundException(name));
        return new User(member.getName(), member.getPassword(), authorities());

        /*
        Member member = memberRepository.findByName(name);
        return new MyUserDetail(member);
        */

    }

    private Collection<? extends GrantedAuthority> authorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    /**
     * 전체회원조회
     * */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 아이디조회
     * */
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

    /**
     * 이름조회
     * */
    public Optional<Member> findByName(String name) {
        return memberRepository.findByName(name);
    }


}
