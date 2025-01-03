package com.example.board.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomErrorCode {

    /**
     * 404 NOT_FOUND
     */
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 회원이 존재하지 않습니다"),
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 게시판이 존재하지 않습니다"),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 게시글이 존재하지 않습니다"),
    ;

    private final HttpStatus status;
    private final String message;
}
