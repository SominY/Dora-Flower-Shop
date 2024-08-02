package com.doraflower.repository;

import com.doraflower.dto.MemberFormDTO;
import com.doraflower.entity.Cart;
import com.doraflower.entity.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
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
@TestPropertySource(locations = "classpath:application-test.properties")
class CartTest {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PersistenceContext
    EntityManager em;

    // 회원 엔티티 생성
    public Member createMember() {

        MemberFormDTO memberFormDTO = MemberFormDTO.builder()
                .name("test")
                .email("test@test.com")
                .password("1234")
                .address("123 st")
                .build();

        return Member.createMember(memberFormDTO, passwordEncoder);
    }

    @Test
    @DisplayName("장바구니 회원 엔티티 매핑 조회 테스트")
    public void findCartAndMemberTest() {

        // 1. Member 객체 생성 및 저장
        Member member = createMember();
        memberRepository.save(member);

        // 2. Cart 객체 생성 및 설정
        Cart cart = new Cart();
        cart.setMember(member);
        cartRepository.save(cart);

        // 3. 영속성 컨텍스트 플러시 및 초기화
        em.flush();
        em.clear();

        // 4. Cart 조회 및 검증
        Cart savedCart = cartRepository.findById(cart.getId())
                .orElseThrow(EntityNotFoundException::new);
        assertEquals(savedCart.getMember().getMemberId(), member.getMemberId());
    }
}