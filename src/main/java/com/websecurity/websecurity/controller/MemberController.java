package com.websecurity.websecurity.controller;

import com.websecurity.websecurity.domain.Member;
import com.websecurity.websecurity.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        //비밀번호 암호화
        String encodePassword = passwordEncoder.encode(form.getPassword());
        member.setPassword(encodePassword);

        memberService.join(member);

        return "redirect:/";
    }

    //Spring security 가 대신하므로 구현 불필요
    @GetMapping("/members/signIn")
    public String signInForm() {
        //return "members/signInForm";
        return "redirect:/";
    }

/*

    @PostMapping("/members/signIn")
    public String signIn(MemberForm form) {
        return "redirect:/";
    }
*/

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

}
