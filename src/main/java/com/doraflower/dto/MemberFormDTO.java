package com.doraflower.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MemberFormDTO {

    private String name;

    private String email;

    private String password;

    private String address;
}
