package com.example.board.service;

import com.example.board.domain.Member;
import com.example.board.dto.AddMemberDto;
import com.example.board.exception.CustomErrorCode;
import com.example.board.exception.CustomException;
import com.example.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long save(AddMemberDto memberDto) {

        // 회원 중복 검증
        Optional<Member> optionalMember = memberRepository.findByName(memberDto.getName());
        if (optionalMember.isPresent()) {
            throw new CustomException(CustomErrorCode.DUPLICATED_MEMBER_NAME);
        }

        Member newMember = Member.createMember(memberDto.getName(), passwordEncoder.encode(memberDto.getPassword()));

        return memberRepository.save(newMember).getId();
    }
}
