package com.example.board.service;

import com.example.board.dto.AddMemberDto;
import com.example.board.exception.CustomErrorCode;
import com.example.board.exception.CustomException;
import com.example.board.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void 중복_회원_예외() throws Exception{
        //given
        AddMemberDto member1 = new AddMemberDto();
        member1.setName("son");
        member1.setPassword("123456");
        memberService.save(member1);

        AddMemberDto member2 = new AddMemberDto();
        member2.setName("son");
        member2.setPassword("123456");

        //when
        CustomException result = assertThrows(CustomException.class, () -> memberService.save(member2));

        //then
        assertThat(result.getCustomErrorCode()).isEqualTo(CustomErrorCode.DUPLICATED_MEMBER_NAME);
    }

    @Test
    public void 회원가입성공() throws Exception{
        //given
        AddMemberDto member1 = new AddMemberDto();
        member1.setName("son");
        member1.setPassword("123456");

        //when
        Long saveId = memberService.save(member1);

        //then
        assertThat(memberRepository.findById(saveId).get().getName()).isEqualTo("son");
    }
}