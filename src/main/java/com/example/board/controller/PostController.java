package com.example.board.controller;

import com.example.board.domain.Post;
import com.example.board.domain.PostImage;
import com.example.board.dto.AddPostDto;
import com.example.board.service.PostImageService;
import com.example.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostImageService postImageService;

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
        List<PostImage> images = postImageService.getImagesByPost(id);

        model.addAttribute("boardId", findPost.getBoard().getId());
        model.addAttribute("post", findPost);
        model.addAttribute("postImages", images);

        return "post/post-detail";
    }

    @ResponseBody
    @GetMapping("/posts/images/{filename}")
    public Resource downloadImages(@PathVariable String filename) throws MalformedURLException {

        //Todo: 디폴트 이미지 적용
        return new UrlResource("file:" + postImageService.getFullPath(filename));
    }
}
