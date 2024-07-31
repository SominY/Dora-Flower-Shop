package com.doraflower.entity;

import com.doraflower.constant.Role;
import com.doraflower.dto.MemberFormDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.processing.Pattern;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "member")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long memberId;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    public static Member createMember(MemberFormDTO memberFormDTO,
                                      PasswordEncoder passwordEncoder) {

        String password = passwordEncoder.encode(memberFormDTO.getPassword());

        Member member = Member.builder()
                .name(memberFormDTO.getName())
                .email(memberFormDTO.getEmail())
                .password(password)
                .address(memberFormDTO.getAddress())
                .role(Role.USER)
                .build();

        return member;
    }

}
