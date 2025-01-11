package com.example.board.service;

import com.example.board.domain.Board;
import com.example.board.domain.Member;
import com.example.board.domain.Post;
import com.example.board.dto.AddPostDto;
import com.example.board.dto.UpdatePostDto;
import com.example.board.exception.CustomErrorCode;
import com.example.board.exception.CustomException;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.MemberRepository;
import com.example.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final PostImageService postImageService;

    @Transactional
    public Long addPost(Long boardId, AddPostDto postDto) {

        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        Board findBoard = optionalBoard.orElseThrow(() -> new CustomException(CustomErrorCode.BOARD_NOT_FOUND));

        Optional<Member> optionalMember = memberRepository.findById(findBoard.getMember().getId());
        Member findMember = optionalMember.orElseThrow(() -> new CustomException(CustomErrorCode.MEMBER_NOT_FOUND));

        Post post = Post.createPost(findMember, findBoard, postDto);
        Post savedPost = postRepository.save(post);

        postImageService.addImages(savedPost.getId(), postDto);

        return savedPost.getId();
    }

    @Transactional
    public void updatePost(Long postId, UpdatePostDto postDto) {

        Optional<Post> optionalPost = postRepository.findById(postId);
        Post post = optionalPost.orElseThrow(() -> new CustomException(CustomErrorCode.POST_NOT_FOUND));

        post.update(postDto);
    }

    public List<Post> getPostList(Long boardId) {
        return postRepository.findAllByBoardId(boardId);
    }

    public Post getPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new CustomException(CustomErrorCode.POST_NOT_FOUND));
    }
}
