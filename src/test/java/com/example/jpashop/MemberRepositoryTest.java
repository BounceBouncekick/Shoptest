package com.example.jpashop;


import com.example.jpashop.entity.Member;
import com.example.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
@ActiveProfiles("test")
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Rollback(false)
    public void testMember() throws Exception{
        //given
        Member member = new Member();
        member.setUsername("memberA");

        //when
        Member savedMember = memberRepository.save(member);  // 저장
        Member findMember = memberRepository.findById(savedMember.getId()).orElse(null);  // 조회


        //then
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
    }



}