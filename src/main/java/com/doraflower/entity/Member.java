package com.doraflower.entity;

import com.doraflower.constant.Role;
import com.doraflower.dto.MemberFormDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "member")
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Member {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long memberId;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

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
                .build();

        return member;
    }

}
