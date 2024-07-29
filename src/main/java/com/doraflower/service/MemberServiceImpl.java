package com.doraflower.service;

import com.doraflower.entity.Member;
import com.doraflower.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService, UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public Member saveMember(Member theMember) {

        Member tempMember = memberRepository.findByEmail(theMember.getEmail());
        if (tempMember != null) {
            throw new IllegalStateException("Already Registered Member!");
        }

        return memberRepository.save(theMember);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Member theMember = memberRepository.findByEmail(email);

        if(theMember == null) {
            throw new UsernameNotFoundException(email);
        }

        return null;
    }
}
