package com.doraflower.entity;

import com.doraflower.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberTest {

    @Autowired
    MemberRepository memberRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("Auditing 테스트")
    @WithMockUser(username = "dora", roles = "USER")
    public void auditingTest() {

        Member tempMember = new Member();
        memberRepository.save(tempMember);

        em.flush();
        em.clear();

        Member member = memberRepository.findById(tempMember.getMemberId())
                .orElseThrow(EntityNotFoundException::new);

        System.out.println("register time : " + member.getRegDate());
        System.out.println("update time : " + member.getModDate());
        System.out.println("create member : " + member.getCreatedBy());
        System.out.println("modify member : " + member.getModifiedBy());
    }
}