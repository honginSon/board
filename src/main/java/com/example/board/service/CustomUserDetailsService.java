package com.example.board.service;

import com.example.board.domain.Member;
import com.example.board.dto.CustomUserDetails;
import com.example.board.exception.CustomErrorCode;
import com.example.board.exception.CustomException;
import com.example.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Member> optionalMember = memberRepository.findByName(username);
        Member findMember = optionalMember.orElseThrow(() -> new CustomException(CustomErrorCode.MEMBER_NOT_FOUND));

        return new CustomUserDetails(findMember);
    }
}
