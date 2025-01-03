package com.example.board.domain;

import com.example.board.dto.AddPostDto;
import com.example.board.dto.UpdatePostDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    //==생성 메서드==
    public static Post createPost(Member member, Board board, AddPostDto postDto) {
        Post post = new Post();
        post.member = member;
        post.board = board;
        post.title = postDto.getTitle();
        post.content = postDto.getContent();

        return post;
    }

    public void update(UpdatePostDto postDto) {
        this.title = postDto.getTitle();
        this.content = postDto.getDescription();
    }
}
