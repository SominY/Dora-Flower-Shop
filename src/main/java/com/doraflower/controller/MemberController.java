package com.doraflower.controller;

import com.doraflower.dto.MemberFormDTO;
import com.doraflower.entity.Member;
import com.doraflower.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/new")
    public String memberForm(Model model, Principal principal) {

        if (principal != null) {
            // 로그인된 사용자가 접근 시 다른 페이지로 리디렉션
            return "redirect:/";
        }

        model.addAttribute("memberFormDTO", new MemberFormDTO());
        return "member/memberForm";
    }

    @PostMapping("/new")
    public String memberForm(@Valid MemberFormDTO memberFormDTO,
                             BindingResult bindingResult,
                             Model model) {

        // 유효성 오류시 실행할 메서드
        if (bindingResult.hasErrors()) {
            System.out.println("Binding results: " + bindingResult.toString());
            return "member/memberForm";
        }

        // 이메일 중복 시 예외처리
        try {
            Member member = Member.createMember(memberFormDTO, passwordEncoder);
            memberService.saveMember(member);
        } catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberForm";
        }

        // 성공 시 리다이렉트
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginMember() {
        return "/member/memberLoginForm";
    }

    @GetMapping("/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "Please enter a valid email or password");
        return "/member/memberLoginForm";
    }
}
