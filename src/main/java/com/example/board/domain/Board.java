package com.example.board.domain;

import com.example.board.dto.AddBoardDto;
import com.example.board.dto.UpdateBoardDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    private String title;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    //==생성 메서드==
    public static Board createBoard(Member member, AddBoardDto boardDto) {
        Board board = new Board();
        board.member = member;
        board.title = boardDto.getTitle();
        board.description = boardDto.getDescription();

        return board;
    }

    public void update(UpdateBoardDto boardDto) {
        this.title = boardDto.getTitle();
        this.description = boardDto.getDescription();
    }
}
