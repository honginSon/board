package com.example.board.controller;

import com.example.board.domain.Member;
import com.example.board.domain.Post;
import com.example.board.dto.AddBoardDto;
import com.example.board.service.BoardService;
import com.example.board.service.MemberService;
import com.example.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final MemberService memberService;
    private final BoardService boardService;
    private final PostService postService;

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

    @GetMapping("/boards/{id}")
    public String boardView(@PathVariable Long id, Model model) {

        model.addAttribute("boardId", id);

        List<Post> posts = postService.getPostList(id);
        model.addAttribute("posts", posts);

        return "/post/post-list";
    }
}
