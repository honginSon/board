package com.example.board.controller;

import com.example.board.domain.Board;
import com.example.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final BoardService boardService;

    @GetMapping("/")
    public String homePage(Model model) {
        List<Board> boardList = boardService.getBoardList();
        model.addAttribute("boards", boardList);

        return "index";
    }
}
