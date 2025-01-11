package com.example.board.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AddPostDto {

    private String title;
    private String content;
    private List<MultipartFile> postImages = new ArrayList<>();
}
