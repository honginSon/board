package com.example.board.controller;

import com.example.board.domain.Post;
import com.example.board.dto.AddPostDto;
import com.example.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/posts/new")
    public String addPostView(@RequestParam("boardId") Long boardId, Model model) {

        model.addAttribute("boardId", boardId);

        return "post/add-form";
    }

    @PostMapping("/posts/new")
    public String addPost(@RequestParam("boardId") Long boardId, AddPostDto postDto, Model model) {

        postService.addPost(boardId, postDto);

        return "redirect:/boards/" + boardId;
    }

    @GetMapping("/posts/{id}")
    public String postView(@PathVariable Long id, Model model) {

        Post findPost = postService.getPost(id);

        model.addAttribute("boardId", findPost.getBoard().getId());
        model.addAttribute("post", findPost);

        return "post/post-detail";
    }
}
