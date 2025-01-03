package com.example.board.domain;

import com.example.board.dto.AddMemberDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;

    //==생성 메서드==
    public static Member createMember(AddMemberDto memberDto) {
        Member member = new Member();
        member.name = memberDto.getName();

        return member;
    }
}
