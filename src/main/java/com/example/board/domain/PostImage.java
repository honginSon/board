package com.example.board.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostImage {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String storeFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;


    public static PostImage create(String storeFilename, Post post) {
        PostImage postImage = new PostImage();
        postImage.storeFileName = storeFilename;
        postImage.post = post;

        return postImage;
    }
}
