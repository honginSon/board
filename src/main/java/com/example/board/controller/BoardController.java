package com.example.board.controller;

import com.example.board.domain.Member;
import com.example.board.dto.AddBoardDto;
import com.example.board.service.BoardService;
import com.example.board.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final MemberService memberService;
    private final BoardService boardService;

    @GetMapping("/boards/new")
    public String addBoardView(Model model) {
        return "board/add-form";
    }

    @PostMapping("/boards/new")
    public String addBoard(AddBoardDto boardDto, Model model) {

        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        Member findMember = memberService.findOneByName(name);
        boardService.addBoard(findMember.getId(), boardDto);

        return "redirect:/";
    }
}
