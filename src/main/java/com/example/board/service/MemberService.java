package com.example.board.service;

import com.example.board.domain.Member;
import com.example.board.dto.AddMemberDto;
import com.example.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long save(AddMemberDto memberDto) {

        Member newMember = Member.createMember(memberDto);

        return memberRepository.save(newMember).getId();
    }
}
