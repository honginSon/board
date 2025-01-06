package com.example.board.controller;

import com.example.board.dto.AddMemberDto;
import com.example.board.exception.CustomException;
import com.example.board.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String joinView() {
        return "member/join";
    }

    @PostMapping("/members/new")
    public String joinProcess(@Valid AddMemberDto memberDto) {

        try {
            memberService.save(memberDto);
        } catch (CustomException e) {
            log.info(e.getCustomErrorCode().getMessage());
        }

        return "redirect:/";
    }
}
