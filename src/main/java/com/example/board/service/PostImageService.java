package com.example.board.service;

import com.example.board.domain.Post;
import com.example.board.domain.PostImage;
import com.example.board.dto.AddPostDto;
import com.example.board.exception.CustomErrorCode;
import com.example.board.exception.CustomException;
import com.example.board.repository.PostImageRepository;
import com.example.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostImageService {

    private final PostImageRepository postImageRepository;
    private final PostRepository postRepository;

    @Value("${post.image.dir}")
    private String fileDir;

    public String getFullPath(String storeFilename) {
        return fileDir + storeFilename;
    }

    @Transactional
    public void addImages(Long postId, AddPostDto postDto) {

        Post findPost = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.POST_NOT_FOUND));

        List<MultipartFile> postImages = postDto.getPostImages();

        //Todo: postImages 사이즈가 0인 경우 처리
        if (postImages.isEmpty()) {
            return;
        }

        for (MultipartFile image : postImages) {
            PostImage postImage = storeFile(image, findPost);

            postImageRepository.save(postImage);
        }
    }

    public PostImage storeFile(MultipartFile multipartFile, Post post) {

        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename(); // 본래 파일명
        String storeFilename = createStoreFilename(originalFilename); // uuid 파일명

        // 지정 경로에 저장
        try {
            multipartFile.transferTo(new File(getFullPath(storeFilename)));
        } catch (IOException e) {
            log.error("이미지 저장 에러 : {}", e.getMessage());
        }

        return PostImage.create(storeFilename, post);
    }

    private String createStoreFilename(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    // 확장자 추출
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    public List<PostImage> getImagesByPost(Long postId) {
        return postImageRepository.findByPost_Id(postId);
    }
}
