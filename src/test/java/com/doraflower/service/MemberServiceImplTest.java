package com.doraflower.service;

import com.doraflower.dto.MemberFormDTO;
import com.doraflower.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
@Log4j2
@RequiredArgsConstructor
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberServiceImplTest {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원가입 테스트")
    public void saveMemberTest() {

        MemberFormDTO theMemberDTO = MemberFormDTO.builder()
                .email("test@email.com")
                .name("Dora")
                .address("Daegu")
                .password("1234")
                .build();

        // 비밀번호 인코딩 및 회원 생성
        Member member = Member.createMember(theMemberDTO, passwordEncoder);

        // 회원 저장
        Member savedMember = memberService.saveMember(member);

        // 로그로 저장된 Member 확인
        // 로그로 저장된 Member 확인
        log.info("Saved Member: {}", savedMember);
        assertNotNull(savedMember.getMemberId()); // ID가 null이 아닌지 확인
        assertEquals("test@email.com", savedMember.getEmail());

    }

    @Test
    @DisplayName("중복 회원 가입 테스트")
    public void saveDuplicateMember() {

        Member member1 = new Member(null,"Dora", "test@email.com", "1234", "Daegu", null);
        Member member2 = new Member(null,"Dora", "test@email.com", "1234", "Daegu", null);

        memberService.saveMember(member1);

        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            memberService.saveMember(member2);
        });

        assertTrue(thrown.getMessage().contains("Already Registered Member!"));
    }
}