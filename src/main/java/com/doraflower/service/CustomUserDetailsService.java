package com.doraflower.service;

import com.doraflower.entity.Member;
import com.doraflower.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    public final MemberRepository memberRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member theMember = memberRepository.findByEmail(email);

        if(theMember == null) {
            throw new UsernameNotFoundException(email);
        }

        return User.builder()
                .username(theMember.getEmail())
                .password(theMember.getPassword())
                .roles(theMember.getRole().toString())
                .build();

    }
}
