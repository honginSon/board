package com.example.board.service;

import com.example.board.domain.Board;
import com.example.board.domain.Member;
import com.example.board.dto.AddBoardDto;
import com.example.board.dto.UpdateBoardDto;
import com.example.board.exception.CustomErrorCode;
import com.example.board.exception.CustomException;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long addBoard(Long memberId, AddBoardDto boardDto) {

        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Member findMember = optionalMember.orElseThrow(() -> new CustomException(CustomErrorCode.MEMBER_NOT_FOUND));

        Board board = Board.createBoard(findMember, boardDto);

        return boardRepository.save(board).getId();
    }

    @Transactional
    public void updateBoard(Long boardId, UpdateBoardDto boardDto) {

        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        Board findBoard = optionalBoard.orElseThrow(() -> new CustomException(CustomErrorCode.BOARD_NOT_FOUND));

        findBoard.update(boardDto);
    }

    //Todo: 페이지네이션
    public List<Board> getBoardList() {
        return boardRepository.findAll();
    }

}
