package com.doraflower.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberFormDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Pattern(regexp = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$",
            message = "Please enter a valid email address.")
    private String email;

    @NotBlank(message = "Password is required")
    @Length(min=8, max=16, message = "Enter a password between 8 and 16 characters, including numbers.")
    private String password;

    @NotBlank(message = "Address is required")
    private String address;
}
