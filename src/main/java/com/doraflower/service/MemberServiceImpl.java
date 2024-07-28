package com.doraflower.service;

import com.doraflower.entity.Member;
import com.doraflower.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Override
    public Member saveMember(Member theMember) {

        Member tempMember = memberRepository.findByEmail(theMember.getEmail());
        if (tempMember != null) {
            throw new IllegalStateException("Already Registered Member!");
        }

        return memberRepository.save(theMember);
    }
}
